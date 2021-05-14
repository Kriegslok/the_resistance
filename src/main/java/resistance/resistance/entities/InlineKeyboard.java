package resistance.resistance.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InlineKeyboard {
    @JsonSerialize
    List<List<TelegramInlineButton>>inline_keyboard = new ArrayList<>();

    public InlineKeyboard(TelegramInlineButton  ... inlineButton) {
        List<TelegramInlineButton> buttons = Arrays.asList(inlineButton);
        inline_keyboard.add(buttons);
    }
}
