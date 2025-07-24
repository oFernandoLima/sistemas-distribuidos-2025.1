package com.ufc.correios.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Telegrama {
    private String codigo;
    private String destinatario;
    private String endereco;

    @JsonProperty("numero_palavras")
    private int numeroPalavras;
    private String tipo = "telegrama";

    public Telegrama() {
    }

    public Telegrama(String codigo, String destinatario, String endereco, int numeroPalavras) {
        this.codigo = codigo;
        this.destinatario = destinatario;
        this.endereco = endereco;
        this.numeroPalavras = numeroPalavras;
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

    public int getNumeroPalavras() {
        return numeroPalavras;
    }

    public void setNumeroPalavras(int numeroPalavras) {
        this.numeroPalavras = numeroPalavras;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
