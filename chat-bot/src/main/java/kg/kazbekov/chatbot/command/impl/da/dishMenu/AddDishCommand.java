//package kg.kazbekov.chatbot.command.impl.dishMenu;
//
//import kg.kazbekov.chatbot.command.Command;
//import kg.kazbekov.chatbot.util.InlineKeyboardMarkupHelper;
//import kg.kazbekov.chatbot.util.RequestType;
//import kg.kazbekov.chatbot.util.TelegramRequest;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AddDishCommand implements Command {
//    @Override
//    public Object handle(TelegramRequest request) {
//        var inlineKeyboardMarkup = request.getMessage().getReplyMarkup();
//        var callBackData = request.getUpdate().getCallbackQuery().getData();
//        var dishName = callBackData.split("_")[3];
//        var optionalInlineKeyboardButtons = inlineKeyboardMarkup.getKeyboard().stream()
//                .filter(inlineKeyboardButtons -> inlineKeyboardButtons.get(0).getText().equals(dishName))
//                .findFirst();
//
//        if(optionalInlineKeyboardButtons.isPresent()){
//            var inlineKeyboard = InlineKeyboardMarkupHelper.builder()
//                    .button(dishName, " ")
//                    .button("-", "dish_decrease_" + dishName)
//                    .button("1", "dish_quantity_" + dishName)
//                    .button("+", "dish_increase_" + dishName)
//                    .build().getKeyboard().get(0);
//            optionalInlineKeyboardButtons.get().clear();
//            optionalInlineKeyboardButtons.get().addAll(inlineKeyboard);
//        }
//
//        var editMessageReplyMarkup = editMarkup(request);
//        editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);
//        return editMessageReplyMarkup;
//    }
//
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        return request.getRequestType().equals(RequestType.CALLBACK)
//                && request.getUpdate().getCallbackQuery().getData().startsWith(CallbackDataEnum.DISH_MENU_ADD.name());
//    }
//}
