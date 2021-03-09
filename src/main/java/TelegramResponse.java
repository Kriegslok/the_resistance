public class TelegramResponse {
    private String ok;
    private Result result;

    public TelegramResponse(String ok, Result result) {
        this.ok = null;
        this.result = null;
    }

    public String isOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
