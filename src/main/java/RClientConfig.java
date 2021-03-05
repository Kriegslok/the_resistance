public class RClientConfig {
    private final String telegramBotUrl;
    private final String token;

    public RClientConfig(String telegramBotUrl, String token) {
        this.telegramBotUrl = telegramBotUrl;
        this.token = token;
    }

    public String getTelegramBotUrl() {
        return telegramBotUrl;
    }

    public String getToken() {
        return token;
    }
}
