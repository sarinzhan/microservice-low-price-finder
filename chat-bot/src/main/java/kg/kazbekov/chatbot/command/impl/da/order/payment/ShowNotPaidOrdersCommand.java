//package kg.kazbekov.chatbot.command.impl.order.payment;
//
//import com.example.technoparkfoodorderbot.command.CallbackDataEnum;
//import com.example.technoparkfoodorderbot.command.Command;
//import com.example.technoparkfoodorderbot.model.Order;
//import com.example.technoparkfoodorderbot.model.OrderPaymentStatus;
//import com.example.technoparkfoodorderbot.service.UserService;
//import com.example.technoparkfoodorderbot.util.InlineKeyboardMarkupHelper;
//import com.example.technoparkfoodorderbot.util.RequestType;
//import com.example.technoparkfoodorderbot.util.TelegramRequest;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class ShowNotPaidOrdersCommand implements Command {
//
//    private final UserService userService;
//
//    @Override
//    @Transactional
//    public Object handle(TelegramRequest request) {
//        var user = userService.findById(request.getUser().getId());
//        var userOrders = user.getOrders();
//        var userNotPaidOrders = userOrders.stream()
//                .filter(order -> order.getOrderPaymentStatus().equals(OrderPaymentStatus.NOT_PAID) || order.getOrderPaymentStatus().equals(OrderPaymentStatus.PAID))
//                .toList();
//
//        var keyboard = InlineKeyboardMarkupHelper.init();
//
//        for (Order userNotPaidOrder : userNotPaidOrders) {
//            keyboard.button("Номер: #%d".formatted(userNotPaidOrder.getId()), CallbackDataEnum.ORDER_PAYMENT_ADD_RECEIPT.name() + "_" + userNotPaidOrder.getId());
//            keyboard.nextLine();
//        }
//        keyboard.button("<-- вернуться назад", CallbackDataEnum.MAIN_MENU.name());
//
//        if(userNotPaidOrders.isEmpty()){
//            return editMessage(request, "У вас отсутствуют заказы где необходимо подтвердить оплату", keyboard.build());
//        }
//
//        return editMessage(request,"Выберите заказ для подтверждения оплаты", keyboard.build());
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        return request.getRequestType().equals(RequestType.CALLBACK)
//                && request.getUpdate().getCallbackQuery().getData().startsWith(CallbackDataEnum.ORDER_PAYMENT_SHOW_NOT_PAID.name());
//    }
//}
