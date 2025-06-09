package kg.kazbekov.chatbot;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


public class InlineKeyboardMarkup {
    @Getter
    List<List<InlineKeyboardButton>> buttons;

    List<InlineKeyboardButton> row;

    public InlineKeyboardMarkup() {
        buttons = new ArrayList<>();
        row = new ArrayList<>();
    }

    public InlineKeyboardMarkup button(String text, String callBackData){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callBackData);
        row.add(button);
        return this;
    }

    public InlineKeyboardMarkup nextRow(){
        buttons.add(row);
        row = new ArrayList<>();
        return this;
    }


    public static InlineKeyboardMarkup builder(){
        return new InlineKeyboardMarkup();
    }

    public org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup build(){
        if(!row.isEmpty()){
            buttons.add(row);
        }
        return new org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup(buttons);
    }
}
