package model;

public class Observacao {
    private String observacao;
    private int id_utilizador;
    private String assunto;
    private String informacao;


    public Observacao(String observacao, int id_utilizador, String assunto, String informacao) {
        this.observacao = observacao;
        this.id_utilizador = id_utilizador;
        this.assunto = assunto;
        this.informacao = informacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getId_utilizador() {
        return id_utilizador;
    }

    public void setId_utilizador(int id_utilizador) {
        this.id_utilizador = id_utilizador;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getInformacao() {
        return informacao;
    }

    public void setInformacao(String informacao) {
        this.informacao = informacao;
    }
}
