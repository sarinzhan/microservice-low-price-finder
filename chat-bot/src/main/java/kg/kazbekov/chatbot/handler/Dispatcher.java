package kg.kazbekov.chatbot.handler;

import jakarta.annotation.PostConstruct;
import kg.kazbekov.chatbot.interceptor.Interceptor;
import kg.kazbekov.chatbot.util.RequestType;
import kg.kazbekov.chatbot.util.TelegramRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Dispatcher {
    private final HandlerMapping handlerMapping;
    private final List<Interceptor> interceptors;
//    private final FormHandler formHandler;



    public SendMessage dispatch(Update update){
        log.info("Request accepted to dispatch");
        TelegramRequest telegramRequest = new TelegramRequest();
        telegramRequest.setUpdate(update);

        defineCommandType(telegramRequest);

        interceptors.forEach(interceptor -> interceptor.handle(telegramRequest));

//        if(formHandler.condition(telegramRequest)){
//            return formHandler.handle(telegramRequest);
//        }

        var command = handlerMapping.getCommand(telegramRequest);
        if(command != null){
            return command.handle(telegramRequest);
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Command not found");
        sendMessage.setChatId(telegramRequest.getChat().getChatId());
        return sendMessage;
    }

    public TelegramRequest defineCommandType(TelegramRequest request){
        var update = request.getUpdate();
        if(update.getCallbackQuery() != null){
            request.setRequestType(RequestType.CALLBACK);
            request.setMessage((Message) update.getCallbackQuery().getMessage());
        }else if(update.getMessage() != null){
            request.setRequestType(RequestType.COMMAND);
            request.setMessage(update.getMessage());
        }else if(update.getInlineQuery() != null){
            request.setRequestType(RequestType.INLINE_QUERY);
        }
        else {
            throw new RuntimeException("Request type didn't recognized");
        }
        return request;
    }
}
