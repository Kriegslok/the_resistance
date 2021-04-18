package resistance;

//import org.springframework.context.annotation.ComponentScan;
//@org.springframework.context.annotation.Configuration
//

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import resistance.resistance.RClient;
import resistance.resistance.RClientConfig;
import resistance.resistance.TelegramEventLoop;

@Configuration
@ComponentScan("resistance")
@PropertySource("classpath:TelegramEventLoop.properties")


public class SpringConfig {
    @Bean
    public TelegramEventLoop eventLoop (RClientConfig config){
        return new TelegramEventLoop(config);
    }

}

