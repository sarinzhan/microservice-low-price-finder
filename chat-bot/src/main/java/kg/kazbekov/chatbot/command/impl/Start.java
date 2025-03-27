package kg.kazbekov.chatbot.command.impl;

import kg.kazbekov.chatbot.InlineKeyboardMarkup;
import kg.kazbekov.chatbot.command.CallbackDataEnum;
import kg.kazbekov.chatbot.command.Command;
import kg.kazbekov.chatbot.util.RequestType;
import kg.kazbekov.chatbot.util.TelegramRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class Start implements Command {
    @Override
    public SendMessage handle(TelegramRequest request) {
        var inlineKeyboard = InlineKeyboardMarkup.builder()
                .button("Производитель 1", CallbackDataEnum.SELECTED_MANUFACTURE_ + "производитель1")
                .nextRow()
                .button("Производитель 2", CallbackDataEnum.SELECTED_MANUFACTURE_ + "производитель2")
                .build();
        return SendMessage.builder()
                .text("Выберите производителя")
                .replyMarkup(inlineKeyboard)
                .chatId(request.getChat().getChatId())
                .build();
    }

    @Override
    public boolean condition(TelegramRequest request) {
        return request.getRequestType().equals(RequestType.COMMAND)
                && request.getMessage().getText().equals("/start");
    }
}
