//package kg.kazbekov.chatbot.command.impl;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class SubscribeNewOrderCommand implements Command {
//
//    private final ChatService chatService;
//
//    @Override
//    @Transactional
//    public Object handle(TelegramRequest request) {
//        var chatId = request.getChat().getId();
//        Chat chat = chatService.findById(chatId);
//        var isAdded = chat.getEventSubscription().add(new EventSubscription(chat, EventEnum.NEW_ORDER));
//        if(isAdded){
//            return sendText("Чат добавлен в список получателей новых заказов", request);
//        }
//        return sendText("Чат уже был добавлен в список получателей новых заказов",request);
//    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        var message = request.getUpdate().getMessage();
//        return message != null && message.getText().startsWith("/subscribe-new-order");
//    }
//}
