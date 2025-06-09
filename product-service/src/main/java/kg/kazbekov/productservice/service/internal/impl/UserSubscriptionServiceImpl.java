package kg.kazbekov.productservice.service.internal.impl;

import kg.kazbekov.productservice.kafka.event.ModePriceUpdateEvent;
import kg.kazbekov.productservice.model.UserSubscription;
import kg.kazbekov.productservice.repository.UserSubscriptionRepository;
import kg.kazbekov.productservice.service.internal.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public Mono<UserSubscription> subscribeUser(String modelId, Long chatId){
        // TODO:
        //  1) if user already subscript, throw error
        //  2) check whether model id is exist
        return userSubscriptionRepository.save(new UserSubscription(chatId, modelId))
                .doOnSuccess(userSubscription -> log.info("Создана новая подписка"));
    }

    @Override
    public Mono<Void> noticeUsers(String modelId){
        // TODO:
        //  1) create entity, that will represent not sended notifications
        return userSubscriptionRepository.findAllByModelId(modelId)
                .flatMap(userSubscription ->
                        Mono.fromFuture(() -> kafkaTemplate.send("model-price-change", ModePriceUpdateEvent.of(modelId, userSubscription.getChatId())))
                                .doOnSuccess(result -> log.debug("Notification to %s chat id was send"))
                                .doOnError(ex -> log.error("Kafka send failed: ", ex))
                                .onErrorResume(ex -> Mono.empty())
                )
                .then();
    }


}
