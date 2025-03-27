package kg.kazbekov.chatbot.command.impl;

import kg.kazbekov.chatbot.command.Command;
import kg.kazbekov.chatbot.util.RequestType;
import kg.kazbekov.chatbot.util.TelegramRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static kg.kazbekov.chatbot.command.CallbackDataEnum.*;

@Component
public class ShowResultCommand implements Command {
    @Override
    public SendMessage handle(TelegramRequest request) {
        var model = request.getUpdate().getCallbackQuery().getData().split("_")[2];
        // grep data from selected model

        return SendMessage.builder()
                .text("Вы выбрали модель: %s\n Цены и ссылки ниже: \n 1 вариант\n2 вариант".formatted(model))
                .chatId(request.getChat().getChatId())
                .build();
    }

    @Override
    public boolean condition(TelegramRequest request) {
        return request.getRequestType().equals(RequestType.CALLBACK)
                && request.getUpdate().getCallbackQuery().getData().startsWith(SELECTED_MODEL_.toString());
    }
}
