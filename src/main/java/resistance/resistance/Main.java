package resistance.resistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import resistance.SpringConfig;

@Component

public class Main {

    @Autowired

    private static TelegramEventLoop telegramEventLoop;

    public static void main(String[] args) {
       AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
       TelegramEventLoop telegramEventLoop = context.getBean("telegramEventLoop", TelegramEventLoop.class);

        telegramEventLoop.getExecutorService().submit(telegramEventLoop);


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                telegramEventLoop.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}
