package kg.kazbekov.parserservice.service.impl;

import kg.kazbekov.parserservice.model.WebCite;
import kg.kazbekov.parserservice.repository.WebCiteRepository;
import kg.kazbekov.parserservice.service.WebCiteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebCiteServiceImpl implements WebCiteService {
    private final WebCiteRepository webCiteRepository;

    @Override
    public List<WebCite> getAllByWebCiteName(WebCite.WebCiteName name){
        return webCiteRepository.getByWebCiteName(name)
                .collectList()
                .block();
    }
}
