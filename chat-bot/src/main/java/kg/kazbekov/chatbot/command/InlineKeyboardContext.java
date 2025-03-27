package kg.kazbekov.chatbot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashMap;
import java.util.Map;

@Component
public class InlineKeyboardContext {
    private Map<String, InlineKeyboardMarkup> chatInstanceAndInlineKeyboardMap;

    public InlineKeyboardContext() {
        chatInstanceAndInlineKeyboardMap = new HashMap<>();
    }

    public InlineKeyboardMarkup getInlineKeyboardMarkup(String chatInstance){
        if(chatInstanceAndInlineKeyboardMap.containsKey(chatInstance)){
            return chatInstanceAndInlineKeyboardMap.get(chatInstance);
        }
        return null;
    }

    public void put(String chatInstance,InlineKeyboardMarkup markup){
        chatInstanceAndInlineKeyboardMap.put(chatInstance, markup);
    }
}
