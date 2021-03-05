


import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class.getName());
    static String telegramBotUrl = "https://api.telegram.org/";
    static String token = "bot1535167887:AAHspHwJURI66rdMqxim_xYxjMGrKQWngKk/";


    public static void main(String[] args) {
        RClientConfig config = new RClientConfig(telegramBotUrl, token);
        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
        RClient client = new RClient(asyncHttpClient, config);
        client.getUpdates();
        //client.printMessages();
        client.getUpdates();
        //client.printMessages();


    }
}
