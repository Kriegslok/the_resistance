package resistance.resistance;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import resistance.SpringConfig;

import java.util.concurrent.Executors;


public class Main {

    //static Logger logger = LoggerFactory.getLogger(resistance.resistance.Main.class.getName());
//    static final String telegramBotUrl = "https://api.telegram.org/";
//    static final String token = "bot1535167887:AAHspHwJURI66rdMqxim_xYxjMGrKQWngKk/";


    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        TelegramEventLoop eventLoop = context.getBean("eventLoop", TelegramEventLoop.class);
        eventLoop.setExecutorService(Executors.newSingleThreadExecutor());

        Thread ev = new Thread(eventLoop);
        ev.start();
        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                eventLoop.stop();
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        context.close();
    }
}
