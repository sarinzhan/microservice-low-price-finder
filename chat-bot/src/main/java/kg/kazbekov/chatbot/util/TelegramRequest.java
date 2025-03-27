package kg.kazbekov.chatbot.util;

import kg.kazbekov.chatbot.model.Chat;
import kg.kazbekov.chatbot.model.User;
import lombok.Data;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
@Getter
public class TelegramRequest {
    private Update update;
    private User user;

    private Chat chat;

    private RequestType requestType;
    private Message message;
}
