package model;
import java.time.LocalDate;
import java.util.Date;

/**
 * Classe que representa um Fabricante, que é um tipo de Utilizador.
 * Contém informações específicas do fabricante, como NIF, telefone, morada, setor comercial e data de início.
 */
public class Fabricante extends Utilizador {

    private String nif;
    private String telefone;
    private String morada;
    private String sector_comercial;
    private LocalDate data_inicio;

    public Fabricante(String name, String username, String password, String email, String type,
                      String nif, String telefone, String morada, String sector_comercial, LocalDate data) {
        super(name, username, password, email, type);
        this.nif = nif;
        this.telefone = telefone;
        this.morada = morada;
        this.sector_comercial = sector_comercial;
        this.data_inicio = data;
    }

    public String getNif() {
        return nif;
    }


    public String getTelefone() {
        return telefone;
    }


    public String getMorada() {
        return morada;
    }


    public String getSector_comercial() {
        return sector_comercial;
    }


    public LocalDate getData() {
        return data_inicio;
    }

}