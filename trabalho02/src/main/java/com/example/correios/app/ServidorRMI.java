package com.example.correios.app;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import com.example.correios.servico.EntregasService;

public class ServidorRMI {

    public static void main(String[] args) {
        try {
            System.out.println("=================================================");
            System.out.println("INICIANDO SERVIDOR RMI DE CORREIOS");
            System.out.println("=================================================");

            LocateRegistry.createRegistry(1099);
            System.out.println("Registro RMI criado na porta 1099");

            EntregasService service = new EntregasService("Loja Central de Correios");

            String serviceName = "EntregasService";
            Naming.rebind("rmi://localhost:1099/" + serviceName, service);

            System.out.println("Servico '" + serviceName + "' registrado com sucesso!");
            System.out.println("Servidor RMI rodando em: rmi://localhost:1099/" + serviceName);
            System.out.println("Aguardando clientes...");
            System.out.println("\nPara parar o servidor, pressione Ctrl+C");

            Thread.currentThread().join();
        } catch (Exception e) {
            System.err.println("Erro ao iniciar servidor RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
