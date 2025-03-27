//package kg.kazbekov.chatbot.command.impl.order.payment;
//
//import com.example.technoparkfoodorderbot.command.CallbackDataEnum;
//import com.example.technoparkfoodorderbot.command.Command;
//import com.example.technoparkfoodorderbot.event.OrderMarkedAsPaidEvent;
//import com.example.technoparkfoodorderbot.model.OrderPaymentStatus;
//import com.example.technoparkfoodorderbot.service.UserService;
//import com.example.technoparkfoodorderbot.util.InlineKeyboardMarkupHelper;
//import com.example.technoparkfoodorderbot.util.RequestType;
//import com.example.technoparkfoodorderbot.util.TelegramRequest;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class MarkAsPaidCommand implements Command {
//
//    private final UserService userService;
//    private final ApplicationEventPublisher applicationEventPublisher;
//
//    @Override
//    @Transactional
//    public Object handle(TelegramRequest request) {
//        var user = userService.findById(request.getUser().getId());
//        var userOrders = user.getOrders();
//        var orderId = request.getUpdate().getCallbackQuery().getData().split("_")[5];
//
//        var optionalNotPaidOrder = userOrders.stream()
//                .filter(order -> order.getId().equals(Long.valueOf(orderId)))
//                .findFirst();
//
//        if(optionalNotPaidOrder.isEmpty()){
//            return null;
//        }
//
//        var notPaidOrder = optionalNotPaidOrder.get();
//        notPaidOrder.setOrderPaymentStatus(OrderPaymentStatus.WAITING_FOR_CONFIRMATION);
//        applicationEventPublisher.publishEvent(new OrderMarkedAsPaidEvent(this, notPaidOrder));
//
//        return editMessage(
//                request,
//                "Оплата заказа #%s помечена как оплачен. Оплата будет проверена и подтверждена".formatted(orderId),
//                InlineKeyboardMarkupHelper.builder().button("<-- вернуться назад", CallbackDataEnum.MAIN_MENU.name()).build()
//        );
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        return request.getRequestType().equals(RequestType.CALLBACK)
//                && request.getUpdate().getCallbackQuery().getData().startsWith(CallbackDataEnum.MARK_ORDER_PAYMENT_AS_PAID.name());
//    }
//}
