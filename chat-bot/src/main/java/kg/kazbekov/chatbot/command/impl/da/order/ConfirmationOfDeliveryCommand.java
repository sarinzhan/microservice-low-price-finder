//package kg.kazbekov.chatbot.command.impl.order;
//
//import com.example.technoparkfoodorderbot.command.CallbackDataEnum;
//import com.example.technoparkfoodorderbot.command.Command;
//import com.example.technoparkfoodorderbot.model.OrderStatus;
//import com.example.technoparkfoodorderbot.service.OrderService;
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
//public class ConfirmationOfDeliveryCommand implements Command {
//    private final OrderService orderService;
//    private final UserService userService;
//
//    @Override
//    @Transactional
//    public Object handle(TelegramRequest request) {
//        var userId = request.getUser().getId();
//        var userOrders = userService.findById(userId).getOrders();
//        var confirmedUserOrders = userOrders.stream()
//                .filter(order -> order.getStatus() == OrderStatus.CONFIRMED)
//                .toList();
//        var inlineKeyboardMarkup = InlineKeyboardMarkupHelper.init();
//        for (var confirmedUserOrder : confirmedUserOrders) {
//            inlineKeyboardMarkup.button(
//                    "Номер: #%d".formatted(confirmedUserOrder.getId()),
//                    CallbackDataEnum.MARK_ORDER_AS_DELIVERED +"_"+ confirmedUserOrder.getId().toString()
//            ).nextLine();
//        }
//        inlineKeyboardMarkup.button("<-- вернуться назад", CallbackDataEnum.MAIN_MENU.name());
//       return editMessage(request,"Выберите заказ для подтвреждения доставки", inlineKeyboardMarkup.build());
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        return request.getRequestType().equals(RequestType.CALLBACK)
//                && request.getUpdate().getCallbackQuery().getData().equals(CallbackDataEnum.CONFIRMATION_OF_DELIVERY.name());
//    }
//}
