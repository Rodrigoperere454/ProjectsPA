package model;

import java.time.LocalDateTime;

public class Log {
    private String username;
    private String acao;
    private LocalDateTime dataHora;

    public Log(String username, String acao) {
        this.username = username;
        this.acao = acao;
    }

    public Log(String username, String acao, LocalDateTime dataHora) {
        this.username = username;
        this.acao = acao;
        this.dataHora = dataHora;
    }

    public String getUsername() {
        return username;
    }

    public String getAcao() {
        return acao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
