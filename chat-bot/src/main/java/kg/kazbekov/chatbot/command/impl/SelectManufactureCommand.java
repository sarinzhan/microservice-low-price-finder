package kg.kazbekov.chatbot.command.impl;

import kg.kazbekov.chatbot.InlineKeyboardMarkup;
import kg.kazbekov.chatbot.command.CallbackDataEnum;
import kg.kazbekov.chatbot.command.Command;
import kg.kazbekov.chatbot.util.RequestType;
import kg.kazbekov.chatbot.util.TelegramRequest;
import kg.kazbekov.productservice.grpc.AllRequest;
import kg.kazbekov.productservice.grpc.ProductServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class SelectManufactureCommand implements Command {

    @GrpcClient("productService")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

    @Override
    public SendMessage handle(TelegramRequest request) {
        var allManufacture = productServiceStub.getAllManufacture(AllRequest.newBuilder().build()).getManufacturiesList();
        var inlineKeyboardMarkupBuilder = InlineKeyboardMarkup.builder();
        for (var manufacture : allManufacture) {
            inlineKeyboardMarkupBuilder
                    .button(manufacture.getName(), CallbackDataEnum.SELECTED_MANUFACTURE_ID_ + manufacture.getId()).nextRow();
        }

        return SendMessage.builder()
                .text("Выберите производителя")
                .replyMarkup(inlineKeyboardMarkupBuilder.build())
                .chatId(request.getChat().getChatId())
                .build();
    }

    @Override
    public boolean condition(TelegramRequest request) {
        return request.getRequestType().equals(RequestType.COMMAND)
                && request.getMessage().getText().equals("/start");
    }
}
