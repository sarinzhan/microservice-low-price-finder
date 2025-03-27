//package kg.kazbekov.chatbot.command.impl;
//
//import com.example.technoparkfoodorderbot.command.CallbackDataEnum;
//import com.example.technoparkfoodorderbot.command.Command;
//import com.example.technoparkfoodorderbot.util.InlineKeyboardMarkupHelper;
//import com.example.technoparkfoodorderbot.util.TelegramRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
//import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//
//import java.util.List;
//
//@Component
//public class DishQuantityCommand implements Command {
//
//    @Override
//    public Object handle(TelegramRequest request) {
//        var callbackQuery = request.getUpdate().getCallbackQuery();
//        var callbackData = callbackQuery.getData();
//        var update = request.getUpdate();
//        String [] dataArr = callbackData.split("_");
//
//
//            // updating
//        var inlineKeyboards = callbackQuery.getMessage().getReplyMarkup().getKeyboard();
//            inlineKeyboards.forEach(inlineKeyboardButton -> {
//                if (inlineKeyboardButton.get(0).getText().equals(dataArr[2])) {
//                    var oldDishQuantity = Integer.parseInt(inlineKeyboardButton.get(2).getText());
//                    if (callbackData.startsWith("dish_decrease") && oldDishQuantity > 1) {
//                        inlineKeyboardButton.get(2).setText(String.valueOf(oldDishQuantity - 1));
//                    } else if (callbackData.startsWith("dish_increase")) {
//                        inlineKeyboardButton.get(2).setText(String.valueOf(oldDishQuantity + 1));
//                    }else {
//                        inlineKeyboardButton.clear();
//                        var inlineKeyboard = InlineKeyboardMarkupHelper.builder()
//                                .button(dataArr[2], "dish_name_" + dataArr[2])
//                                .button("Добавить", CallbackDataEnum.DISH_MENU_ADD + "_" + dataArr[2])
//                                .build().getKeyboard().get(0);
//                        inlineKeyboardButton.addAll(inlineKeyboard);
//                    }
//                }
//            });
//
//            EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
//            editMarkup.setChatId(callbackQuery.getMessage().getChatId().toString());
//            editMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
//            editMarkup.setReplyMarkup(new InlineKeyboardMarkup(inlineKeyboards));
//            return editMarkup;
//
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        var callbackQuery = request.getUpdate().getCallbackQuery();
//        return callbackQuery != null &&
//                (callbackQuery.getData().startsWith("dish_increase") || callbackQuery.getData().startsWith("dish_decrease"));
//    }
//}
