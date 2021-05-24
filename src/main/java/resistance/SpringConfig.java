package resistance;

//import org.springframework.context.annotation.ComponentScan;
//@org.springframework.context.annotation.Configuration
//

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import resistance.resistance.Main;
import resistance.resistance.RClient;
import resistance.resistance.RClientConfig;
import resistance.resistance.TelegramEventLoop;
import resistance.resistance.logic.EventRouter;
import resistance.resistance.logic.OnlineService;
import resistance.resistance.logic.Room;
import resistance.resistance.logic.RoomManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan("resistance")
@PropertySource("classpath:Resistance.properties")


public class SpringConfig {
    @Bean
    public Main main(){
        return new Main();
    }

    @Bean
    public AsyncHttpClient httpClient(){
        return new DefaultAsyncHttpClient();
    }

    @Bean
    public RClient client (RClientConfig rConfig, AsyncHttpClient httpClient){
        return new RClient(rConfig, httpClient);
    }

    @Bean
    public TelegramEventLoop telegramEventLoop ( RClient client, ExecutorService executorService, EventRouter eventRouter){
        return new TelegramEventLoop(client, executorService, eventRouter);
    }

    @Bean
    public ExecutorService executorService(){
        ExecutorService exec = Executors.newSingleThreadExecutor();
        return exec;
    }

    @Bean
    public EventRouter eventRouter(RoomManager roomManager, OnlineService onlineService, RClient client){
        return new EventRouter(roomManager, onlineService, client);
    }

    @Bean
    public RoomManager roomManager(Room room) {
        return new RoomManager(room);
    }

    @Bean
    public OnlineService onlineService(){
        return new OnlineService();
    }

    @Bean
    public Room room(){
        return new Room();
    }











}

