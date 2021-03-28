


import java.util.concurrent.Executors;


public class Main {

    //static Logger logger = LoggerFactory.getLogger(Main.class.getName());
    static final String telegramBotUrl = "https://api.telegram.org/";
    static final String token = "bot1535167887:AAHspHwJURI66rdMqxim_xYxjMGrKQWngKk/";


    public static void main(String[] args) {
        TelegramEventLoop eventLoop = new TelegramEventLoop(telegramBotUrl, token, Executors.newSingleThreadExecutor());
        Thread ev = new Thread(eventLoop);
        ev.start();
        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            eventLoop.stop();
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));


    }
}
