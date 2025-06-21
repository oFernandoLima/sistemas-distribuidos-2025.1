package com.example.correios.modelo;

import java.io.Serializable;

public abstract class Correspondencia implements Serializable {
    protected String codigo;
    protected String destinatario;
    protected String endereco;

    public Correspondencia(String codigo, String destinatario, String endereco) {
        this.codigo = codigo;
        this.destinatario = destinatario;
        this.endereco = endereco;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public abstract double calcularPreco();
}