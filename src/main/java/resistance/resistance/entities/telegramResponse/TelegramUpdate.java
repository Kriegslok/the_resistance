package resistance.resistance.entities.telegramResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelegramUpdate {
    private boolean ok;
    private List<Update> result;

    public TelegramUpdate() {
        this.ok = false;
        this.result = null;
    }
    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<Update> getResult() {
        return result;
    }

    public void setResult(List<Update> result) {
        this.result = result;
    }
}
