package com.example.correios.servico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.correios.modelo.Correspondencia;

public class LojaCorreios implements Serializable {
    private String nome;
    private List<Correspondencia> correspondencias;

    public LojaCorreios(String nome) {
        this.nome = nome;
        this.correspondencias = new ArrayList<>();
    }

    public void adicionarCorrespondencia(Correspondencia c) {
        correspondencias.add(c);
    }

    public List<Correspondencia> getCorrespondencias() {
        return correspondencias;
    }

    public String getNome() {
        return nome;
    }
}
