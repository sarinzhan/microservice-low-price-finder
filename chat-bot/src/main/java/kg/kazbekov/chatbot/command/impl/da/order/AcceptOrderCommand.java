//package kg.kazbekov.chatbot.command.impl.order;
//
//import com.example.technoparkfoodorderbot.config.BotInitialization;
//import com.example.technoparkfoodorderbot.command.Command;
//import com.example.technoparkfoodorderbot.model.OrderStatus;
//import com.example.technoparkfoodorderbot.service.OrderService;
//import com.example.technoparkfoodorderbot.util.TelegramRequest;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//
//import java.util.concurrent.CompletableFuture;
//
//
//@Component
//@RequiredArgsConstructor
//public class AcceptOrderCommand implements Command {
//
//    private final OrderService orderService;
//    private final ApplicationEventPublisher eventPublisher;
//
//
//    @Autowired
//    @Lazy
//    private BotInitialization foodOrderBot;
//
//    @Override
//    @Transactional
//    public Object handle(TelegramRequest request) {
//        var callbackQuery = request.getUpdate().getCallbackQuery();
//        var orderId = callbackQuery.getData().split("_")[2];
//
//        var order = orderService.changeStatus(Long.valueOf(orderId), OrderStatus.CONFIRMED);
//
//        if(order == null){
//            CompletableFuture.runAsync(() -> foodOrderBot.exec(deleteMessage(request)));
//            return sendText("ERROR: Order not found", request);
//        }
//
//        var inlineKeyboardMarkup = callbackQuery.getMessage().getReplyMarkup();
//        var keyboard = inlineKeyboardMarkup.getKeyboard();
//        keyboard.get(0).get(0).setText("Принят");
//        var responseMarkup = editMarkup(request);
//        responseMarkup.setReplyMarkup(inlineKeyboardMarkup);
//
////        eventPublisher.publishEvent(new OrderAcceptedEvent(this, order));
//
//        return responseMarkup;
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        var callbackQuery = request.getUpdate().getCallbackQuery();
//        return callbackQuery != null && callbackQuery.getData().startsWith("order_accept_");
//    }
//
//
//    private InlineKeyboardButton createButton(String text, String callBackData){
//        InlineKeyboardButton button = new InlineKeyboardButton();
//        button.setText(text);
//        button.setCallbackData(callBackData);
//        return button;
//    }
//}
