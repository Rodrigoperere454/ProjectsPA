package model;

import java.time.LocalDateTime;

/**
 * Classe que representa um log de ações do utilizador.
 * Contém informações sobre o utilizador, a ação realizada e a data/hora da ação.
 */
public class Log {
    private String username;
    private String acao;
    private String dataHora;

    public Log(String username, String acao) {
        this.username = username;
        this.acao = acao;
    }

    public Log(String username, String acao, String dataHora) {
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

    public String getDataHora() {
        return dataHora;
    }
}
