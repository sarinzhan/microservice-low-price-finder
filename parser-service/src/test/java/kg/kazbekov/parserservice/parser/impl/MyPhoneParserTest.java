package kg.kazbekov.parserservice.parser.impl;

import com.microsoft.playwright.Browser;
import kg.kazbekov.parserservice.parser.ParsedCardDto;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public class MyPhoneParserTest {
    static Browser browser;
    static MyPhoneParser parser;

    private final String PATH = "src/test/cart/my-phone";

    @BeforeAll
    static void setup() {
        parser = new MyPhoneParser(browser);
    }

    @AfterAll
    static void teardown() {
    }

    @Test
    void Apple_Iphone16_Pro() {
        var actualResult = getProduct(PATH + "/iPhone 16 Pro.html","div.col-sm-4");

        ParsedCardDto expected = ParsedCardDto.builder()
                .manufacture("Apple")
                .model("iPhone 16 Pro Max")
                .ram(0)
                .storage(512)
                .price(153900)
                .url("https://www.myphone.kg/ru/catalog/cell/i3895/iphone_16_pro_max")
                .build();

        Assertions.assertEquals(expected, actualResult);
    }

    @Test
    void Xiaomi_Poco_F6_Pro() {
        var actualResult = getProduct(PATH + "/Xiaomi Poco F6 Pro.html", "div.col-sm-4");

        ParsedCardDto expected = ParsedCardDto.builder()
                .manufacture("Xiaomi")
                .model("Poco F6 Pro")
                .ram(16)
                .storage(1024)
                .price(59990)
                .url("https://www.myphone.kg/ru/catalog/cell/i3910/poco_f6_pro")
                .build();

        Assertions.assertEquals(expected, actualResult);
    }

    @Test
    void Samsung_Galaxy_S24() {
        var actualResult = getProduct(PATH + "/Samsung Galaxy S24.html", "div.col-md-3");

        ParsedCardDto expected = ParsedCardDto.builder()
                .manufacture("Samsung")
                .model("Galaxy S24")
                .ram(8)
                .storage(512)
                .price(69990)
                .url("https://www.myphone.kg/ru/catalog/cell/i4102/galaxy_s24")
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
