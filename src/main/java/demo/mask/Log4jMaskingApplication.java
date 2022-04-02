package demo.mask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jMaskingApplication {
    private static final Logger log = LogManager.getLogger(Log4jMaskingApplication.class);

    public static void main(String[] args) {
        log.info("password=Top$cr3t");
        log.error("password=Top$cr3t is fun");
        log.warn("fun is password=Top$cr3t");
        log.info("fun is password=Top$cr3t the fact; password=Top$cr3t is fun");


        log.info("this is my info message");
        log.debug("This is debug message");
        log.debug("Passed to server::0084USER:17603,IP:0:0:0:0:0:0:0:1,3425,Credit Card 1:1000002367844224,3425,Credit Card2:1000002367844224 , CVV:234,SSN:123456789");

    }

}
