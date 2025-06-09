package kg.kazbekov.chatbot.kafka.listener;

import kg.kazbekov.chatbot.Sender;
import kg.kazbekov.chatbot.kafka.event.ModePriceUpdateEvent;
import kg.kazbekov.chatbot.util.ResponseHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class Listener {

    private final Sender sender;

    @KafkaListener(topics = "model-price-change", groupId = "model-price-change-consumers")
    public void listenModelPriceChange(ModePriceUpdateEvent event) {
        var sendMessage = ResponseHelper.sendText("Цена на выбранную модель изменилась", event.getChatId());
        sender.internalExecute(sendMessage);
        log.info("Received price change event: modelId={}, chatId={}", event.getModelId(), event.getChatId());

    }
}
