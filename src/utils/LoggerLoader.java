package utils;

import java.io.IOException;
import java.util.logging.*;

/**
 * Class to load the logger and create a log file
 */
public class LoggerLoader {
    private static Logger logger;

    static {
        try {
            FileHandler fileHandler = new FileHandler("logs.log", true);

            fileHandler.setFormatter(new SimpleFormatter());

            logger = Logger.getLogger(LoggerLoader.class.getName());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Error creating log file: " + e.getMessage());
        }
    }

    /**
     * Get the logger instance.
     * @return the logger instance
     */
    public static Logger getLogger() {
        return logger;
    }
}