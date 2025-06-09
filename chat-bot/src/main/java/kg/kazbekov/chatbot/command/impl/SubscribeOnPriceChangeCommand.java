package kg.kazbekov.chatbot.command.impl;

import kg.kazbekov.chatbot.command.Command;
import kg.kazbekov.chatbot.util.RequestType;
import kg.kazbekov.chatbot.util.TelegramRequest;
import kg.kazbekov.productservice.grpc.ProductServiceGrpc;
import kg.kazbekov.productservice.grpc.SubscribeUserOnModelPriceChangeRequest;
import kg.kazbekov.productservice.grpc.SubscribeUserOnModelPriceChangeResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;

import static kg.kazbekov.chatbot.command.CallbackDataEnum.*;

@Component
public class SubscribeOnPriceChangeCommand implements Command {
    @GrpcClient("productService")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

    @Override
    public SendMessage handle(TelegramRequest request) {
        var modelId = request.getUpdate().getCallbackQuery().getData().split("_")[SUBSCRIBE_ON_PRICE_CHANGE_OF_MODEL_ID_.getValueIndex()];

        productServiceStub.subscribeUserOnModelPriceChange(
                SubscribeUserOnModelPriceChangeRequest.newBuilder()
                        .setModelId(modelId)
                        .setChatId(request.getChat().getChatId())
                        .build()
        );
        return SendMessage.builder()
                .text("Вы будете уведомлены при изменении цены")
                .chatId(request.getChat().getChatId())
                .build();
    }

    @Override
    public boolean condition(TelegramRequest request) {
        return request.getRequestType().equals(RequestType.CALLBACK)
                && request.getUpdate().getCallbackQuery().getData().startsWith(SUBSCRIBE_ON_PRICE_CHANGE_OF_MODEL_ID_.name());
    }
}
