package resistance.resistance.entities.telegramResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import resistance.resistance.entities.ReplyMarkup;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {


    private int message_id;
    private From from;
    private Chat chat;
    private int date;
    private String text;
    private ReplyMarkup reply_markup;

    public Message() {
        this.message_id = 0;
        this.from = null;
        this.chat = null;
        this.date = 0;
        this.text = null;
        this.reply_markup = null;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    @Override
    public String toString() {
        return (chat.getId() + " " + message_id + " " + text);
    }
}
