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
import resistance.resistance.RClient;
import resistance.resistance.RClientConfig;
import resistance.resistance.TelegramEventLoop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan("resistance")
@PropertySource("classpath:TelegramEventLoop.properties")


public class SpringConfig {
    @Bean
    public AsyncHttpClient httpClient(){
        return new DefaultAsyncHttpClient();
    }

    @Bean
    public RClient client (RClientConfig rConfig, AsyncHttpClient httpClient){
        return new RClient(rConfig, httpClient);
    }

    @Bean
    public TelegramEventLoop telegramEventLoop ( RClient client, ExecutorService executorService){
        return new TelegramEventLoop(client, executorService);
    }

    @Bean
    public ExecutorService executorService(){
        ExecutorService exec = Executors.newSingleThreadExecutor();
        return exec;
    }







}

