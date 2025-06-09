package kg.kazbekov.chatbot.interceptor.impl;

import kg.kazbekov.chatbot.interceptor.Interceptor;
import kg.kazbekov.chatbot.model.Chat;
import kg.kazbekov.chatbot.service.internal.ChatService;
import kg.kazbekov.chatbot.util.RequestType;
import kg.kazbekov.chatbot.util.TelegramRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ChatInterceptor implements Interceptor {

    private final ChatService chatService;

    @Override
    public TelegramRequest handle(TelegramRequest telegramRequest) {
        var chatId = getChatId(telegramRequest);

        var chatByChatId = chatService.findByChatId(chatId);
        if(chatByChatId == null){
            Chat chat = new Chat();
            chat.setChatId(chatId);
            var persistedChat = chatService.save(chat);
            telegramRequest.setChat(persistedChat);
        }else{
            telegramRequest.setChat(chatByChatId);
        }
        return telegramRequest;
    }

    private Long getChatId(TelegramRequest telegramRequest){
        if(telegramRequest.getRequestType() == RequestType.CALLBACK){
            return telegramRequest.getUpdate().getCallbackQuery().getMessage().getChatId();
        }
        return telegramRequest.getMessage().getChatId();
    }

//    private Integer getTopicId(TelegramRequest telegramRequest){
//        if(telegramRequest.getRequestType() == RequestType.CALLBACK){
//            return telegramRequest.getUpdate().getCallbackQuery().getMessage().getMessageThreadId();
//        }
//        return telegramRequest.getUpdate().getMessage().getMessageThreadId();
//    }
}
