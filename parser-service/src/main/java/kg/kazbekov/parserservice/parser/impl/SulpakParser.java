package kg.kazbekov.parserservice.parser.impl;

import com.microsoft.playwright.*;
import kg.kazbekov.parserservice.parser.Parser;
import kg.kazbekov.parserservice.parser.ParsedCardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
@Qualifier("SulpakParser")
@Slf4j
@RequiredArgsConstructor
public class SulpakParser implements Parser {

    private final Browser browser;

    @Override
    public List<ParsedCardDto> parseManufactureCatalog(String url) {
        log.debug("Start parsing sulpak. Url: {}", url);
        List<ParsedCardDto> productRespons = new ArrayList<>();
        try (Page page = browser.newPage()) {
            page.navigate(url);
            page.querySelector("div.popup__content").querySelector("a.popup__close").click();
            do {
                // class="products__items products__items-js flex__block"
                log.info("Start parsing url: {}", url);
                var document = Jsoup.parse(page.content());

                var elements = document.select("#products").select("div.product__item");

                for (var element : elements) {
                    try {
                        var productResponse = parseCart(element);
                        productRespons.add(productResponse);
                    } catch (Exception ex) {
                        log.error("Error while parsing sulpak cart.\n Ex: {}\nHtml:\n {}", ex, element.html());
                    }
                }

                var nextButton = page.querySelector("li.pagination__next a");
                if (nextButton.getAttribute("class").equals("disabled")) {
                    break;
                }
                log.info(nextButton.innerHTML());
                try {
                    nextButton.click();
                    Thread.sleep(2000);
                } catch (Exception ex) {
                    log.info("Error while attempting click next button");
                }

            } while (true);
        }
        log.debug("Parsed {} product from sulpak.", productRespons.size());
        return productRespons;
    }
    private static final String MEM_UNIT = "(?:GB|TB)";
    private static final Pattern SIZE_RE =
            Pattern.compile("(\\d+)\\s*" + MEM_UNIT, Pattern.CASE_INSENSITIVE);
    private static final Pattern BOTH_SIZES =
            Pattern.compile("(\\d+)\\s*/\\s*(\\d+)\\s*" + MEM_UNIT, Pattern.CASE_INSENSITIVE);

    public ParsedCardDto parseCart(Element product) {
        String manufacture = product.attr("data-brand").trim();
        String dataName    = product.attr("data-name").trim();
        int    price       = (int) Math.round(Double.parseDouble(product.attr("data-price")));
        String url         = "https://www.sulpak.kg" +
                product.selectFirst("div.product__item-images-block a").attr("href").trim();

        String shortDesc = product.select("div.product__item-description").text();

        int ram = 0, storage = 0;

        /* 1) «8/128 GB» ­— сразу берём обе цифры */
        Matcher pair = BOTH_SIZES.matcher(shortDesc);
        if (pair.find()) {
            ram     = Integer.parseInt(pair.group(1));
            storage = parseSizeInGb(pair.group(2) + "GB");   // конвертирует TB→GB, если надо
        }

        /* 2) стандартные подписи из описания («Объем оперативной…») */
        if (ram == 0)
            ram = extractInt(shortDesc,
                    Pattern.compile("Объем оперативной памяти:\\s*(\\d+)\\s*" + MEM_UNIT));
        if (storage == 0)
            storage = extractInt(shortDesc,
                    Pattern.compile("Объем встроенной памяти:\\s*(\\d+)\\s*" + MEM_UNIT));

        /* 3) не нашли — тащим из имени */
        if (ram == 0)
            ram = parseFromName(dataName,
                    Pattern.compile("(\\d+)\\s*/\\s*\\d+\\s*" + MEM_UNIT));
        if (storage == 0)
            storage = parseSizeInGb(dataName);

        if(storage < 10){
            storage *=  1024;
        }

        String model = extractModel(dataName, manufacture);

        return ParsedCardDto.builder()
                .manufacture(manufacture)
                .model(model)
                .ram(ram)
                .storage(storage)
                .price(price)
                .url(url)
                .build();
    }

    /* --------- Вспомогательные методы ---------- */
    private int parseSizeInGb(String src) {
        Matcher m = SIZE_RE.matcher(src);
        if (m.find()) {
            int v = Integer.parseInt(m.group(1));
            return m.group(0).toLowerCase().contains("tb") ? v * 1024 : v;
        }
        return 0;
    }
    private int extractInt(String text, Pattern p) {
        Matcher m = p.matcher(text);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return 0;
    }

    private int parseFromName(String name, Pattern p) {
        Matcher m = p.matcher(name);
        if (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                if (m.group(i) != null) {
                    return Integer.parseInt(m.group(i));
                }
            }
        }
        return 0;
    }

    // 1. ------------------- extractModel -------------------
    private String extractModel(String dataName, String brand) {
        String tmp = dataName
                .replaceFirst("(?i)Смартфон\\s+", "")
                .replaceFirst("(?i)" + Pattern.quote(brand) + "\\s+", "")
                .trim();

    /*   … 8/128GB …
         … 8/128 GB …
         … 128GB …
         … 128 GB …                                */
//        Pattern p = Pattern.compile(
//                "^(.+?)(?=\\s+(?:\\d{1,2}/\\d{1,3}(?:\\s*(?:GB|TB))?|\\d{2,4}\\s*(?:GB|TB)))",
//                Pattern.CASE_INSENSITIVE);
        String MEM_BLOCK = "\\d{1,2}/\\d{1,3}(?:\\s*(?:GB|TB))?";
        Pattern MODEL_RX = Pattern.compile(
                "^(.+?)\\s+" + MEM_BLOCK + "\\s+.+$",
                Pattern.CASE_INSENSITIVE);
        Matcher m = MODEL_RX.matcher(tmp);
        if (m.find()) {
            if (!m.matches()) {
                throw new IllegalArgumentException(
                        "Некорректный формат товара: " + dataName);
            }
            return m.group(1).trim();
            // Redmi 13, Redmi 10, iPhone 16 Pro, POCO X6 …
        }

        // fallback: обрезаем всё, что начинается с GB или TB
        int pos = Stream.of("gb", "tb")
                .mapToInt(u -> tmp.toLowerCase().indexOf(u))
                .filter(i -> i >= 0)
                .min().orElse(-1);
        return (pos == -1 ? tmp : tmp.substring(0, pos)).trim();
    }

}
