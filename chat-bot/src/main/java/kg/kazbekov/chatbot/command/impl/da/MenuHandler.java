//package kg.kazbekov.chatbot.command.impl;
//
//import com.example.technoparkfoodorderbot.command.CommandHandler;
//import com.example.technoparkfoodorderbot.command.CommandType;
//import com.example.technoparkfoodorderbot.command.InlineKeyboardContext;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Component
//public class MenuHandler{
//
//    private final InlineKeyboardContext inlineKeyboardContext;
//
//    public MenuHandler(InlineKeyboardContext inlineKeyboardContext) {
//        this.inlineKeyboardContext = inlineKeyboardContext;
//    }
//
//    List<String> dishes = new ArrayList<>();
//
//    {
//        dishes.add("Плов");
//        dishes.add("Манты");
//        dishes.add("Рис");
//        dishes.add("Картошка");
//    }
//
//   // @Override
//    public CommandType getCommandType() {
//        return CommandType.COMMAND;
//    }
//
//    //@Override
//    public SendMessage getAnswer(Update update) {
//
//        // Create the inline keyboard
//        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//        keyboardMarkup.setKeyboard(rows);
//
//        dishes.forEach(dish -> {
//            var keyboard = createInlineButtons(dish);
//            rows.add(keyboard);
//        });
//
//        List<InlineKeyboardButton> confirmInlineButton = new ArrayList<>();
//        var confirmButton = createButton("ПОДТВЕРДИТЬ", "Confirm");
//        confirmInlineButton.add(confirmButton);
//        rows.add(confirmInlineButton);
//
//        inlineKeyboardContext.put(update.getMessage().getChatId().toString(),keyboardMarkup);
//
//        // Create the message with the inline keyboard
//        SendMessage message = new SendMessage();
//        message.setChatId(update.getMessage().getChatId());
//        message.setText("Please select dishes");
//        message.setReplyMarkup(keyboardMarkup);
//        return message;
//    }
//
//    private List<InlineKeyboardButton> createInlineButtons(String dishName){
//        var button = createButton(dishName, "dish_name_" + dishName);
//        var decreaseButton = createButton("-", "dish_decrease_" + dishName + "_0");
//        var quantityButton = createButton("0", "dish_quantity");
//        var increaseButton = createButton("+", "dish_increase_" + dishName + "_0");
//
//        List<InlineKeyboardButton> inlineButtons = new ArrayList<>();
//        inlineButtons.add(button);
//        inlineButtons.add(decreaseButton);
//        inlineButtons.add(quantityButton);
//        inlineButtons.add(increaseButton);
//
//        return inlineButtons;
//    }
//
//    private InlineKeyboardButton createButton(String text, String callBackData){
//        InlineKeyboardButton button = new InlineKeyboardButton();
//        button.setText(text);
//        button.setCallbackData(callBackData);
//       return button;
//    }
//}
