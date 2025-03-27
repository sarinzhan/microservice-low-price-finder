package kg.kazbekov.productservice.repository;

import kg.kazbekov.productservice.model.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    @Query("{'modelId': ?0}")
    Flux<Product> findByModelId(Long modelId);

    @Query("{'providerId': ?0}")
    Flux<Product> findByProviderId(Long providerId);
}
