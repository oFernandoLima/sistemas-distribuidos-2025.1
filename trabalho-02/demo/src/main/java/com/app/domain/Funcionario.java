package com.app.domain;

import java.io.Serializable;

public class Funcionario implements Serializable {
    private String nome;
    private String codigo;

    public Funcionario(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public String getNome() { return nome; }
    public String getCodigo() { return codigo; }
}
