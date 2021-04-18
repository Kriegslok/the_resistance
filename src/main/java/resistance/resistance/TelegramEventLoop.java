package resistance.resistance;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import resistance.resistance.telegramResponse.Message;
import resistance.resistance.telegramResponse.Update;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TelegramEventLoop implements Runnable{
    @Value("${telegramEventLoop.telegramBotUrl}")
    private String telegramBotUrl;

    @Value("${telegramEventLoop.token}")
    private String token;
    private ExecutorService executorService;
    private RClientConfig config;
    private RClient client;
//    private AsyncHttpClient asyncHttpClient;

    @Autowired
    public void setClient(RClient client) {
        this.client = client;
    }

//    @Autowired
//    public AsyncHttpClient getAsyncHttpClient() {
//        return asyncHttpClient;
//    }
    //    public TelegramEventLoop(String telegramBotUrl, String token, ExecutorService executorService) {
//        this.telegramBotUrl = telegramBotUrl;
//        this.token = token;
//        this.executorService = executorService;
//    }

    @Autowired
    public TelegramEventLoop(RClientConfig config) {
        this.config = config;

    }


    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    //private void task = null;
    private volatile boolean running = false;

    public void run(){
        this.running = true;
        executorService.execute(this::job);
    }

    public void stop() throws InterruptedException {
        this.running = false;
        this.executorService.shutdown();
        executorService.awaitTermination(2000, TimeUnit.MILLISECONDS);
        System.out.println("shutdown");
    }



    private void job() {
        try {

            List<Update> listOfUpdates;
            Message responseMessage;
            int lastUpdateId = 3;
            //RClientConfig config = new RClientConfig(telegramBotUrl, token);

//            RClient client = new RClient(asyncHttpClient, config);//, lastUpdateId);
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
