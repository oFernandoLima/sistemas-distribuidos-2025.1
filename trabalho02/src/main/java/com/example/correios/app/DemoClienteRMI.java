package com.example.correios.app;

import java.rmi.Naming;
import com.example.correios.modelo.*;
import com.example.correios.servico.EntregasServiceRemote;

public class DemoClienteRMI {

    public static void main(String[] args) {
        try {
            System.out.println("=================================================");
            System.out.println("DEMO CLIENTE RMI - SISTEMA DE CORREIOS");
            System.out.println("=================================================");
            String serviceURL = "rmi://localhost:1099/EntregasService";
            EntregasServiceRemote service = (EntregasServiceRemote) Naming.lookup(serviceURL);

            System.out.println("Conectado ao servidor: " + serviceURL);
            System.out.println("Loja: " + service.getNomeLoja());
            System.out.println();

            System.out.println("Registrando correspondencias de teste...");

            Carta carta = new Carta("C001", "Joao Silva", "Rua A, 123", true);
            service.registrarCorrespondencia(carta);
            System.out.println("Carta registrada: " + carta.getCodigo());

            Encomenda encomenda = new Encomenda("E001", "Maria Santos", "Rua B, 456", 2.5);
            service.registrarCorrespondencia(encomenda);
            System.out.println("Encomenda registrada: " + encomenda.getCodigo());
            Telegrama telegrama = new Telegrama("T001", "Pedro Costa", "Rua C, 789", 10);
            service.registrarCorrespondencia(telegrama);
            System.out.println("Telegrama registrado: " + telegrama.getCodigo());

            System.out.println();

            System.out.println("Listando todas as correspondencias:");
            var correspondencias = service.listarCorrespondencias();
            for (var c : correspondencias) {
                System.out.printf("%s - %s - R$ %.2f%n",
                        c.getCodigo(), c.getDestinatario(), c.calcularPreco());
            }

            System.out.println();

            System.out.println("Consultando precos:");
            System.out.printf("Carta C001: R$ %.2f%n", service.consultarPreco("C001"));
            System.out.printf("Encomenda E001: R$ %.2f%n", service.consultarPreco("E001"));
            System.out.printf("Telegrama T001: R$ %.2f%n", service.consultarPreco("T001"));

            System.out.println();

            System.out.println("Entregando carta C001...");
            boolean entregue = service.entregar("C001");
            System.out.println(entregue ? "Entregue com sucesso!" : "Falha na entrega");

            System.out.println();
            System.out.println("Correspondencias restantes:");
            correspondencias = service.listarCorrespondencias();
            for (var c : correspondencias) {
                System.out.printf("%s - %s - R$ %.2f%n",
                        c.getCodigo(), c.getDestinatario(), c.calcularPreco());
            }

            System.out.println();
            System.out.println("Demo concluida com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            System.err.println("Certifique-se que o ServidorRMI esta rodando!");
            e.printStackTrace();
        }
    }
}
