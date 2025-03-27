//package kg.kazbekov.chatbot;
//
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
//
//@Component
//@Slf4j
//public class FoodOrderBot extends TelegramLongPollingBot {
//
//    @Value("${telegrambot.token}")
//    private String token;
//
//    @Value("${telegrambot.username")
//    private String username;
//
//
////    @Autowired
////    private Navigator navigator;
//
//    private final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//
//    public FoodOrderBot() throws TelegramApiException {}
//
//    @PostConstruct
//    private void init() throws TelegramApiException {
//        telegramBotsApi.registerBot(this);
//    }
//    @Override
//    public void onUpdateReceived(Update update) {
//        try {
//            execute(navigator.navigate(update, this));
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    @Override
//    public String getBotUsername() {
//        return username;
//    }
//
//    @Override
//    public String getBotToken() {
//        return token;
//    }
//
//}
