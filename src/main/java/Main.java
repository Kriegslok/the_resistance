

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class.getName());


    public static void main(String[] args) throws InterruptedException {
//        SimpleRequest sr = new SimpleRequest();
//        sr.init();
        RClient rClient = new RClient();
        rClient.init();


//        System.out.println("111");
//        logger.info("1111");

    }
}
