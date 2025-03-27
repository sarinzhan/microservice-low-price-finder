package kg.kazbekov.chatbot.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Chat extends BaseEntityIdentity{
    private Long chatId;
}
