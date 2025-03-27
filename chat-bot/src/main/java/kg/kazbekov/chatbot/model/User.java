package kg.kazbekov.chatbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "\"user\"")
public class User extends BaseEntityIdentity{
    private Long telegramId;
    private String username;
    private String phoneNumber;
    private String name;

    @OneToOne
    private Chat privateChat;
}