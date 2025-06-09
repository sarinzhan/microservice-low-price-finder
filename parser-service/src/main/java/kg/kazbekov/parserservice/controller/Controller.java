package kg.kazbekov.parserservice.controller;

import kg.kazbekov.parserservice.parser.ParsedCardDto;
import kg.kazbekov.parserservice.service.ScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final ScraperService scraperService;
    @GetMapping("/phone")
    public ResponseEntity<List<ParsedCardDto>> getPhones(){
        return ResponseEntity.ok(
                scraperService.getAll()
        );
    }

    @GetMapping
    public String hello(){
        return "Hello from parser service";
    }
}
