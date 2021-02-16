import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimpleRequest {
    static Logger logger = LoggerFactory.getLogger(Main.class.getName());
    public void init() {
        System.out.println("Введите строку");
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            String s = reader.readLine();
            System.out.println(s);
            logger.info(s);
        }
        catch (IOException e){
            logger.info("IOException: ", e);
        }

    }

}
