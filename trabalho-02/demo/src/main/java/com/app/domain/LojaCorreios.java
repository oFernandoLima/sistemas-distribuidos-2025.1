package com.app.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LojaCorreios implements Serializable {
    private String nome;
    private List<Correspondencia> correspondencias;
    private Funcionario funcionario; // Segunda agregação

    public LojaCorreios(String nome) {
        this.nome = nome;
        this.correspondencias = new ArrayList<>();
        this.funcionario = new Funcionario("João Silva", "12345");
    }

    public void adicionarCorrespondencia(Correspondencia c) {
        correspondencias.add(c);
    }

    public List<Correspondencia> getCorrespondencias() {
        return correspondencias;
    }

    public String getNome() { return nome; }
    public Funcionario getFuncionario() { return funcionario; }
}