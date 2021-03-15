package telegramResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import telegramResponse.Message;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Update {
    private int update_id;
    private Message message;

    public Update() {
        this.update_id = 0;
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
}
