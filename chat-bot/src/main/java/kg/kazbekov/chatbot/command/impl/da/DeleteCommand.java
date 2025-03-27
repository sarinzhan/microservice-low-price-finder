//package kg.kazbekov.chatbot.command.impl;
//
//import com.example.technoparkfoodorderbot.command.CallbackDataEnum;
//import com.example.technoparkfoodorderbot.command.Command;
//import com.example.technoparkfoodorderbot.util.RequestType;
//import com.example.technoparkfoodorderbot.util.TelegramRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
//
//@Component
//@RequiredArgsConstructor
//public class DeleteCommand implements Command {
//
//    @Override
//    public Object handle(TelegramRequest request) {
//        DeleteMessage deleteMessage = new DeleteMessage();
//        deleteMessage.setMessageId(request.getMessage().getMessageId());
//        deleteMessage.setChatId(request.getChat().getChatId());
//        return deleteMessage;
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        return request.getRequestType() == RequestType.CALLBACK
//                && request.getUpdate().getCallbackQuery().getData().equals(CallbackDataEnum.DELETE.name());
//    }
//}
