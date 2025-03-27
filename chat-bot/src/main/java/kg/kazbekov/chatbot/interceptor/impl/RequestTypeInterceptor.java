package kg.kazbekov.chatbot.interceptor.impl;

import kg.kazbekov.chatbot.interceptor.Interceptor;
import kg.kazbekov.chatbot.util.TelegramRequest;
import org.springframework.stereotype.Component;

@Component
public class RequestTypeInterceptor implements Interceptor {
    @Override
    public TelegramRequest handle(TelegramRequest telegramRequest) {
        return telegramRequest;
    }
}
