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
public class SulpakScraper implements Scraper {
    @Autowired
    @Qualifier("SulpakParser")
    private Parser parser;

    private final WebCiteService webCiteService;

//    private final String XIAOMI_URL = "https://www.sulpak.kg/f/smartfoniy/bishkek/1056_477";
//    private final String APPLE_URL = "https://www.sulpak.kg/f/smartfoniy/bishkek/1056_62";
//    private final String SAMSUNG_URL = "https://www.sulpak.kg/f/smartfoniy/bishkek/1056_1";


//    @PostConstruct
//    public void postConstruct(){
//        var response = scrape();
//        System.out.println();
//    }

    @Override
    public List<ParsedCardDto> scrape() {
        log.debug("Start scraping sulpak");
        var responsList = webCiteService.getAllByWebCiteName(WebCite.WebCiteName.SULPAK)
                .stream()
                .flatMap(webCite -> webCite.getUrls().stream())
//                .parallel()
                .map(parser::parseManufactureCatalog)
                .flatMap(Collection::stream)
                .toList();
        log.debug("Scraped {} products from sulpak", responsList.size());
        return responsList;
    }
}
