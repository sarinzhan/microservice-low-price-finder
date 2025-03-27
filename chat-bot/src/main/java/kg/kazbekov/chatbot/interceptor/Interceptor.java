package kg.kazbekov.chatbot.interceptor;


import kg.kazbekov.chatbot.util.TelegramRequest;

public interface Interceptor {
    TelegramRequest handle(TelegramRequest telegramRequest);
}
