package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe que representa um equipamento.
 * Contém informações sobre o equipamento, como marca, modelo, setor comercial, potência, amperagem, código SKU e número do modelo.
 */
public class Equipamento {
    private int id;
    private int id_user;
    private String marca;
    private String modelo;
    private String setor_comercial;
    private int potencia;
    private int amperagem;
    private int codigo_sku;
    private int numero_modelo;
    private String data_submissao;
    private String data_certificacao;

    public Equipamento(int id_user, String marca, String modelo, String setor_comercial, int potencia, int amperagem, int numero_modelo) {
        this.id_user = id_user;
        this.marca = marca;
        this.modelo = modelo;
        this.setor_comercial = setor_comercial;
        this.potencia = potencia;
        this.amperagem = amperagem;
        this.numero_modelo = numero_modelo;
    }

    public Equipamento(int id, int id_user, String marca, String modelo, String setor_comercial, int potencia, int amperagem, int codigo_sku, int numero_modelo, String data_submissao, String data_certificacao) {
        this.id = id;
        this.id_user = id_user;
        this.marca = marca;
        this.modelo = modelo;
        this.setor_comercial = setor_comercial;
        this.potencia = potencia;
        this.amperagem = amperagem;
        this.codigo_sku = codigo_sku;
        this.numero_modelo = numero_modelo;
        this.data_submissao = data_submissao;
        this.data_certificacao = data_certificacao;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSetor_comercial() {
        return setor_comercial;
    }

    public void setSetor_comercial(String setor_comercial) {
        this.setor_comercial = setor_comercial;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public int getAmperagem() {
        return amperagem;
    }

    public void setAmperagem(int amperagem) {
        this.amperagem = amperagem;
    }

    public int getCodigo_sku() {
        return codigo_sku;
    }

    public void setCodigo_sku(int codigo_sku) {
        this.codigo_sku = codigo_sku;
    }

    public int getNumero_modelo() {
        return numero_modelo;
    }

    public void setNumero_modelo(int numero_modelo) {
        this.numero_modelo = numero_modelo;
    }

    public String getData_submissao() {
        return data_submissao;
    }

    public void setData_submissao(String data_submissao) {
        this.data_submissao = data_submissao;
    }

    public String getData_certificacao() {
        return data_certificacao;
    }

    public void setData_certificacao(String data_certificacao) {
        this.data_certificacao = data_certificacao;
    }

    public String toString() {
        return marca + " - " + modelo;
    }
}
