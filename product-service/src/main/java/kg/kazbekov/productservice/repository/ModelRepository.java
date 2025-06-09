package kg.kazbekov.productservice.repository;

import kg.kazbekov.productservice.model.Model;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.channels.MembershipKey;

public interface ModelRepository extends ReactiveMongoRepository<Model, String> {

    @Query("{'manufactureId': \"?0\"}")
    Flux<Model> findByManufactureId(String manufactureId);

    Mono<Model> findByNameIgnoreCase(String modelName);
}
