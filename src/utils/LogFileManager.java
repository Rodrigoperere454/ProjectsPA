package utils;

import model.Log;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogFileManager {
    private static final String log_file = "logs.log";

    public static List<Log> readLogFile() {
        try {
            return Files.readString(Path.of(log_file))
                    .lines()
                    .map(line -> {
                        String[] parts = line.split(";");
                        if (parts.length == 3) {
                            String username = parts[0];
                            String acao = parts[1];
                            LocalDateTime dataHora = LocalDateTime.parse(parts[2]);
                            return new Log(username, acao, dataHora);
                        }
                        return null;
                    })
                    .filter(log -> log != null)
                    .toList();
        } catch (IOException e) {
            System.err.println("Erro ao ler o ficheiro de log: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void clearLogFile() {
        try {
            new PrintWriter(log_file).close();
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao limpar o ficheiro de log: " + e.getMessage());
        }
    }
}
