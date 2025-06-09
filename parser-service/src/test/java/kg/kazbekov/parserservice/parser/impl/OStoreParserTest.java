package kg.kazbekov.parserservice.parser.impl;

import com.microsoft.playwright.Browser;
import kg.kazbekov.parserservice.parser.ParsedCardDto;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public class OStoreParserTest {
    static Browser browser;
    static OStoreParser parser;

    private final String PATH = "src/test/cart/o-store";

    @BeforeAll
    static void setup() {
        parser = new OStoreParser(browser);
    }

    @Test
    void Xiaomi() {
        var actualResult = getProduct(PATH + "/Xiaomi Redmi Note 14 Pro.html","div.itemRow");

        ParsedCardDto expected = ParsedCardDto.builder()
                .manufacture("Xiaomi")
                .model("Redmi Note 14 Pro+ 5G")
                .ram(8)
                .storage(256)
                .price(36990)
                .url("https://ostore.kg/ru/product/xiaomi-redmi-note-14-pro-5g/")
                .build();

        Assertions.assertEquals(expected, actualResult);
    }

    @Test
    void Samsung() {
        var actualResult = getProduct(PATH + "/Samsung.html","div.item");

        ParsedCardDto expected = ParsedCardDto.builder()
                .manufacture("Samsung")
                .model("Galaxy A05s")
                .ram(4)
                .storage(128)
                .price(11750)
                .url("https://ostore.kg/ru/product/samsung-galaxy-a05s/")
                .build();

        Assertions.assertEquals(expected, actualResult);
    }

    ParsedCardDto getProduct(String path, String cssQuery){
        try {
            var file = Path.of(path);
            var html = Files.readString(file);
            var element = Jsoup.parse(html).select(cssQuery).first();
            return parser.parseCart(element);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
