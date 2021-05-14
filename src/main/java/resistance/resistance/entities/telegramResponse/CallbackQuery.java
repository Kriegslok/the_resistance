package resistance.resistance.entities.telegramResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackQuery {
    private long id;
    private From from;
    private Message message;
    private long chat_instance;
    private String data;

    public long getId() {
        return id;
    }

    public From getFrom() {
        return from;
    }

    public Message getMessage() {
        return message;
    }

    public long getChat_instance() {
        return chat_instance;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "CallbackQuery{" +
                "id=" + id +
                ", from=" + from +
                ", message=" + message +
                ", chat_instance=" + chat_instance +
                ", data='" + data + '\'' +
                '}';
    }
}
