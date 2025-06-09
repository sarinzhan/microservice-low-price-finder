package kg.kazbekov.chatbot.command.impl;

import kg.kazbekov.chatbot.command.Command;
import kg.kazbekov.chatbot.util.RequestType;
import kg.kazbekov.chatbot.util.TelegramRequest;
import kg.kazbekov.productservice.grpc.ProductByModelRequest;
import kg.kazbekov.productservice.grpc.ProductServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static kg.kazbekov.chatbot.command.CallbackDataEnum.*;

@Component
public class ProductCommand implements Command {

    @GrpcClient("productService")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

    @Override
    public SendMessage handle(TelegramRequest request) {
        var modelId = request.getUpdate().getCallbackQuery().getData().split("_")[SELECTED_MODEL_ID_.getValueIndex()];

        var requestByModel = ProductByModelRequest.newBuilder().setModelId(modelId).build();
        var productList = productServiceStub.getProductByModel(requestByModel).getProductsList();

        if(productList.isEmpty()){
            return SendMessage.builder()
                    .text("Данные отсутствуют для выбранной модели")
                    .chatId(request.getChat().getChatId())
                    .build();
        }


        var inlineKeyboardMarkupBuilder = InlineKeyboardMarkup.builder();
        for (var product : productList) {
            inlineKeyboardMarkupBuilder.keyboardRow(
                    List.of(
                            InlineKeyboardButton.builder()
                                    .text("%d сом %d/%d GB".formatted(product.getPrice(), product.getRam(), product.getStorage()))
                                    .url(product.getUrl())
                                    .build()
                    )
            );
        }
        inlineKeyboardMarkupBuilder.keyboardRow(
                List.of(
                        InlineKeyboardButton.builder()
                                .text("Уведомить при изменении цен")
                                .callbackData(SUBSCRIBE_ON_PRICE_CHANGE_OF_MODEL_ID_.name()+modelId)
                                .build()
                )
        );
        return SendMessage.builder()
                .text("Данные для %s".formatted(modelId))
                .replyMarkup(inlineKeyboardMarkupBuilder.build())
                .chatId(request.getChat().getChatId())
                .build();
    }

    @Override
    public boolean condition(TelegramRequest request) {
        return request.getRequestType().equals(RequestType.CALLBACK)
                && request.getUpdate().getCallbackQuery().getData().startsWith(SELECTED_MODEL_ID_.toString());
    }
}
