package kg.kazbekov.productservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ModePriceUpdateEvent {
    private String modelId;
    private Long chatId;
}
