package kg.kazbekov.chatbot.interceptor.impl;

import kg.kazbekov.chatbot.interceptor.Interceptor;
import kg.kazbekov.chatbot.service.internal.UserService;
import kg.kazbekov.chatbot.util.RequestType;
import kg.kazbekov.chatbot.util.TelegramRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class UserInterceptor implements Interceptor {

    private final UserService userService;

    @Override
    public TelegramRequest handle(TelegramRequest telegramRequest) {
        var update = telegramRequest.getUpdate();
        Message message = getMessage(update);
        Long id = null;
        if(telegramRequest.getRequestType() == RequestType.CALLBACK){
            id = message.getChat().getId();
        }else if(telegramRequest.getRequestType() == RequestType.COMMAND){
            id = message.getFrom().getId();
        }

        var user = userService.findByTelegramId(id);
        if(user != null) {
            telegramRequest.setUser(user);
        }

        return telegramRequest;
    }

    private Message getMessage(Update update){
        if(update.getCallbackQuery() != null){
            return (Message) update.getCallbackQuery().getMessage();
        }else if(update.getMessage() != null){
            return update.getMessage();
        }
        return null;
    }
}
