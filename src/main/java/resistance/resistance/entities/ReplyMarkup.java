package resistance.resistance.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

public class ReplyMarkup {
    @JsonSerialize
 InlineKeyboard reply_markup;

    public ReplyMarkup(TelegramInlineButton  ... inlineButton) {
        this.reply_markup = new InlineKeyboard(inlineButton);
    }
}
