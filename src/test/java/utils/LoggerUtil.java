package utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerUtil {

    public static Logger logger = Logger.getLogger("EaseMyTripLogger");

    static {
        PropertyConfigurator.configure("src/test/resources/log4j2.xml");
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }
}