


import com.fasterxml.jackson.core.JsonProcessingException;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import telegramResponse.Message;
import telegramResponse.Update;

import java.util.List;


public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class.getName());
    static String telegramBotUrl = "https://api.telegram.org/";
    static String token = "bot1535167887:AAHspHwJURI66rdMqxim_xYxjMGrKQWngKk/";


    public static void main(String[] args) throws JsonProcessingException, InterruptedException {
        List<Update> listOfUpdates;
        Message responseMessage;
        int lastUpdateId = 55813919;
        RClientConfig config = new RClientConfig(telegramBotUrl, token);
        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
        RClient client = new RClient(asyncHttpClient, config, lastUpdateId);
        //listOfUpdates = client.getUpdates();


        while (true) {
            listOfUpdates = client.getUpdates();
            int newUpdateId = listOfUpdates.get(listOfUpdates.size() - 1).getUpdate_id();
            if (newUpdateId != lastUpdateId) {
                System.out.println(listOfUpdates.get(listOfUpdates.size() - 1).getUpdate_id());
                lastUpdateId = newUpdateId;
                responseMessage = client.respondOnUpdate((listOfUpdates.get(listOfUpdates.size() - 1)), "Roger: " + listOfUpdates.get(listOfUpdates.size() - 1).getMessage().getText());
                for (Update update: listOfUpdates) {
                    System.out.println(update.getMessage().getText());
                }
                System.out.println(responseMessage.getText());
                Thread.sleep(5000);
                client.editTextMessage(responseMessage.getChat().getId(), responseMessage.getMessage_id(), "Roger: qwerty");
            }
            //client.printMessages();
            // client.getUpdates();
            //client.printMessages();


        }
    }
}
