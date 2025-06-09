package kg.kazbekov.chatbot.command.impl;

import kg.kazbekov.chatbot.InlineKeyboardMarkup;
import kg.kazbekov.chatbot.command.CallbackDataEnum;
import kg.kazbekov.chatbot.command.Command;
import kg.kazbekov.chatbot.util.RequestType;
import kg.kazbekov.chatbot.util.TelegramRequest;
import kg.kazbekov.productservice.grpc.ModelByManufactureRequest;
import kg.kazbekov.productservice.grpc.ProductServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static kg.kazbekov.chatbot.command.CallbackDataEnum.SELECTED_MANUFACTURE_ID_;

@Component
public class SelectModelCommand implements Command {

    @GrpcClient("productService")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;
    @Override
    public SendMessage handle(TelegramRequest request) {
        var manufacture = request.getUpdate().getCallbackQuery().getData().split("_")[SELECTED_MANUFACTURE_ID_.getValueIndex()];

        var modelByManufactureRequest = ModelByManufactureRequest.newBuilder().setManufactureId(manufacture).build();
        var modelsList = productServiceStub.getModelsByManufacture(modelByManufactureRequest).getModelsList();

        var inlineKeyboardMarkupBuilder = InlineKeyboardMarkup.builder();
        modelsList.forEach(model ->
                inlineKeyboardMarkupBuilder.button("%s (%d)".formatted(model.getName(), model.getQuantity()), CallbackDataEnum.SELECTED_MODEL_ID_ + model.getId()).nextRow()
        );

        return SendMessage.builder()
                .text("Выберите модель для %s".formatted(manufacture))
                .replyMarkup(inlineKeyboardMarkupBuilder.build())
                .chatId(request.getChat().getChatId())
                .build();
    }

    @Override
    public boolean condition(TelegramRequest request) {
        return request.getRequestType().equals(RequestType.CALLBACK)
                && request.getUpdate().getCallbackQuery().getData().startsWith(SELECTED_MANUFACTURE_ID_.toString());
    }
}
