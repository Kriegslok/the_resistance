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
    private ExecutorService executorService;
    private RClient client;


    @Autowired
    public TelegramEventLoop(RClient client, ExecutorService executorService) {
        this.client = client;
        this.executorService = executorService;
    }

    @Autowired
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    private volatile boolean running = false;

    public void run(){
        this.running = true;
        job();
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
            listOfUpdates = client.getUpdates(lastUpdateId);
            lastUpdateId = listOfUpdates.get(listOfUpdates.size() - 1).getUpdate_id();


            while (running) {
                listOfUpdates = client.getUpdates(lastUpdateId);
                Update lastUpdate = listOfUpdates.get(listOfUpdates.size() - 1);
                int newUpdateId = lastUpdate.getUpdate_id();
                if (newUpdateId != lastUpdateId) {
                    System.out.println(lastUpdate.getUpdate_id());
                    lastUpdateId = newUpdateId;
                    if(listOfUpdates.size() > 1){
                        listOfUpdates.remove(0);
                    }
                    for (Update update : listOfUpdates) {
                        System.out.println(update.getMessage().getText());
                        responseMessage = client.respondOnUpdate(update, "Roger: " + update.getMessage().getText());
                        System.out.println(responseMessage.getText());
                        Thread.sleep(5000);
                        client.editTextMessage(responseMessage.getChat().getId(), responseMessage.getMessage_id(), "Roger: qwerty");
                    }
                    listOfUpdates.clear();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
