package resistance.resistance;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RClientConfig {
    @Value("${telegramEventLoop.telegramBotUrl}")
    private  String telegramBotUrl;
    @Value("${telegramEventLoop.token}")
    private String token;
    private AsyncHttpClient asyncHttpClient;

//    @Autowired
//    public RClientConfig(String telegramBotUrl, String token) {
//        this.telegramBotUrl = telegramBotUrl;
//        this.token = token;
//        asyncHttpClient = new DefaultAsyncHttpClient();
//    }

    @Autowired
    public RClientConfig() {
        asyncHttpClient = new DefaultAsyncHttpClient();
    }

    public String getTelegramBotUrl() {
        return telegramBotUrl;
    }

    public String getToken() {
        return token;
    }

    public AsyncHttpClient getAsyncHttpClient() {
        return asyncHttpClient;
    }
}
