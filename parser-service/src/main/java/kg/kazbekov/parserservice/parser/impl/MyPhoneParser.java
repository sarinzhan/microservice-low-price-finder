package kg.kazbekov.parserservice.parser.impl;

import com.microsoft.playwright.*;
import kg.kazbekov.parserservice.parser.Parser;
import kg.kazbekov.parserservice.parser.ParsedCardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
@Qualifier("MyPhoneParser")
@RequiredArgsConstructor
public class MyPhoneParser implements Parser {

    private final Browser browser;
//    @PostConstruct
//    public void postConstruct(){
//        String xiaomiUrl = "https://www.myphone.kg/ru/catalog/cell?sort%5Bby%5D=price_min&sort%5Bord%5D=desc&brn%5B%5D=77&price%5Bmin%5D=0&price%5Bmax%5D=0";
//        String appleUrl = "https://www.myphone.kg/ru/catalog/cell?sort%5Bby%5D=price_min&sort%5Bord%5D=desc&brn%5B%5D=5&price%5Bmin%5D=&price%5Bmax%5D=";
//        String samsungUrl = "https://www.myphone.kg/ru/catalog/cell?sort%5Bby%5D=price_min&sort%5Bord%5D=desc&brn%5B%5D=21&price%5Bmin%5D=0&price%5Bmax%5D=0";
//        parseManufactureCatalog(xiaomiUrl);
//    }

    @Override
    public List<ParsedCardDto> parseManufactureCatalog(String url) {
        log.debug("Start parsing my phone. Url: {}", url);
        var htmlCode = getFullHtmlCode(url);
        var document = Jsoup.parse(htmlCode);
        var elements = document.select("div.col-sm-4");

        List<ParsedCardDto> productRespons = new ArrayList<>();

        for (var element : elements) {
            try {
                var productResponse = parseCart(element);
                productRespons.add(productResponse);
            }catch (Exception ex){
                log.error("Error while parsing my phone cart.\nEx: {}.\nHtml:\n {}", ex, element.html());
            }
        }
        log.debug("Parsed {} product from my phone.", productRespons.size());
        return productRespons;
    }

    private String getFullHtmlCode(String url) {
        Page page = browser.newPage();
        page.navigate(url);
        for (int i = 0; i < 5; i++) {
            page.evaluate("window.scrollTo(0, document.body.scrollHeight);");
            page.waitForTimeout(1000); // Adjust time based on load speed
        }

        // Get the fully loaded HTML content
        String content = page.content();
        page.close();
        return content;
    }

    public ParsedCardDto parseCart(Element element){
            // val:  /ru/catalog/cell/i3895/iphone_16_pro_max
            var urlToDetails = "https://www.myphone.kg" +element.select("a").getFirst().attribute("href").getValue();

            //  <a href="/ru/catalog/cell/i3895/iphone_16_pro_max">
            //  Apple<br> iPhone 16 Pro Max
            //  <span>512Gb</span>
            //  </a>
            var text = element.select("div.title").text();
            if (text.isEmpty()) {
                return null;
            }
            var manufacture = text.substring(0, text.indexOf(" ")).trim();
            var model = text.substring(text.indexOf(" "), text.lastIndexOf(" ")).trim();
            int storage = 0;
            int ram = 0;
            if (manufacture.equals("Apple")) {
                storage = Integer.parseInt(text.substring(text.lastIndexOf(" ")).replaceAll("\\D+", ""));
            } else if (manufacture.equals("Samsung") || manufacture.equals("Xiaomi")) {
                // ищем число+число
                var matcher = Pattern.compile("\\d+\\+\\d+").matcher(text);
                if (matcher.find()) {
                    var ramAndStorage = matcher.group();
                    ram = Integer.parseInt(ramAndStorage.substring(0, ramAndStorage.indexOf("+")));
                    storage = Integer.parseInt(ramAndStorage.substring(ramAndStorage.indexOf("+") + 1));
                    model = text.substring(text.indexOf(" "), text.indexOf(ramAndStorage)).trim();
                } else {
                    return null;
                }
            } else {
                return null;
            }

            if (storage < 10) {
                storage *= 1024;
            }

            var price = Integer.parseInt(element.select("span.price_sp").text().replaceAll(" ", ""));

            return ParsedCardDto.builder()
                    .storage(storage)
                    .ram(ram)
                    .url(urlToDetails)
                    .manufacture(manufacture)
                    .model(model)
                    .price(price)
                    .build();
    }
}
