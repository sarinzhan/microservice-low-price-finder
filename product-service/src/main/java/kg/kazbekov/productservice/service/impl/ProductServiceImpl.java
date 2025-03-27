package kg.kazbekov.productservice.service.impl;

import kg.kazbekov.productservice.model.Product;
import kg.kazbekov.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {
    private final ProductRepository productRepository;

    public Flux<Product> findByModelId(Long modelId){
        return productRepository.findByModelId(modelId);
    }

    public Mono<Product> create(Product product) {
        return productRepository.save(product);
    }
}
