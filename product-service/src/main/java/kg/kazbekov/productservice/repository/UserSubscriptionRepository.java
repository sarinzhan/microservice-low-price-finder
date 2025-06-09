package kg.kazbekov.productservice.repository;

import kg.kazbekov.productservice.model.UserSubscription;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface UserSubscriptionRepository extends ReactiveMongoRepository<UserSubscription, String> {
    Flux<UserSubscription> findAllByModelId(String modelId);
}
