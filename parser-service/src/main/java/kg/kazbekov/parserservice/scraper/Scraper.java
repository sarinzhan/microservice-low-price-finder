package kg.kazbekov.parserservice.scraper;

import kg.kazbekov.parserservice.parser.ParsedCardDto;

import java.util.List;

public interface Scraper {
    List<ParsedCardDto> scrape();
}
