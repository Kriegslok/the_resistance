

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class.getName());


    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        SimpleRequest sr = new SimpleRequest();
//        sr.init();
        RClient client = new RClient();
        client.execute(client.getRequest());
//        RClient_tdlib rClient = new RClient_tdlib();
//        rClient.init();


//        System.out.println("111");
//        logger.info("1111");

    }
}
