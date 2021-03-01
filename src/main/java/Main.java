


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class.getName());


    public static void main(String[] args) {
        RClient client = new RClient();
        client.getUpdates();
        client.printMessages();
        client.getUpdates();
        client.printMessages();


    }
}
