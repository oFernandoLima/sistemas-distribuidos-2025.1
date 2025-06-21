package com.example.correios.cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;

import com.example.correios.servico.EntregasImpl;

public class ServidorCorreios {

    public static void main(String[] args) {
        try {
            // Inicia o RMI Registry na porta padrão 1099
            try {
                LocateRegistry.createRegistry(1099);
                System.out.println("RMI Registry iniciado na porta 1099.");
            } catch (Exception e) {
                System.out.println("RMI Registry já está em execução.");
            }

            // Cria uma instância do serviço remoto
            EntregasImpl entregas = new EntregasImpl("Loja Central dos Correios");

            // Registra o serviço com o nome "EntregasService"
            Naming.rebind("EntregasService", entregas);

            System.out.println("Servidor pronto. Serviço registrado como 'EntregasService'.");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
