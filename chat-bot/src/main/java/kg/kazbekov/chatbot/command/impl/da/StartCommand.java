//package kg.kazbekov.chatbot.command.impl;
//
//import com.example.technoparkfoodorderbot.command.CallbackDataEnum;
//import com.example.technoparkfoodorderbot.command.Command;
//import com.example.technoparkfoodorderbot.handler.FormHandler;
//import com.example.technoparkfoodorderbot.service.UserService;
//import com.example.technoparkfoodorderbot.util.InlineKeyboardMarkupHelper;
//import com.example.technoparkfoodorderbot.util.RequestType;
//import com.example.technoparkfoodorderbot.util.TelegramRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class StartCommand implements Command {
//
//    private final UserService userService;
//    private final FormHandler formHandler;
//
//    @Override
//    public Object handle(TelegramRequest request) {
//        String greeting = "Привет, это бот для заказа обеда\n";
//        var inlineKeyboardMarkup = InlineKeyboardMarkupHelper.builder()
//                .button("Продолжить", CallbackDataEnum.MAIN_MENU.name())
//                .build();
//
//
//
//        var sendMessage = sendText(greeting, request);
//        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//        return sendMessage;
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        return request.getRequestType() == RequestType.COMMAND && request.getMessage().getText().startsWith("/start");
//    }
//}
