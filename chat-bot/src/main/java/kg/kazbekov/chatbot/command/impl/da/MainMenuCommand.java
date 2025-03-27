//package kg.kazbekov.chatbot.command.impl;
//
//import com.example.technoparkfoodorderbot.command.CallbackDataEnum;
//import com.example.technoparkfoodorderbot.command.Command;
//import com.example.technoparkfoodorderbot.handler.FormHandler;
//import com.example.technoparkfoodorderbot.util.InlineKeyboardMarkupHelper;
//import com.example.technoparkfoodorderbot.util.RequestType;
//import com.example.technoparkfoodorderbot.util.TelegramRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class MainMenuCommand implements Command {
//    private final FormHandler formHandler;
//    @Override
//    public Object handle(TelegramRequest request) {
//        if(request.getUser() == null){
//            formHandler.initForm(request);
//            return null;
//        }
//        var inlineKeyboardMarkup = InlineKeyboardMarkupHelper.builder()
//                .button("Меню", CallbackDataEnum.DISH_MENU.name())
//                .nextLine()
//                .button("Мои заказы", CallbackDataEnum.CLIENT_ORDERS.name())
//                .nextLine()
//                .button("Отметить заказ как доставлено", CallbackDataEnum.CONFIRMATION_OF_DELIVERY.name())
//                .nextLine()
//                .button("Отметить заказ как оплачено", CallbackDataEnum.ORDER_PAYMENT_SHOW_NOT_PAID.name())
//                .build();
//
//        return editMessage(request, "....", inlineKeyboardMarkup);
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        return request.getRequestType() == RequestType.CALLBACK
//                && request.getUpdate().getCallbackQuery().getData().startsWith(CallbackDataEnum.MAIN_MENU.toString());
//    }
//}
