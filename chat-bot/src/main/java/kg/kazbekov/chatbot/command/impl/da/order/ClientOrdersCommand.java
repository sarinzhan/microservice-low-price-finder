//package kg.kazbekov.chatbot.command.impl.order;
//
//import com.example.technoparkfoodorderbot.command.CallbackDataEnum;
//import com.example.technoparkfoodorderbot.command.Command;
//import com.example.technoparkfoodorderbot.model.Order;
//import com.example.technoparkfoodorderbot.service.UserService;
//import com.example.technoparkfoodorderbot.util.InlineKeyboardMarkupHelper;
//import com.example.technoparkfoodorderbot.util.RequestType;
//import com.example.technoparkfoodorderbot.util.TelegramRequest;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.Comparator;
//
//@Component
//@RequiredArgsConstructor
//public class ClientOrdersCommand implements Command {
//
//    private final UserService userService;
//
//    @Override
//    @Transactional
//    public Object handle(TelegramRequest request) {
//        var user = userService.findById(request.getUser().getId());
//        var userOrders = user.getOrders();
//        StringBuilder output = new StringBuilder();
//        var sortedUserOrder = userOrders.stream()
//                .sorted(Comparator.comparing(Order::getCreatedAt)).toList();
//        for(var order : sortedUserOrder){
//            output.append("Номер: #%d\nСтатус: %s\nСписок блюд и их количество:\n".formatted(order.getId(), order.getStatus().getDescription()));
//
//            order.getOrderDetails().forEach(orderDetail -> {
//                output.append("\t\t\t- %s: %d\n".formatted(orderDetail.getDish().getName(), orderDetail.getQuantity()));
//            });
//            output.append("------------------------------------------------------------\n");
//        }
//        var returnBackButton = InlineKeyboardMarkupHelper.builder().button("<-- вернуться назад", CallbackDataEnum.MAIN_MENU.name()).build();
//        String outputText = output.toString().isEmpty() ? "Заказы отсутствуют" : output.toString();
//        return editMessage(request, outputText, returnBackButton);
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        return request.getRequestType() == RequestType.CALLBACK
//                && request.getUpdate().getCallbackQuery().getData().equals(CallbackDataEnum.CLIENT_ORDERS.name());
//    }
//}
