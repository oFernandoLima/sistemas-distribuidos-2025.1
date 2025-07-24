package com.ufc.correios.model;

public class Carta {
    private String codigo;
    private String destinatario;
    private String endereco;
    private boolean selada;
    private String tipo = "carta";

    public Carta() {
    }

    public Carta(String codigo, String destinatario, String endereco, boolean selada) {
        this.codigo = codigo;
        this.destinatario = destinatario;
        this.endereco = endereco;
        this.selada = selada;
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

    public boolean isSelada() {
        return selada;
    }

    public void setSelada(boolean selada) {
        this.selada = selada;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
