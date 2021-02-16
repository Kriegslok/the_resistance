//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        SimpleRequest sr = new SimpleRequest();
        sr.init();


        System.out.println("111");
        logger.info("1111");

    }
}
