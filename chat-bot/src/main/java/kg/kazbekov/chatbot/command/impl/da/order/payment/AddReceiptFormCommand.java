//package kg.kazbekov.chatbot.command.impl.order.payment;
//
//import com.example.technoparkfoodorderbot.command.CallbackDataEnum;
//import com.example.technoparkfoodorderbot.command.Command;
//import com.example.technoparkfoodorderbot.event.OrderMarkedAsPaidEvent;
//import com.example.technoparkfoodorderbot.model.Chat;
//import com.example.technoparkfoodorderbot.model.Order;
//import com.example.technoparkfoodorderbot.model.OrderPaymentStatus;
//import com.example.technoparkfoodorderbot.service.OrderService;
//import com.example.technoparkfoodorderbot.util.InlineKeyboardMarkupHelper;
//import com.example.technoparkfoodorderbot.util.RequestType;
//import com.example.technoparkfoodorderbot.util.TelegramRequest;
//import lombok.*;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class AddReceiptFormCommand implements Command {
//    private final OrderService orderService;
//    private final ApplicationEventPublisher applicationEventPublisher;
//    private final Map<Chat, ConversationState> activeChat = new HashMap<>();
//    enum ConversationState{
//        ASK_TO_SEND_RECEIPT, SAVE_RECEIPT;
//
//        @Setter
//        @Getter
//        private Long orderId;
//    }
//
//
//    @Override
//    public Object handle(TelegramRequest request) {
//        var conversationState = activeChat.get(request.getChat());
//        return switch (activeChat.get(request.getChat())){
//            case ASK_TO_SEND_RECEIPT -> {
//                var orderId = Long.valueOf(request.getUpdate().getCallbackQuery().getData().split("_")[4]);
//                conversationState.setOrderId(orderId);
//                conversationState = ConversationState.SAVE_RECEIPT;
//                activeChat.computeIfPresent(request.getChat(), (chat,state) -> ConversationState.SAVE_RECEIPT);
//                yield editMessage(request, "Отправьте файл/скриншот квитанции");
//            }
//            case SAVE_RECEIPT -> {
//                var userOrders = orderService.findById(conversationState.getOrderId());
//                if(userOrders == null){
//                    yield null;
//                }
//
//                String fileId = "";
//                Order.FileType fileType = null;
//
//                if(request.getUpdate().getMessage().hasPhoto()){
//                    fileId = request.getMessage().getPhoto().get(request.getMessage().getPhoto().size() - 1).getFileId();
//                    fileType = Order.FileType.PHOTO;
//                }else if(request.getMessage().hasDocument()){
//                    fileId = request.getMessage().getDocument().getFileId();
//                    fileType = Order.FileType.DOCUMENT;
//                }
//
//                userOrders.setOrderPaymentStatus(OrderPaymentStatus.WAITING_FOR_CONFIRMATION);
//                userOrders.setTelegramFileId(fileId);
//                userOrders.setFileType(fileType);
//                orderService.save(userOrders);
//
//                applicationEventPublisher.publishEvent(new OrderMarkedAsPaidEvent(this, userOrders));
//
//                yield sendText(
//                        "Оплата заказа #%s помечена как оплачен. Оплата будет проверена и подтверждена".formatted(conversationState.getOrderId()),
//                        request,
//                        InlineKeyboardMarkupHelper.builder().button("<-- вернуться назад", CallbackDataEnum.MAIN_MENU.name()).build()
//                );
//            }
//        };
//
//    }
//
////    public void processFile(String fileId, String fileName, Long orderId){
////        // Get file details from Telegram
////        GetFile getFile = new GetFile();
////        getFile.setFileId("AgACAgIAAxkBAAIDT2d8WdFJ6e-JwivlmwsSNUBC3PbJAAIw7jEbgt7gSxEHeutmGojFAQADAgADdwADNgQ");
////        File telegramFile = sender.internalExecute(getFile);
////
////        // Download the file
////        String fileUrl = "https://api.telegram.org/file/bot" + botToken + "/" + telegramFile.getFilePath();
////        try (InputStream inputStream = new URL(fileUrl).openStream()) {
////            byte[] fileBytes = inputStream.readAllBytes();
////
////            var order = orderService.findById(orderId);
////            order.setReceipt(fileBytes);
////            orderService.save(order);
////            // Save the file to the database
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//    @Override
//    public boolean condition(TelegramRequest request) {
//        if(activeChat.containsKey(request.getChat()))
//            return true;
//        if(request.getRequestType().equals(RequestType.CALLBACK)
//                && request.getUpdate().getCallbackQuery().getData().startsWith(CallbackDataEnum.ORDER_PAYMENT_ADD_RECEIPT.name())
//        ){
//            activeChat.put(request.getChat(), ConversationState.ASK_TO_SEND_RECEIPT);
//            return true;
//        }
//        return false;
//    }
//}
