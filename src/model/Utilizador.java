package model;

/**
 * Classe que representa um utilizador do sistema.
 * Contém informações sobre o utilizador, como nome, username, password, email, tipo, estado, NIF, telefone, morada,
 * setor comercial, área de especialização e nível de certificação.
 */
public class Utilizador {
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String type;
    private String estado;
    private String nif;
    private String telefone;
    private String morada;
    private String sector_comercial;
    private String area_especializacao;
    private int nivel_certificacao;

    public Utilizador(String name, String username, String password, String email, String type) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    public Utilizador(int id, String name, String username, String password, String email, String type, String estado, String nif, String telefone, String morada, String sector_comercial, String area_especializacao, int nivel_certificacao) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
        this.estado = estado;
        this.nif = nif;
        this.telefone = telefone;
        this.morada = morada;
        this.sector_comercial = sector_comercial;
        this.area_especializacao = area_especializacao;
        this.nivel_certificacao = nivel_certificacao;
    }

    public Utilizador(int id, String name, String username, String email, String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getPassword() { return password; }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getSector_comercial() {
        return sector_comercial;
    }

    public void setSector_comercial(String sector_comercial) {
        this.sector_comercial = sector_comercial;
    }

    public String getArea_especializacao() {
        return area_especializacao;
    }

    public void setArea_especializacao(String area_especializacao) {
        this.area_especializacao = area_especializacao;
    }

    public int getNivel_certificacao() {
        return nivel_certificacao;
    }

    public void setNivel_certificacao(int nivel_certificacao) {
        this.nivel_certificacao = nivel_certificacao;
    }
}
