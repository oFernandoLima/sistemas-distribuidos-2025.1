package com.example.correios.app;

import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

import com.example.correios.modelo.*;
import com.example.correios.servico.EntregasServiceRemote;

public class ClienteRMI {
    private static EntregasServiceRemote service;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("=================================================");
            System.out.println("CLIENTE INTERATIVO DE CORREIOS - RMI");
            System.out.println("=================================================");

            String serviceURL = "rmi://localhost:1099/EntregasService";
            service = (EntregasServiceRemote) Naming.lookup(serviceURL);
            System.out.println("Conectado ao servidor: " + serviceURL);
            System.out.println("Loja: " + service.getNomeLoja());
            System.out.println();

            while (true) {
                try {
                    exibirMenu();
                    int opcao = lerOpcao();

                    switch (opcao) {
                        case 1:
                            registrarCorrespondencia();
                            break;
                        case 2:
                            listarCorrespondencias();
                            break;
                        case 3:
                            consultarPreco();
                            break;
                        case 4:
                            entregarCorrespondencia();
                            break;
                        case 0:
                            System.out.println("Encerrando o cliente...");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Opcao invalida! Tente novamente.");
                    }
                    System.out.println("\nPressione ENTER para continuar...");
                    scanner.nextLine();

                } catch (Exception e) {
                    System.err.println("Erro: " + e.getMessage());
                    System.out.println("\nPressione ENTER para continuar...");
                    scanner.nextLine();
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao conectar com o servidor RMI: " + e.getMessage());
            System.err.println("Certifique-se que o ServidorRMI esta rodando!");
        }
    }

    private static void exibirMenu() {
        System.out.println("\nMENU PRINCIPAL:");
        System.out.println("1  Registrar Correspondencia");
        System.out.println("2  Listar Correspondencias");
        System.out.println("3  Consultar Preco");
        System.out.println("4  Entregar Correspondencia");
        System.out.println("0  Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private static int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void registrarCorrespondencia() throws Exception {
        System.out.println("\nREGISTRAR NOVA CORRESPONDENCIA");
        System.out.println("Tipos disponiveis:");
        System.out.println("1 - Carta");
        System.out.println("2 - Encomenda");
        System.out.println("3 - Telegrama");
        System.out.print("Tipo: ");

        int tipo = Integer.parseInt(scanner.nextLine());
        System.out.print("Codigo: ");
        String codigo = scanner.nextLine();

        System.out.print("Destinatario: ");
        String destinatario = scanner.nextLine();

        System.out.print("Endereco: ");
        String endereco = scanner.nextLine();

        Correspondencia correspondencia = null;
        switch (tipo) {
            case 1:
                System.out.print("Carta selada? (s/n): ");
                String resposta = scanner.nextLine();
                boolean selada = resposta.toLowerCase().equals("s");
                correspondencia = new Carta(codigo, destinatario, endereco, selada);
                break;

            case 2:
                System.out.print("Peso (kg): ");
                double pesoKg = Double.parseDouble(scanner.nextLine());
                correspondencia = new Encomenda(codigo, destinatario, endereco, pesoKg);
                break;

            case 3:
                System.out.print("Mensagem: ");
                String mensagem = scanner.nextLine();
                int numeroPalavras = mensagem.split("\\s+").length;
                correspondencia = new Telegrama(codigo, destinatario, endereco, numeroPalavras);
                break;

            default:
                System.out.println("Tipo invalido!");
                return;
        }
        service.registrarCorrespondencia(correspondencia);
        System.out.println("Correspondencia registrada com sucesso!");
        System.out.println("Preco calculado: R$ " + String.format("%.2f", correspondencia.calcularPreco()));
    }

    private static void listarCorrespondencias() throws Exception {
        System.out.println("\nCORRESPONDENCIAS CADASTRADAS:");

        List<Correspondencia> correspondencias = service.listarCorrespondencias();

        if (correspondencias.isEmpty()) {
            System.out.println("Nenhuma correspondencia encontrada.");
            return;
        }

        System.out.println("─".repeat(80));
        for (Correspondencia c : correspondencias) {
            System.out.printf("Codigo: %s | Para: %s | Endereco: %s%n",
                    c.getCodigo(), c.getDestinatario(), c.getEndereco());
            System.out.printf("Preco: R$ %.2f | Tipo: %s%n",
                    c.calcularPreco(), c.getClass().getSimpleName());
            System.out.println("─".repeat(80));
        }

        System.out.printf("Total de correspondencias: %d%n", correspondencias.size());
    }

    private static void consultarPreco() throws Exception {
        System.out.println("\nCONSULTAR PRECO");
        System.out.print("Codigo da correspondencia: ");
        String codigo = scanner.nextLine();

        double preco = service.consultarPreco(codigo);

        if (preco > 0) {
            System.out.printf("Preco da correspondencia '%s': R$ %.2f%n", codigo, preco);
        } else {
            System.out.printf("Correspondencia '%s' nao encontrada.%n", codigo);
        }
    }

    private static void entregarCorrespondencia() throws Exception {
        System.out.println("\nENTREGAR CORRESPONDENCIA");
        System.out.print("Codigo da correspondencia: ");
        String codigo = scanner.nextLine();

        boolean entregue = service.entregar(codigo);

        if (entregue) {
            System.out.printf("Correspondencia '%s' entregue com sucesso!%n", codigo);
        } else {
            System.out.printf("Correspondencia '%s' nao encontrada.%n", codigo);
        }
    }
}
