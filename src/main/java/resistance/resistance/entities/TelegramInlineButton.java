package resistance.resistance.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public class TelegramInlineButton {
    @JsonSerialize
    String text;
   @JsonSerialize
    String callback_data;


    public TelegramInlineButton(String text, String callbackData) {
        this.text = text;
        this.callback_data = callbackData;
    }

    public TelegramInlineButton(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCallback_data() {
        return callback_data;
    }

    public void setCallback_data(String callback_data) {
        this.callback_data = callback_data;
    }
}


