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


    @Autowired
    public RClientConfig() {

    }

    public String getTelegramBotUrl() {
        return telegramBotUrl;
    }

    public String getToken() {
        return token;
    }

}
