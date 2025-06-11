package model;

public class Certificacao {
    private int id_equipamento;
    private int id_tecnico;
    private int id_fabricante;
    private int custo;
    private String estado;
    private String data_realizacao;
    private String numero_certificacao;
    private String numero_licenca;
    private int tempo_decorrido;
    private int id;


    public Certificacao(int id_fabricante, int id_equipamento, String estado) {
        this.id_fabricante = id_fabricante;
        this.id_equipamento = id_equipamento;
        this.estado = estado;
    }

    public Certificacao(int id, int id_fabricante, int id_equipamento, int id_tecnico, String estado, String data_realizacao, String numero_certificacao, String numero_licenca, int custo, int tempo_decorrido) {
        this.id_fabricante = id_fabricante;
        this.id_equipamento = id_equipamento;
        this.id_tecnico = id_tecnico;
        this.custo = custo;
        this.estado = estado;
        this.data_realizacao = data_realizacao;
        this.numero_certificacao = numero_certificacao;
        this.numero_licenca = numero_licenca;
        this.tempo_decorrido = tempo_decorrido;
    }



    public int getId_fabricante() {
        return id_fabricante;
    }

    public void setId_fabricante(int id_fabricante) {
        this.id_fabricante = id_fabricante;
    }

    public int getId_equipamento() {
        return id_equipamento;
    }

    public void setId_equipamento(int id_equipamento) {
        this.id_equipamento = id_equipamento;
    }

    public int getId_tecnico() {
        return id_tecnico;
    }

    public void setId_tecnico(int id_tecnico) {
        this.id_tecnico = id_tecnico;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getData_realizacao() {
        return data_realizacao;
    }

    public void setData_realizacao(String data_realizacao) {
        this.data_realizacao = data_realizacao;
    }

    public String getNumero_certificacao() {
        return numero_certificacao;
    }

    public void setNumero_certificacao(String numero_certificacao) {
        this.numero_certificacao = numero_certificacao;
    }

    public String getNumero_licenca() {
        return numero_licenca;
    }

    public void setNumero_licenca(String numero_licenca) {
        this.numero_licenca = numero_licenca;
    }

    public int getTempo_decorrido() {
        return tempo_decorrido;
    }

    public void setTempo_decorrido(int tempo_decorrido) {
        this.tempo_decorrido = tempo_decorrido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
