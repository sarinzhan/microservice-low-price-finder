//package kg.kazbekov.chatbot.command.impl;
//
//import com.example.technoparkfoodorderbot.FoodOrderBot;
//import com.example.technoparkfoodorderbot.command.CommandHandler;
//import com.example.technoparkfoodorderbot.command.CommandType;
//import com.example.technoparkfoodorderbot.model.Order;
//import com.example.technoparkfoodorderbot.repository.OrderRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class OrderHandler implements CommandHandler {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    @Lazy
//    private FoodOrderBot foodOrderBot;
//
//    @Override
//    public CommandType getCommandType() {
//        return null;
//    }
//
//    @Override
//    public SendMessage getAnswer(Update update) {
//        var orders = orderRepository.getOrders();
//
//        for(var order : orders) {
//            String orderOuput = "Имя пользователя: " + order.getUsername() + "\n";
//            var dishAndQuantity = order.getDishQuantityMap().entrySet().stream().map(entry -> "   " + entry.getKey() + ": " + entry.getValue()).collect(Collectors.joining("\n"));
//            orderOuput += dishAndQuantity;
//
//            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//            List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//            keyboardMarkup.setKeyboard(rows);
//
//            List<InlineKeyboardButton> row = new ArrayList<>();
//            var button = createButton("Подтвердить", "order_confirm_" + order.getId());
//            row.add(button);
//            rows.add(row);
//
//            SendMessage message = new SendMessage();
//            message.setChatId(update.getMessage().getChatId());
//            message.setText(orderOuput);
//            message.setReplyMarkup(keyboardMarkup);
//            try {
//                foodOrderBot.execute(message);
//            } catch (TelegramApiException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return null;
//    }
//
//    private InlineKeyboardButton createButton(String text, String callBackData){
//        InlineKeyboardButton button = new InlineKeyboardButton();
//        button.setText(text);
//        button.setCallbackData(callBackData);
//        return button;
//    }
//}
