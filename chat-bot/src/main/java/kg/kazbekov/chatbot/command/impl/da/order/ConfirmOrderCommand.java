//package kg.kazbekov.chatbot.command.impl.order;
//
//import com.example.technoparkfoodorderbot.Sender;
//import com.example.technoparkfoodorderbot.command.CallbackDataEnum;
//import com.example.technoparkfoodorderbot.command.Command;
//import com.example.technoparkfoodorderbot.config.BotInitialization;
//import com.example.technoparkfoodorderbot.model.Order;
//import com.example.technoparkfoodorderbot.model.OrderDetails;
//import com.example.technoparkfoodorderbot.service.DishService;
//import com.example.technoparkfoodorderbot.service.OrderService;
//import com.example.technoparkfoodorderbot.util.InlineKeyboardMarkupHelper;
//import com.example.technoparkfoodorderbot.util.TelegramRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class ConfirmOrderCommand implements Command {
//
//    private final OrderService orderService;
//    private final DishService dishService;
//
//
//    @Override
//    public Object handle(TelegramRequest request) {
//
//        var update = request.getUpdate();
//
//        var inlineKeyboards = request.getMessage().getReplyMarkup().getKeyboard();
//
//        Map<String, Integer> dishQuantityMap = new HashMap<>();
//        inlineKeyboards.stream()
//                .filter(inlineKeyboardButton -> inlineKeyboardButton.size() == 4)
//                .forEach(inlineKeyboardButton -> {
//                    var dishName = inlineKeyboardButton.get(0).getText();
//                    var quantity = Integer.parseInt(inlineKeyboardButton.get(2).getText());
//
//                    if(quantity > 0) {
//                        dishQuantityMap.put(dishName, quantity);
//                    }
//                });
//
//        Order order = new Order();
//        order.setUser(request.getUser());
//        var orderDetails = order.getOrderDetails();
//
//        for(var entry : dishQuantityMap.entrySet()) {
//            var dish = dishService.findByName(entry.getKey());
//            var orderDetail = new OrderDetails(order,dish, entry.getValue());
//            orderDetails.add(orderDetail);
//        }
//
//        var orderId = orderService.addNewOrder(order).getId();
//
//
//        var inlineKeyboardMarkup = InlineKeyboardMarkupHelper.builder()
//                .button("<-- Вернуться", CallbackDataEnum.MAIN_MENU.name())
//                .build();
//
//        return editMessage(request, "Ваш заказ #%d на обработке. Ожидайте подтверждения.".formatted(orderId), inlineKeyboardMarkup);
//
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        var callbackQuery = request.getUpdate().getCallbackQuery();
//        return callbackQuery != null &&  callbackQuery.getData().startsWith(CallbackDataEnum.CONFIRM_ORDER.name());
//    }
//}
