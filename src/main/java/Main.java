

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class.getName());


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, ParseException {
        RClient client = new RClient();
        client.getUpdates();



//        System.out.println("111");
//        logger.info("1111");

    }
}
