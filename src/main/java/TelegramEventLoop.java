

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import telegramResponse.Message;
import telegramResponse.Update;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class TelegramEventLoop implements Runnable{
    final String telegramBotUrl;
    final String token;
    private final ExecutorService executorService;

    public TelegramEventLoop(String telegramBotUrl, String token, ExecutorService executorService) {
        this.telegramBotUrl = telegramBotUrl;
        this.token = token;
        this.executorService = executorService;
    }

    //private void task = null;
    private volatile boolean running = false;

    public void run(){
        this.running = true;
        executorService.execute(this::job);
    }

    public void stop(){
        this.running = false;
        this.executorService.shutdown();
        System.out.println("shutdown");
    }

    private void job() {
        try {

            List<Update> listOfUpdates;
            Message responseMessage;
            int lastUpdateId = 3;
            RClientConfig config = new RClientConfig(telegramBotUrl, token);
            AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
            RClient client = new RClient(asyncHttpClient, config);//, lastUpdateId);
            listOfUpdates = client.getUpdates(lastUpdateId);
            lastUpdateId = listOfUpdates.get(listOfUpdates.size() - 1).getUpdate_id();


            while (running) {
                listOfUpdates = client.getUpdates(lastUpdateId);
                Update lastUpdate = listOfUpdates.get(listOfUpdates.size() - 1);
                int newUpdateId = lastUpdate.getUpdate_id();
                if (newUpdateId != lastUpdateId) {
                    System.out.println(lastUpdate.getUpdate_id());
                    //client.updateToJson(lastUpdate);
                    lastUpdateId = newUpdateId;
                    responseMessage = client.respondOnUpdate((listOfUpdates.get(listOfUpdates.size() - 1)), "Roger: " + listOfUpdates.get(listOfUpdates.size() - 1).getMessage().getText());
                    for (Update update : listOfUpdates) {
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
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
