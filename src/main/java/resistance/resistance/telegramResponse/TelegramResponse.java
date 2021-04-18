package resistance.resistance.telegramResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TelegramResponse {
    private boolean ok;
    private Message result;

    public TelegramResponse() {
        this.ok = false;
        this.result = null;
    }
    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Message getResult() {
        return result;
    }

    public void setResult(Message result) {
        this.result = result;
    }
}
