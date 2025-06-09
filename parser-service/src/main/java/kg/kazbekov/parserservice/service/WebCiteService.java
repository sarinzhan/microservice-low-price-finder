package kg.kazbekov.parserservice.service;

import kg.kazbekov.parserservice.model.WebCite;

import java.util.List;

public interface WebCiteService {
    List<WebCite> getAllByWebCiteName(WebCite.WebCiteName name);
}
