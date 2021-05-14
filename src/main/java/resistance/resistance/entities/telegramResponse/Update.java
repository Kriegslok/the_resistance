package resistance.resistance.entities.telegramResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Update {
    private int update_id;
    private CallbackQuery callback_query;
    private Message message;


    public Update() {
        this.update_id = 0;
        this.callback_query = null;
        this.message = null;

    }

    public int getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(int update_id) {
        this.update_id = update_id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public CallbackQuery getCallback_query() {
        return callback_query;
    }

    @Override
    public String toString() {
        return "Update{" +
                "update_id=" + update_id +
                ", callback_query=" + callback_query +
                ", message=" + message +
                '}';
    }
}
