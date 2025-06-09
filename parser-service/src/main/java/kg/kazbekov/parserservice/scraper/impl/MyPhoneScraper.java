package kg.kazbekov.parserservice.scraper.impl;

import kg.kazbekov.parserservice.model.WebCite;
import kg.kazbekov.parserservice.parser.Parser;
import kg.kazbekov.parserservice.parser.ParsedCardDto;
import kg.kazbekov.parserservice.scraper.Scraper;
import kg.kazbekov.parserservice.service.WebCiteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyPhoneScraper implements Scraper {
    @Qualifier("MyPhoneParser")
    @Autowired
    private Parser parser;

    private final WebCiteService webCiteService;

//    private final String XIAOMI_URL = "https://www.myphone.kg/ru/catalog/cell?sort%5Bby%5D=price_min&sort%5Bord%5D=desc&brn%5B%5D=77&price%5Bmin%5D=0&price%5Bmax%5D=0";
//    private final String APPLE_URL = "https://www.myphone.kg/ru/catalog/cell?sort%5Bby%5D=price_min&sort%5Bord%5D=desc&brn%5B%5D=5&price%5Bmin%5D=&price%5Bmax%5D=";
//    private final String SAMSUNG_URL = "https://www.myphone.kg/ru/catalog/cell?sort%5Bby%5D=price_min&sort%5Bord%5D=desc&brn%5B%5D=21&price%5Bmin%5D=0&price%5Bmax%5D=0";

//    @PostConstruct
//    public void postConstruct(){
//        var response = scrape();
//        System.out.println();
//    }

    @Override
    public List<ParsedCardDto> scrape() {
        log.debug("Start scraping my phone cite");
        var responseList = webCiteService.getAllByWebCiteName(WebCite.WebCiteName.MY_PHONE)
                .stream()
                .flatMap(cite -> cite.getUrls().stream())
//                .parallel()
                .map(parser::parseManufactureCatalog)
                .flatMap(Collection::stream)
                .toList();
        log.debug("Scraped {} product from my phone", responseList.size());
        return responseList;
    }
}
