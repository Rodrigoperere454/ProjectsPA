package utils;

import java.io.IOException;
import java.util.logging.*;

/**
 * Classe responsável por carregar e configurar o logger da aplicação.
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
     * Obtém o logger configurado para a aplicação.
     * @return O logger configurado.
     */
    public static Logger getLogger() {
        return logger;
    }
}