package kg.kazbekov.parserservice.service;

import kg.kazbekov.parserservice.parser.ParsedCardDto;
import kg.kazbekov.parserservice.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScraperService {
    private final List<Scraper> scraper;

    public List<ParsedCardDto> getAll(){
        return scraper
                .stream()
                .map(scraper -> {
                    try{
                        return scraper.scrape();
                    }catch (Exception ex){
                        log.error("Error while scraping. Ex:", ex);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .toList();
    }
}
