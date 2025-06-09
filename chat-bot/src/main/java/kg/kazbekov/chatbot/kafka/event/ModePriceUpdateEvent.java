package kg.kazbekov.chatbot.kafka.event;

import lombok.Data;

@Data
public class ModePriceUpdateEvent {
    private String modelId;
    private Long chatId;
}
