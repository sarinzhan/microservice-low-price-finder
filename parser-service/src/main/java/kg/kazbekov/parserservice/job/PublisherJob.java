package kg.kazbekov.parserservice.job;

import kg.kazbekov.parserservice.parser.Parser;
import kg.kazbekov.parserservice.service.ScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PublisherJob {


//    @Scheduled(cron = "0 0,30 * * * *")
    public void runParsers(){

    }
}
