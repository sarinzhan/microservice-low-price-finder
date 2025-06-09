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

class SulpakParserTest {
    static Browser browser;
    static SulpakParser parser;

    private final String PATH = "src/test/cart/sulpak";

    @BeforeAll
    static void setup() {
        parser = new SulpakParser(browser);
    }

    @AfterAll
    static void teardown() {
    }

    @Test
    void Apple_Iphone13_4_128() {
        var actualResult = getProduct(PATH + "/Смартфон_Apple_iPhone_13_4-128_Midnight.html");

        ParsedCardDto expected = ParsedCardDto.builder()
                .manufacture("Apple")
                .model("iPhone 13")
                .ram(4)
                .storage(128)
                .price(51990)
                .url("https://www.sulpak.kg/g/smartfon_apple_iphone_13_128gb_midnight_mlnw3rka")
                .build();
        Assertions.assertEquals(expected, actualResult);
    }

    @Test
    void Apple_Iphone16Pro_8_128() {
        var actualResult = getProduct(PATH + "/Смартфон_Apple_iPhone_16_Pro_8_128GB_Black_Titanium.html");

        ParsedCardDto expected = ParsedCardDto.builder()
                .manufacture("Apple")
                .model("iPhone 16 Pro")
                .ram(8)
                .storage(128)
                .price(117290)
                .url("https://www.sulpak.kg/g/smartfoniy-apple-iphone-16-pro-128gb-black-titanium-mynd3hxa")
                .build();

        Assertions.assertEquals(expected, actualResult);
    }

    @Test
    void Apple_iPhone_16ProMax_8_256_White_Titanium(){
        var actualResult = getProduct(PATH + "/Смартфон Apple iPhone 16 Pro 1tb Desert Titanium.html");

        ParsedCardDto expected = ParsedCardDto.builder()
                .manufacture("Apple")
                .model("iPhone 16 Pro")
                .ram(8)
                .storage(1024)
                .price(0)
                .url("https://www.sulpak.kg/g/smartfoniy-apple-iphone-16-pro-1tb-desert-titanium-mynw3hxa")
                .build();

        Assertions.assertEquals(expected, actualResult);
    }


    @Test
    void Samsung_GalaxyS24_8_128() {
        var actualResult = getProduct(PATH + "/Смартфон Samsung Galaxy S24 8 128GB.html");

        ParsedCardDto expected = ParsedCardDto.builder()
                .manufacture("Samsung")
                .model("Galaxy S24")
                .ram(8)
                .storage(128)
                .price(59990)
                .url("https://www.sulpak.kg/g/smartfoniy_samsung_galaxy_s24_128gb_marble_gray_sm_s921bzadskz")
                .build();

        Assertions.assertEquals(expected, actualResult);
    }

    @Test
    void Xiaomi_Redmi_10_6_128_GB() {
        var actualResult = getProduct(PATH + "/Смартфон Xiaomi Redmi 10 6-128 GB Sea Blue.html");

        ParsedCardDto expected = ParsedCardDto.builder()
                .manufacture("Xiaomi")
                .model("Redmi 10")
                .ram(6)
                .storage(128)
                .price(21890)
                .url("https://www.sulpak.kg/g/smartfon_xiaomi_redmi_10_6128gb_sea_blue")
                .build();

        Assertions.assertEquals(expected, actualResult);
    }

    @Test
    void Xiaomi_Redmi_10_6_128() {
        var actualResult = getProduct(PATH + "/Смартфон Xiaomi Redmi 10 6-128 Sea Blue.html");

        ParsedCardDto expected = ParsedCardDto.builder()
                .manufacture("Xiaomi")
                .model("Redmi 10")
                .ram(6)
                .storage(128)
                .price(21890)
                .url("https://www.sulpak.kg/g/smartfon_xiaomi_redmi_10_6128gb_sea_blue")
                .build();

        Assertions.assertEquals(expected, actualResult);
    }

    ParsedCardDto getProduct(String path){
        try {
            var file = Path.of(path);
            var html = Files.readString(file);
            var element = Jsoup.parse(html).select("div.product__item").first();
            return parser.parseCart(element);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}