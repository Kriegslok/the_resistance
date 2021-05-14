package resistance.resistance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import resistance.resistance.logic.EventRouter;
import resistance.resistance.entities.telegramResponse.Message;
import resistance.resistance.entities.telegramResponse.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TelegramEventLoop implements Runnable{
    private ExecutorService executorService;
    private RClient client;
    private EventRouter eventRouter;


    @Autowired
    public TelegramEventLoop(RClient client, ExecutorService executorService, EventRouter eventRouter) {
        this.client = client;
        this.executorService = executorService;
        this.eventRouter = eventRouter;
    }

//    @Autowired
//    public void setExecutorService(ExecutorService executorService) {
//        this.executorService = executorService;
//    }

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

    public ExecutorService getExecutorService() {
        return executorService;
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
                        List<String> replyList = new ArrayList<>();
                        if(update.getMessage() != null){
                            System.out.println(update.getMessage().getText());
                        }
                        else if(update.getCallback_query() != null && update.getCallback_query().getData() != null){
                            System.out.println(update.getCallback_query().getData());
                        }

                        replyList = eventRouter.scenerySelector(update);
                        if(replyList.size() > 1){
                            responseMessage = client.respondOnUpdate(update, replyList.get(0), replyList.get(1));
                        }
                        else if(replyList.size() == 1){
                                responseMessage = client.respondOnUpdate(update, replyList.get(0));
                        }
//                        else {
//                            responseMessage = client.respondOnUpdate(update, "Roger: " + update.getMessage().getText());
//                            System.out.println(responseMessage.getText());
//
//                            Thread.sleep(5000);
//                            client.editTextMessage(responseMessage.getChat().getId(), responseMessage.getMessage_id(), "Roger: qwerty");
//                        }
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
