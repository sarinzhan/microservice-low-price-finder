package kg.kazbekov.chatbot.command.impl;

import kg.kazbekov.chatbot.InlineKeyboardMarkup;
import kg.kazbekov.chatbot.command.CallbackDataEnum;
import kg.kazbekov.chatbot.command.Command;
import kg.kazbekov.chatbot.util.RequestType;
import kg.kazbekov.chatbot.util.TelegramRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class SelectModelCommand implements Command {
    @Override
    public SendMessage handle(TelegramRequest request) {
        var manufacture = request.getUpdate().getCallbackQuery().getData().split("_")[2];

        // collect data from this manufacture

        var inlineKeyboard = InlineKeyboardMarkup.builder()
                .button("Модель 1", CallbackDataEnum.SELECTED_MODEL_ + "модель1")
                .nextRow()
                .button("Модель 2", CallbackDataEnum.SELECTED_MODEL_ + "модель2")
                .build();
        return SendMessage.builder()
                .text("Вы выбрали %s производителя, теперь выберите модель:".formatted(manufacture))
                .replyMarkup(inlineKeyboard)
                .chatId(request.getChat().getChatId())
                .build();
    }

    @Override
    public boolean condition(TelegramRequest request) {
        return request.getRequestType().equals(RequestType.CALLBACK)
                && request.getUpdate().getCallbackQuery().getData().startsWith(CallbackDataEnum.SELECTED_MANUFACTURE_.toString());
    }
}
