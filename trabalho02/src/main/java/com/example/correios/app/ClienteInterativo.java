package com.example.correios.app;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.example.correios.modelo.*;
import com.example.middleware.core.Marshaller;
import com.example.middleware.proxy.ClientProxy;
import com.example.middleware.transport.RemoteObjectRef;

/**
 * Cliente interativo que permite interagir com o sistema de correios via
 * terminal
 */
public class ClienteInterativo {
    private static final RemoteObjectRef remoteRef = new RemoteObjectRef("EntregasService");
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("📮 CLIENTE INTERATIVO DE CORREIOS - UDP");
        System.out.println("=================================================");
        System.out.println("⚠️  Certifique-se que o ServidorMiddleware está rodando na porta 5052");
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
                        System.out.println("👋 Encerrando o cliente...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("❌ Opção inválida! Tente novamente.");
                }

                System.out.println("\n⏸️  Pressione ENTER para continuar...");
                scanner.nextLine();

            } catch (Exception e) {
                System.err.println("❌ Erro: " + e.getMessage());
                System.out.println("\n⏸️  Pressione ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n📋 MENU PRINCIPAL:");
        System.out.println("1️⃣  Registrar Correspondência");
        System.out.println("2️⃣  Listar Correspondências");
        System.out.println("3️⃣  Consultar Preço");
        System.out.println("4️⃣  Entregar Correspondência");
        System.out.println("0️⃣  Sair");
        System.out.print("➡️  Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void registrarCorrespondencia() throws IOException, ClassNotFoundException {
        System.out.println("\n📝 REGISTRAR NOVA CORRESPONDÊNCIA");
        System.out.println("Tipos disponíveis:");
        System.out.println("1 - Carta");
        System.out.println("2 - Encomenda");
        System.out.println("3 - Telegrama");
        System.out.print("➡️  Tipo: ");

        int tipo = Integer.parseInt(scanner.nextLine());

        System.out.print("📮 Código: ");
        String codigo = scanner.nextLine();

        System.out.print("📫 Destinatário: ");
        String destinatario = scanner.nextLine();

        System.out.print("🏠 Endereço: ");
        String endereco = scanner.nextLine();

        Correspondencia correspondencia = null;

        switch (tipo) {
            case 1: // Carta
                System.out.print("📧 É selada? (s/n): ");
                boolean selada = scanner.nextLine().toLowerCase().startsWith("s");
                correspondencia = new Carta(codigo, destinatario, endereco, selada);
                break;

            case 2: // Encomenda
                System.out.print("⚖️  Peso (kg): ");
                double peso = Double.parseDouble(scanner.nextLine());
                correspondencia = new Encomenda(codigo, destinatario, endereco, peso);
                break;

            case 3: // Telegrama
                System.out.print("📝 Número de palavras: ");
                int palavras = Integer.parseInt(scanner.nextLine());
                correspondencia = new Telegrama(codigo, destinatario, endereco, palavras);
                break;

            default:
                System.out.println("❌ Tipo inválido!");
                return;
        }
        // Chamada remota para registrar
        byte[] args = Marshaller.marshall(correspondencia);
        ClientProxy.doOperation(remoteRef, 0, args);

        System.out.println("✅ Correspondência registrada com sucesso!");
        System.out.println("📄 Detalhes:");
        System.out.println("  • Código: " + correspondencia.getCodigo());
        System.out.println("  • Tipo: " + correspondencia.getClass().getSimpleName());
        System.out.println("  • Destinatário: " + correspondencia.getDestinatario());
        System.out.println("  • Endereço: " + correspondencia.getEndereco());
        System.out.println("  • Preço: R$ " + String.format("%.2f", correspondencia.calcularPreco()));
    }

    private static void listarCorrespondencias() throws IOException, ClassNotFoundException {
        System.out.println("\n📋 LISTA DE CORRESPONDÊNCIAS:");

        // Chamada remota para listar
        byte[] result = ClientProxy.doOperation(remoteRef, 1, new byte[0]);
        List<Correspondencia> correspondencias = Marshaller.unmarshallList(result, Correspondencia.class);

        if (correspondencias.isEmpty()) {
            System.out.println("📭 Nenhuma correspondência encontrada.");
            return;
        }

        System.out.println("┌──────────────────┬──────────┬────────────────┬─────────────────────┬──────────┐");
        System.out.println("│      Código      │   Tipo   │  Destinatário  │      Endereço       │  Preço   │");
        System.out.println("├──────────────────┼──────────┼────────────────┼─────────────────────┼──────────┤");

        for (Correspondencia c : correspondencias) {
            String tipo = c.getClass().getSimpleName();
            double preco = c.calcularPreco();

            System.out.printf("│%-18s│%-10s│%-16s│%-21s│R$ %6.2f│%n",
                    truncate(c.getCodigo(), 17),
                    tipo,
                    truncate(c.getDestinatario(), 15),
                    truncate(c.getEndereco(), 20),
                    preco);
        }

        System.out.println("└──────────────────┴──────────┴────────────────┴─────────────────────┴──────────┘");
        System.out.println("📊 Total: " + correspondencias.size() + " correspondência(s)");

        // Calcular total geral
        double totalGeral = correspondencias.stream()
                .mapToDouble(Correspondencia::calcularPreco)
                .sum();
        System.out.println("💰 Valor total: R$ " + String.format("%.2f", totalGeral));
    }

    private static void consultarPreco() throws IOException, ClassNotFoundException {
        System.out.println("\n💰 CONSULTAR PREÇO");
        System.out.print("📮 Código da correspondência: ");
        String codigo = scanner.nextLine();

        // Chamada remota para consultar preço
        byte[] args = Marshaller.marshall(codigo);
        byte[] result = ClientProxy.doOperation(remoteRef, 2, args);
        Double preco = Marshaller.unmarshall(result, Double.class);

        if (preco > 0) {
            System.out.println("💵 Preço da correspondência '" + codigo + "': R$ " + String.format("%.2f", preco));
        } else {
            System.out.println("❌ Correspondência com código '" + codigo + "' não encontrada!");
        }

        // Exibir regras de preço
        System.out.println("\n📊 Regras de Preço:");
        System.out.println("• 📧 Carta Normal: R$ 1,50");
        System.out.println("• 📧 Carta Selada: R$ 2,00");
        System.out.println("• 📦 Encomenda: R$ 5,00 por kg");
        System.out.println("• 📠 Telegrama: R$ 0,50 por palavra");
    }

    private static void entregarCorrespondencia() throws IOException, ClassNotFoundException {
        System.out.println("\n🚚 ENTREGAR CORRESPONDÊNCIA");
        System.out.print("📮 Código da correspondência: ");
        String codigo = scanner.nextLine();

        // Chamada remota para entregar
        byte[] args = Marshaller.marshall(codigo);
        byte[] result = ClientProxy.doOperation(remoteRef, 3, args);
        Boolean sucesso = Marshaller.unmarshall(result, Boolean.class);

        if (sucesso) {
            System.out.println("✅ Correspondência '" + codigo + "' marcada como entregue!");
            System.out.println("📦 A correspondência foi removida do sistema.");
        } else {
            System.out.println("❌ Correspondência '" + codigo + "' não encontrada!");
            System.out.println("🔍 Verifique se o código está correto ou se a correspondência já foi entregue.");
        }
    }

    private static String truncate(String str, int maxLength) {
        if (str == null)
            return "";
        if (str.length() <= maxLength)
            return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}
