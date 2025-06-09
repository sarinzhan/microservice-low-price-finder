package kg.kazbekov.parserservice.parser;

import java.util.List;

public interface Parser {
    List<ParsedCardDto> parseManufactureCatalog(String url);
}
