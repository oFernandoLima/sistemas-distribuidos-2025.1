package com.example.correios.modelo;

public class Carta extends Correspondencia {
    private boolean selada;

    public Carta(String codigo, String destinatario, String endereco, boolean selada) {
        super(codigo, destinatario, endereco);
        this.selada = selada;
    }

    public boolean isSelada() {
        return selada;
    }

    public void setSelada(boolean selada) {
        this.selada = selada;
    }

    @Override
    public double calcularPreco() {
        return selada ? 2.00 : 1.50;
    }
}