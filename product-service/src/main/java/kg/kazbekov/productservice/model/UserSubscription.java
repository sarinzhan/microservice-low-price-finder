package kg.kazbekov.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_subscription")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserSubscription {
    @Id
    private String id;

    private Long chatId;

    private String modelId;

    public UserSubscription(Long chatId, String modelId) {
        this.chatId = chatId;
        this.modelId = modelId;
    }
}
