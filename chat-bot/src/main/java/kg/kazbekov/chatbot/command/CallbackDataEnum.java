package kg.kazbekov.chatbot.command;

import lombok.Getter;

@Getter
public enum CallbackDataEnum {
    SELECTED_MANUFACTURE_ID_(3),
    SELECTED_MODEL_ID_(3),
    SUBSCRIBE_ON_PRICE_CHANGE_OF_MODEL_ID_(7);

    private Integer valueIndex;

    CallbackDataEnum(Integer valueIndex) {
        this.valueIndex = valueIndex;
    }
}
