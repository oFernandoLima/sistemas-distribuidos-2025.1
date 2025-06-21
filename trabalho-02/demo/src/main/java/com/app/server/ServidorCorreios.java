package com.app.server;
public class ServidorCorreios {
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando servidor de correios na porta 9999...");
            
            ServerDispatcher dispatcher = new ServerDispatcher(9999, "Loja Central dos Correios");
            
            System.out.println("Servidor pronto para receber requisições.");
            
            // Adicionar hook para parada graciosa
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Parando servidor...");
                dispatcher.stop();
            }));
            
            dispatcher.processRequests();
            
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}