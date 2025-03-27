package kg.kazbekov.chatbot.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class ResponseHelper {
    public static SendMessage sendText(String text, TelegramRequest req){
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(req.getChat().getChatId());
        return message;
    }
    public static SendMessage sendText(String text, Long chatId, InlineKeyboardMarkup markup){
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId);
        message.setReplyMarkup(markup);
        return message;
    }

    public static SendMessage sendText(String text, TelegramRequest req, InlineKeyboardMarkup markup){
        SendMessage message = sendText(text, req);
        message.setReplyMarkup(markup);
        return message;
    }

    public static SendMessage sendText(String text, Long chatId){
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId);
        return message;
    }
}
