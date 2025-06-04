package model;

import java.sql.Timestamp;

public class Notificacao {
    private Timestamp dataHora;
    private int idUtilizador;
    private String descricao;
    private String tipo;
    private String encarregado;
    private boolean lida;

    public Notificacao(Timestamp dataHora, int idUtilizador, String descricao, String tipo, String encarregado, boolean lida) {
        this.dataHora = dataHora;
        this.idUtilizador = idUtilizador;
        this.descricao = descricao;
        this.tipo = tipo;
        this.encarregado = encarregado;
        this.lida = lida;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public int getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEncarregado() {
        return encarregado;
    }

    public void setEncarregado(String encarregado) {
        this.encarregado = encarregado;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    @Override
    public String toString() {
        return "Notificação: " + descricao +
               ", Tipo: " + tipo +
               ", Encarregado: " + encarregado +
               ", Data: " + dataHora +
               ", Lida: " + (lida ? "Sim" : "Não");
    }
}
