package kg.kazbekov.productservice.controller;

import kg.kazbekov.productservice.service.external.ParserServiceClient;
import kg.kazbekov.productservice.service.internal.ProductService;
import kg.kazbekov.productservice.service.internal.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final ParserServiceClient parserServiceClient;
    private final ProductService productService;
    private final UserSubscriptionService userSubscriptionService;

    @GetMapping
    public String hello(){
        return "Hello world from product service";
    }

    @PostMapping("/loadProducts")
    public String loadProducts(){
        var phones = parserServiceClient.getPhones();
        productService.addAll(phones);
        return "Загружено %d продуктов".formatted(phones.size());
    }

    @PostMapping("/kafka-event")
    public Mono<String> kafkaEvent(@RequestParam String modelId){
        return userSubscriptionService
                .noticeUsers(modelId)
                .thenReturn("Событие отправлено");
    }
}
