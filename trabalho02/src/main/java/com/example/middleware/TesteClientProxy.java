package com.example.middleware;

import java.util.List;

import com.example.app.Carta;
import com.example.app.Correspondencia;

public class TesteClientProxy {
    public static void main(String[] args) throws Exception {
        RemoteObjectRef ref = new RemoteObjectRef("Entregas");

        // Exemplo: registrar uma carta
        Correspondencia carta = new Carta("123", "João", "Rua A", true);
        byte[] dados = Marshaller.marshall(carta);
        ClientProxy.doOperation(ref, 0, dados);        // Exemplo: listar
        byte[] resposta = ClientProxy.doOperation(ref, 1, new byte[0]);
        List<Correspondencia> lista = Marshaller.unmarshallList(resposta, Correspondencia.class);
        for (Correspondencia c : lista) {
            System.out.println("Recebido: " + c.getCodigo() + " - " + c.getDestinatario() + " - " + c.getEndereco());
        }
    }
}
