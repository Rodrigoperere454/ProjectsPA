package utils;

import model.Log;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe responsável pela gestão do ficheiro de log.
 * Permite ler os logs e limpar o ficheiro de log.
 */
public class LogFileManager {
    private static final String log_file = "logs.log";

    /**
     * Lê o ficheiro de log e retorna uma lista de objetos Log.
     * Cada objeto Log contém informações sobre o utilizador, a ação realizada e a data/hora da ação.
     *
     * @return Lista de logs lidos do ficheiro.
     */
    public static List<Log> readLogFile() {
        List<Log> logs = new ArrayList<>();
        Path path = Path.of("logs.log");

        if (!Files.exists(path)) {
            System.err.println("Ficheiro de log não encontrado.");
            return logs;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            String dataHora = null;
            String descricao = null;
            String username = null;

            while ((line = reader.readLine()) != null) {
                if (line.matches(".*\\d{4}.*")) {
                    // Linha com data e hora
                    dataHora = line.trim();
                } else if (line.startsWith("INFO:")) {
                    descricao = line.replace("INFO: ", "").trim();

                    // Tenta extrair username da descrição
                    Pattern p = Pattern.compile("Utilizador: (\\w+)");
                    Matcher m = p.matcher(descricao);
                    if (m.find()) {
                        username = m.group(1);
                    } else {
                        username = "Desconhecido";
                    }

                    // Cria o log (nota: aqui usamos string para dataHora, ou adaptas para LocalDateTime depois)
                    logs.add(new Log(username, descricao, dataHora));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o ficheiro de log: " + e.getMessage());
        }

        return logs;
    }

    /**
     * Limpa o ficheiro de log, removendo todo o seu conteúdo.
     * Se o ficheiro não existir, ele será criado vazio.
     */
    public static void clearLogFile() {
        try {
            new PrintWriter(log_file).close();
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao limpar o ficheiro de log: " + e.getMessage());
        }
    }
}
