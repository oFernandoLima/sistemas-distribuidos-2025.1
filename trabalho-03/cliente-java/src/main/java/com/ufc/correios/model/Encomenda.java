package com.ufc.correios.model;

public class Encomenda {
    private String codigo;
    private String destinatario;
    private String endereco;
    private double peso;
    private String tipo = "encomenda";

    public Encomenda() {
    }

    public Encomenda(String codigo, String destinatario, String endereco, double peso) {
        this.codigo = codigo;
        this.destinatario = destinatario;
        this.endereco = endereco;
        this.peso = peso;
    }

    // Getters and Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
