package kg.kazbekov.chatbot.util;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class InlineKeyboardMarkupHelper {
    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private List<List<InlineKeyboardButton>> rows;
    private List<InlineKeyboardButton> row;


    public static InlineKeyboardMarkupHelper init(){
        var inlineKeyboard = new InlineKeyboardMarkupHelper();
        inlineKeyboard.inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboard.rows = new ArrayList<>();
        inlineKeyboard.inlineKeyboardMarkup.setKeyboard(inlineKeyboard.rows);
        inlineKeyboard.row = new ArrayList<>();
        return inlineKeyboard;
    }

    public static InlineKeyboardButton createButton(String text, String callbackData){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }




    public static InlineKeyboardMarkupHelper builder(){
        return init();
    }
    public InlineKeyboardMarkupHelper button(String text, String callbackData){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        row.add(button);
        return this;
    }
    public InlineKeyboardMarkupHelper nextLine(){
        rows.add(row);
        row = new ArrayList<>();
        return this;
    }

    public InlineKeyboardMarkup build(){
        if(row.size() > 0){
            rows.add(row);
        }
        return inlineKeyboardMarkup;
    }



}
