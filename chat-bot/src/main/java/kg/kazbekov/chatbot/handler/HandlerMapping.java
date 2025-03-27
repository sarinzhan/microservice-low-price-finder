package kg.kazbekov.chatbot.handler;

import jakarta.annotation.PostConstruct;
import kg.kazbekov.chatbot.command.Command;
import kg.kazbekov.chatbot.util.TelegramRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HandlerMapping {

    private final List<Command> commands;

    @PostConstruct
    public void postConstruct(){
        log.info("Added {} commands", commands.size());
    }


    public Command getCommand(TelegramRequest telegramRequest){
        var optionalCommand = commands.stream()
                .filter(command -> command.condition(telegramRequest))
                .findFirst();

        return optionalCommand.orElse(null);

    }


}
