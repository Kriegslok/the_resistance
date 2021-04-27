package resistance.resistance;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import resistance.SpringConfig;

import java.util.concurrent.Executors;


public class Main {

        public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        TelegramEventLoop telegramEventLoop = context.getBean("telegramEventLoop", TelegramEventLoop.class);
        telegramEventLoop.setExecutorService(Executors.newSingleThreadExecutor());
        Thread ev = new Thread(telegramEventLoop);
        ev.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ev.join();
                context.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));


    }
}
