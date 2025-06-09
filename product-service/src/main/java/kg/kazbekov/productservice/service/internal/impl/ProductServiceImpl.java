package kg.kazbekov.productservice.service.internal.impl;

import kg.kazbekov.productservice.dto.external.ParsedPhoneResponse;
import kg.kazbekov.productservice.model.Manufacture;
import kg.kazbekov.productservice.model.Model;
import kg.kazbekov.productservice.model.Product;
import kg.kazbekov.productservice.repository.ManufactureRepository;
import kg.kazbekov.productservice.repository.ModelRepository;
import kg.kazbekov.productservice.repository.ProductRepository;
import kg.kazbekov.productservice.service.external.ParserServiceClient;
import kg.kazbekov.productservice.service.internal.ProductService;
import kg.kazbekov.productservice.service.internal.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl extends BaseServiceImpl<Product, String> implements ProductService {
    private final ProductRepository productRepository;

    private final ManufactureRepository manufactureRepository;
    private final ModelRepository modelRepository;
    private final UserSubscriptionService userSubscriptionService;
    private final ParserServiceClient parserServiceClient;

    public Flux<Product> findByModelId(Long modelId){
        return productRepository.findByModelId(modelId);
    }

    public Mono<Product> create(Product product) {
        return productRepository.save(product);
    }

    @Scheduled(cron = "0 0 * * * *")
    @Async
    public void postConstruct(){
        log.info("Start job updating product info");
        var phones = parserServiceClient.getPhones();
        addAll(phones);
        log.info("End job updating product info");
    }

    @Override
    public void addAll(List<ParsedPhoneResponse> parsedPhoneResponses){
        Flux.fromIterable(
                        parsedPhoneResponses.stream()
                                .filter(Objects::nonNull)
                                .toList()
                )
                .filter(phone -> phone.getManufacture() != null && phone.getModel() != null && phone.getUrl() != null)
                .concatMap(phone ->
                        saveManufactureAndModelIfNotExist(phone.getManufacture(), phone.getModel())
                                .thenReturn(phone)
                )
                .flatMap(phone ->
                        modelRepository.findByNameIgnoreCase(phone.getModel())
                                .map(model ->
                                        Product.builder()
                                                .modelId(model.getId())
                                                .providerId(1L)
                                                .url(phone.getUrl())
                                                .price(phone.getPrice())
                                                .ram(phone.getRam())
                                                .storage(phone.getStorage())
                                                .build()
                                )
                                .flatMap(product ->
                                        productRepository
                                                .findByModelIdAndRamAndStorageAndUrl(product.getModelId(), product.getRam(), product.getStorage(), product.getUrl())
                                                .flatMap(persistedProduct -> {
                                                    product.setId(persistedProduct.getId());
                                                    if(!Objects.equals(persistedProduct.getPrice(), product.getPrice())){
                                                        return userSubscriptionService.noticeUsers(product.getModelId())
                                                                .thenReturn(product);
                                                    }
                                                    return Mono.just(product);
                                                })
                                                .switchIfEmpty(Mono.just(product))
                                )

                )
                .flatMap(product ->
                        save(product)
                                .doOnSuccess(savedProduct -> {
                                    if (product.getId() == null) {
                                        log.info("Saved new product: Model ID = {}, URL = {}", savedProduct.getModelId(), savedProduct.getUrl());
                                    } else {
                                        log.info("Updated existing product: ID = {}, Model ID = {}, URL = {}", savedProduct.getId(), savedProduct.getModelId(), savedProduct.getUrl());
                                    }
                                })
                )
                .doOnComplete(() -> log.info("Product info updated"))
                .subscribe();
    }

    public Mono<Void> saveManufactureAndModelIfNotExist(String manufactureName, String modelName){
        return manufactureRepository.findByName(manufactureName)
                .switchIfEmpty(
                        manufactureRepository.save(new Manufacture(manufactureName))
                                .doOnSuccess(savedManufacture -> log.info("Saved new manufacture: {}", savedManufacture.getName()))
                )
                .flatMap(manufacture ->
                        modelRepository.findByNameIgnoreCase(modelName)
                                .switchIfEmpty(
                                        modelRepository.save(new Model(modelName, manufacture.getId()))
                                                .doOnSuccess(savedModel -> log.info("Saved new model: {}", savedModel.getName()))
                                )
                )
                .then();
    }

    public Mono<Product> save(Product product){
        product.setLastUpdateTime(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public Flux<Product> getAllByModelId(String modelId) {
        return productRepository.findAllByModelId(modelId);
    }
}
