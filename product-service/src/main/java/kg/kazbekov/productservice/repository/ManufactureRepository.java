package kg.kazbekov.productservice.repository;

import kg.kazbekov.productservice.model.Manufacture;
import kg.kazbekov.productservice.model.Model;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ManufactureRepository extends ReactiveMongoRepository<Manufacture, String> {
    Mono<Manufacture> findByName(String name);
}
