package kg.kazbekov.chatbot.command;

import kg.kazbekov.chatbot.util.TelegramRequest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface Command {
    SendMessage handle(TelegramRequest request);
    boolean condition(TelegramRequest request);

//    default SendMessage sendText(String message, TelegramRequest telegramRequest){
//        return ChatService.sendText(telegramRequest, message);
//    }
//    default SendMessage sendText(String message, TelegramRequest telegramRequest, ReplyKeyboard replyKeyboard){
//        var sendMessage = ChatService.sendText(telegramRequest, message);
//        sendMessage.setReplyMarkup(replyKeyboard);
//        return sendMessage;
//    }
//    default SendMessage sendText(TelegramRequest telegramRequest){
//        return ChatService.sendText(telegramRequest.getChat());
//    }
//
//
//
//    default EditMessageText editMessage(TelegramRequest telegramRequest, String text, InlineKeyboardMarkup replyKeyboard){
//        var editMessageText = editMessage(telegramRequest,text);
//        editMessageText.setReplyMarkup(replyKeyboard);
//        return editMessageText;
//    }
//    default EditMessageText editMessage(TelegramRequest telegramRequest, String text){
//        var editMessageText = editMessage(telegramRequest);
//        editMessageText.setText(text);
//        return editMessageText;
//    }
//    default EditMessageText editMessage(TelegramRequest telegramRequest){
//        EditMessageText editMessageText = new EditMessageText();
//        editMessageText.setChatId(telegramRequest.getChat().getChatId());
//        editMessageText.setMessageId(telegramRequest.getMessage().getMessageId());
//        return editMessageText;
//    }
//
//    default EditMessageCaption editCaption(TelegramRequest telegramRequest, String caption){
//        var editMessageCaption = editCaption(telegramRequest);
//        editMessageCaption.setCaption(caption);
//        return editMessageCaption;
//    }
//    default EditMessageCaption editCaption(TelegramRequest telegramRequest){
//        EditMessageCaption editMessageCaption = new EditMessageCaption();
//        editMessageCaption.setChatId(telegramRequest.getChat().getChatId());
//        editMessageCaption.setMessageId(telegramRequest.getMessage().getMessageId());
//        return editMessageCaption;
//    }
//
//    default EditMessageReplyMarkup editMarkup(TelegramRequest telegramRequest){
//        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
//        editMessageReplyMarkup.setChatId(telegramRequest.getChat().getChatId());
//        editMessageReplyMarkup.setMessageId(telegramRequest.getMessage().getMessageId());
//        return editMessageReplyMarkup;
//    }
//    default DeleteMessage deleteMessage(TelegramRequest telegramRequest){
//        DeleteMessage deleteMessage = new DeleteMessage();
//        deleteMessage.setChatId(telegramRequest.getChat().getChatId());
//        deleteMessage.setMessageId(telegramRequest.getMessage().getMessageId());
//        return deleteMessage;
//    }
}
