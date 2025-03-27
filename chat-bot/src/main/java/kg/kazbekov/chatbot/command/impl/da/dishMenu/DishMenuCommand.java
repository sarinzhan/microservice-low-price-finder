//package kg.kazbekov.chatbot.command.impl.dishMenu;
//
//import kg.kazbekov.chatbot.util.RequestType;
//import kg.kazbekov.chatbot.util.TelegramRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Component
//@RequiredArgsConstructor
//public class DishMenuCommand implements Command {
//
//    private final DishService dishService;
//
//    @Override
//    public Object handle(TelegramRequest request) {
//        // inline keyboard
//        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//        keyboardMarkup.setKeyboard(rows);
//
//        var allDishes = dishService.findAll();
//
//        allDishes.forEach(dish -> {
//            var keyboard = createInlineButtons(dish.getName());
//            rows.add(keyboard);
//        });
//
//
//
//        List<InlineKeyboardButton> confirmInlineButton = new ArrayList<>();
//        var confirmButton = createButton("Сделать заказ", CallbackDataEnum.CONFIRM_ORDER.name());
//        confirmInlineButton.add(confirmButton);
//        rows.add(confirmInlineButton);
//
//        List<InlineKeyboardButton> backInlineRows = new ArrayList<>();
//        var returnBackButton = createButton("<-- Вернуться назад", CallbackDataEnum.MAIN_MENU.name());
//        backInlineRows.add(returnBackButton);
//        rows.add(backInlineRows);
//
////        inlineKeyboardContext.put(request.getUpdate().getMessage().getChatId().toString(),keyboardMarkup);
//
//        return editMessage(request,"Меню блюд",keyboardMarkup);
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        return request.getRequestType() == RequestType.CALLBACK
//                && request.getUpdate().getCallbackQuery().getData().startsWith(CallbackDataEnum.DISH_MENU.name());
//    }
//
//    private List<InlineKeyboardButton> createInlineButtons(String dishName){
//        var button = createButton(dishName, "dish_name_" + dishName);
////        var decreaseButton = createButton("-", "dish_decrease_" + dishName + "_0");
////        var quantityButton = createButton("0", "dish_quantity");
////        var increaseButton = createButton("+", "dish_increase_" + dishName + "_0");
//        var addBtn = createButton("Добавить", CallbackDataEnum.DISH_MENU_ADD +"_" + dishName);
//
//        List<InlineKeyboardButton> inlineButtons = new ArrayList<>();
//        inlineButtons.add(button);
////        inlineButtons.add(decreaseButton);
////        inlineButtons.add(quantityButton);
////        inlineButtons.add(increaseButton);
//        inlineButtons.add(addBtn);
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
