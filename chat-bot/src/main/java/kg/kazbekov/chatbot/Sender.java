package kg.kazbekov.chatbot;

import kg.kazbekov.chatbot.config.BotInitialization;
import kg.kazbekov.chatbot.util.ResponseHelper;
import kg.kazbekov.chatbot.util.TelegramRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
@Getter
public class Sender {
    private final BotInitialization botInitialization;

    public  <T extends Serializable, Method extends BotApiMethod<T>> T internalExecute(Method method){
        try {
            return botInitialization.execute(method);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public <T extends Serializable, Method extends BotApiMethod<T>> void sendWithDelay(Method method, Long delayMillis){
        Thread.ofVirtual().start(() -> {
            sleep(delayMillis);
            internalExecute(method);
        });
    }

    public Message send(TelegramRequest request, String text){
            var message = internalExecute(ResponseHelper.sendText(text, request));
            return internalExecute(ResponseHelper.sendText(text,request));
    }
    public Message send(TelegramRequest request, String text, InlineKeyboardMarkup markup){
        var sendMessage = ResponseHelper.sendText(text, request, markup);
       return internalExecute(sendMessage);
    }

    public void sendThenDelete(String text, Integer chatId, Integer seconds){
        Thread.ofVirtual().start(() -> {
            var message = internalExecute(ResponseHelper.sendText(text, Long.valueOf(chatId)));
            sleep((long) seconds * 1000 );
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(message.getChatId());
            deleteMessage.setMessageId(message.getMessageId());
            internalExecute(deleteMessage);
        });
    }

    public void sendThenDelete(TelegramRequest request,String text, Integer seconds){
        Thread.ofVirtual().start(() -> {
            var message = internalExecute(ResponseHelper.sendText(text, request));
            sleep((long) seconds * 1000 );
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(message.getChatId());
            deleteMessage.setMessageId(message.getMessageId());
            internalExecute(deleteMessage);
        });
    }

    public void deleteAnimation(Message message, Integer seconds){
        for (int i = 0; i < seconds; i++) {

        }
    }

    private void sleep(Long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
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
//            exec(deleteMessage);
        });
    }

}
