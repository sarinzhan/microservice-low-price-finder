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
//public class MarkOrderAsDeliveredCommand implements Command {
//    private final UserService userService;
//    private final OrderService orderService;
//
//    @Override
//    @Transactional
//    public Object handle(TelegramRequest request) {
//        var callbackQuery = request.getUpdate().getCallbackQuery();
//        var orderId = callbackQuery.getData().split("_")[4];
//        var orderToMarkAsDelivered = orderService.findById(Long.valueOf(orderId));
//        orderToMarkAsDelivered.setStatus(OrderStatus.DELIVERED);
//        return editMessage(
//                request,
//                "Заказ #%s был отмечен как доставлен".formatted(orderId),
//                InlineKeyboardMarkupHelper.builder().button("<-- вернуться назад", CallbackDataEnum.MAIN_MENU.name()).build()
//        );
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        return request.getRequestType().equals(RequestType.CALLBACK)
//                && request.getUpdate().getCallbackQuery().getData().startsWith(CallbackDataEnum.MARK_ORDER_AS_DELIVERED.name());
//    }
//}
