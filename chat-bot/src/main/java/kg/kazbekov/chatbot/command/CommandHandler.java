package kg.kazbekov.chatbot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {

    //тип комманды обработаваемый ботом
    CommandType getCommandType();

    // условие для обработки команды
    // boolean condition(User user, ClassifiedUpdate update);

    SendMessage getAnswer(Update update);
}
