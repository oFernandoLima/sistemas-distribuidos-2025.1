package com.example.correios.app;

import java.io.IOException;
import java.util.List;

import com.example.correios.modelo.*;
import com.example.middleware.core.Marshaller;
import com.example.middleware.proxy.ClientProxy;
import com.example.middleware.transport.RemoteObjectRef;

/**
 * Demonstração do cliente automatizada para testes
 */
public class DemoCliente {
    private static final RemoteObjectRef remoteRef = new RemoteObjectRef("EntregasService");

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("🎭 DEMONSTRAÇÃO DO CLIENTE CORREIOS - UDP");
        System.out.println("=================================================");

        try {
            // 1. Registrar algumas correspondências
            System.out.println("\n📝 1. REGISTRANDO CORRESPONDÊNCIAS...");
            registrarCorrespondencias();

            // 2. Listar todas as correspondências
            System.out.println("\n📋 2. LISTANDO CORRESPONDÊNCIAS...");
            listarCorrespondencias();

            // 3. Consultar preços
            System.out.println("\n💰 3. CONSULTANDO PREÇOS...");
            consultarPrecos();

            // 4. Entregar uma correspondência
            System.out.println("\n🚚 4. ENTREGANDO CORRESPONDÊNCIA...");
            entregarCorrespondencia();

            // 5. Listar novamente para mostrar a remoção
            System.out.println("\n📋 5. LISTANDO APÓS ENTREGA...");
            listarCorrespondencias();

            System.out.println("\n✅ Demonstração concluída com sucesso!");

        } catch (Exception e) {
            System.err.println("❌ Erro na demonstração: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void registrarCorrespondencias() throws IOException, ClassNotFoundException {
        // Registrar uma carta
        Carta carta = new Carta("CARTA001", "João Silva", "Rua das Flores, 123", true);
        byte[] args1 = Marshaller.marshall(carta);
        ClientProxy.doOperation(remoteRef, 0, args1);
        System.out.println("✅ Carta selada registrada: " + carta.getCodigo());

        // Registrar uma encomenda
        Encomenda encomenda = new Encomenda("ENC002", "Maria Santos", "Av. Principal, 456", 2.5);
        byte[] args2 = Marshaller.marshall(encomenda);
        ClientProxy.doOperation(remoteRef, 0, args2);
        System.out.println("✅ Encomenda registrada: " + encomenda.getCodigo());

        // Registrar um telegrama
        Telegrama telegrama = new Telegrama("TEL003", "Pedro Costa", "Praça Central, 789", 15);
        byte[] args3 = Marshaller.marshall(telegrama);
        ClientProxy.doOperation(remoteRef, 0, args3);
        System.out.println("✅ Telegrama registrado: " + telegrama.getCodigo());

        // Registrar uma carta normal
        Carta cartaNormal = new Carta("CARTA004", "Ana Oliveira", "Rua do Comércio, 321", false);
        byte[] args4 = Marshaller.marshall(cartaNormal);
        ClientProxy.doOperation(remoteRef, 0, args4);
        System.out.println("✅ Carta normal registrada: " + cartaNormal.getCodigo());
    }

    private static void listarCorrespondencias() throws IOException, ClassNotFoundException {
        byte[] result = ClientProxy.doOperation(remoteRef, 1, new byte[0]);
        List<Correspondencia> correspondencias = Marshaller.unmarshallList(result, Correspondencia.class);

        if (correspondencias.isEmpty()) {
            System.out.println("📭 Nenhuma correspondência encontrada.");
            return;
        }

        System.out.println("┌──────────────────┬──────────┬────────────────┬─────────────────────┬──────────┐");
        System.out.println("│      Código      │   Tipo   │  Destinatário  │      Endereço       │  Preço   │");
        System.out.println("├──────────────────┼──────────┼────────────────┼─────────────────────┼──────────┤");

        double totalGeral = 0;
        for (Correspondencia c : correspondencias) {
            String tipo = c.getClass().getSimpleName();
            double preco = c.calcularPreco();
            totalGeral += preco;

            System.out.printf("│%-18s│%-10s│%-16s│%-21s│R$ %6.2f│%n",
                    truncate(c.getCodigo(), 17),
                    tipo,
                    truncate(c.getDestinatario(), 15),
                    truncate(c.getEndereco(), 20),
                    preco);
        }

        System.out.println("└──────────────────┴──────────┴────────────────┴─────────────────────┴──────────┘");
        System.out.println("📊 Total: " + correspondencias.size() + " correspondência(s)");
        System.out.println("💰 Valor total: R$ " + String.format("%.2f", totalGeral));
    }

    private static void consultarPrecos() throws IOException, ClassNotFoundException {
        String[] codigos = { "CARTA001", "ENC002", "TEL003", "CARTA004", "INEXISTENTE" };

        for (String codigo : codigos) {
            byte[] args = Marshaller.marshall(codigo);
            byte[] result = ClientProxy.doOperation(remoteRef, 2, args);
            Double preco = Marshaller.unmarshall(result, Double.class);

            if (preco > 0) {
                System.out.println("💵 " + codigo + ": R$ " + String.format("%.2f", preco));
            } else {
                System.out.println("❌ " + codigo + ": não encontrada");
            }
        }
    }

    private static void entregarCorrespondencia() throws IOException, ClassNotFoundException {
        String codigo = "TEL003";
        byte[] args = Marshaller.marshall(codigo);
        byte[] result = ClientProxy.doOperation(remoteRef, 3, args);
        Boolean sucesso = Marshaller.unmarshall(result, Boolean.class);

        if (sucesso) {
            System.out.println("✅ Correspondência '" + codigo + "' entregue e removida do sistema");
        } else {
            System.out.println("❌ Falha ao entregar correspondência '" + codigo + "'");
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
