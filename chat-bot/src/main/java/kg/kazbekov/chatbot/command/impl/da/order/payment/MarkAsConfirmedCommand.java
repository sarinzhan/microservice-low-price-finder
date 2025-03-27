//package kg.kazbekov.chatbot.command.impl.order.payment;
//
//import com.example.technoparkfoodorderbot.Sender;
//import com.example.technoparkfoodorderbot.command.CallbackDataEnum;
//import com.example.technoparkfoodorderbot.command.Command;
//import com.example.technoparkfoodorderbot.model.OrderPaymentStatus;
//import com.example.technoparkfoodorderbot.service.OrderService;
//import com.example.technoparkfoodorderbot.service.UserService;
//import com.example.technoparkfoodorderbot.util.RequestType;
//import com.example.technoparkfoodorderbot.util.ResponseHelper;
//import com.example.technoparkfoodorderbot.util.TelegramRequest;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@RequiredArgsConstructor
//public class MarkAsConfirmedCommand implements Command {
//    private final UserService userService;
//    private final OrderService orderService;
//    private final Sender sender;
//
//    @Override
//    @Transactional
//    public Object handle(TelegramRequest request) {
//        var callbackQuery = request.getUpdate().getCallbackQuery();
//        var orderId = callbackQuery.getData().split("_")[5];
//        var order = orderService.findById(Long.valueOf(orderId));
//        order.setOrderPaymentStatus(OrderPaymentStatus.CONFIRMED);
//
//        sender.internalExecute(ResponseHelper.sendText("Оплата заказа #%s подтверждена.".formatted(orderId), order.getUser().getTelegramId()));
//
//        return editCaption(request, "Подтверждение оплаты заказа #%s подтверждено".formatted(orderId));
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        return request.getRequestType().equals(RequestType.CALLBACK)
//                && request.getUpdate().getCallbackQuery().getData().startsWith(CallbackDataEnum.MARK_ORDER_PAYMENT_AS_CONFIRMED.name());
//    }
//}