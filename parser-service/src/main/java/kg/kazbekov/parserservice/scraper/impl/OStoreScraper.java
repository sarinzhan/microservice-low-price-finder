package kg.kazbekov.parserservice.scraper.impl;

import jakarta.annotation.PostConstruct;
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
public class OStoreScraper implements Scraper {

    @Autowired
    @Qualifier("OStoreParser")
    private Parser parser;

    private final WebCiteService webCiteService;

//    private final String XIAOMI_URL = "https://ostore.kg/ru/phones/xiaomi/";
//    private final String APPLE_URL = "https://ostore.kg/ru/phones/apple/";
//    private final String SAMSUNG_URL = "https://ostore.kg/ru/phones/samsung/";

//    @PostConstruct
//    public void postConstruct(){
//        var response = scrape();
//    }

    @Override
    public List<ParsedCardDto> scrape() {
        log.debug("Start scraping o store");
        var responseList = webCiteService.getAllByWebCiteName(WebCite.WebCiteName.O_STORE)
                .stream()
                .flatMap(cite -> cite.getUrls().stream())
//                .parallel()
                .map(parser::parseManufactureCatalog)
                .flatMap(Collection::stream)
                .toList();
        log.debug("Scraped {} product from o store", responseList.size());
        return responseList;
    }
}
