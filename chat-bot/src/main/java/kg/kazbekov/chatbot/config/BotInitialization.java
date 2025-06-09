package kg.kazbekov.chatbot.config;

import jakarta.annotation.PostConstruct;
import kg.kazbekov.chatbot.handler.Dispatcher;
import kg.kazbekov.chatbot.util.TelegramRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.Serializable;

@Component
@Slf4j
public class BotInitialization extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.username}")
    private String username;

    @Autowired
    @Lazy
    private Dispatcher dispatcher;

    private final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

    public BotInitialization() throws TelegramApiException {}

    @PostConstruct
    private void init() throws TelegramApiException {
        telegramBotsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        var response = dispatcher.dispatch(update);
        if (response != null) {
            exec((BotApiMethod) response);
        }
    }


    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }


    public <T extends Serializable, Method extends BotApiMethod<T>> T exec(Method method){
        try {
            return execute(method);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMessage(TelegramRequest telegramRequest, Integer messageId, Long millis){
        Thread.ofVirtual().start(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(telegramRequest.getChat().getChatId());
            deleteMessage.setMessageId(messageId);
            exec(deleteMessage);
        });
    }



}
