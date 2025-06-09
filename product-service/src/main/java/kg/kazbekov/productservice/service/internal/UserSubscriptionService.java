package kg.kazbekov.productservice.service.internal;

import kg.kazbekov.productservice.model.UserSubscription;
import reactor.core.publisher.Mono;

public interface UserSubscriptionService {
    Mono<UserSubscription> subscribeUser(String modelId, Long chatId);

    Mono<Void> noticeUsers(String modelId);
}
