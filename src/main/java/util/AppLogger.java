package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase utilitaria para logging centralizado en la aplicación ElorES.
 */
public class AppLogger {
    
    private static final Logger logger = LoggerFactory.getLogger("ElorES");
    
    public static void info(String message) {
        logger.info(message);
        System.out.println("[INFO] " + message);
    }
    
    public static void error(String message) {
        logger.error(message);
        System.err.println("[ERROR] " + message);
    }
    
    public static void error(String message, Exception e) {
        logger.error(message, e);
        System.err.println("[ERROR] " + message + " - " + e.getMessage());
    }
    
    public static void debug(String message) {
        logger.debug(message);
        System.out.println("[DEBUG] " + message);
    }
    
    public static void warn(String message) {
        logger.warn(message);
        System.out.println("[WARN] " + message);
    }
}
