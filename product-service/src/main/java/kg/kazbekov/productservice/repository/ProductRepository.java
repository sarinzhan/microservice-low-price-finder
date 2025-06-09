package kg.kazbekov.productservice.repository;

import kg.kazbekov.productservice.model.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    @Query("{'modelId': ?0}")
    Flux<Product> findByModelId(Long modelId);

    @Query("{'providerId': ?0}")
    Flux<Product> findByProviderId(Long providerId);

    Mono<Product> findByModelIdAndRamAndStorage(String modelId, Integer ram, Integer storage);

    Mono<Product> findByModelIdAndRamAndStorageAndUrl(String modelId, Integer ram, Integer storage, String url);

    Flux<Product> findAllByModelId(String modelId);
}
