package com.app.client;

import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;

import com.app.domain.Carta;
import com.app.domain.Correspondencia;
import com.app.domain.Encomenda;
import com.app.domain.Telegrama;
import com.app.protocol.MessageSerializer;
import com.app.protocol.RemoteObjectRef;

public class ClienteCorreios {
    private ClientProxy proxy;
    private RemoteObjectRef serverRef;

    public ClienteCorreios() throws Exception {
        this.proxy = new ClientProxy();
        this.serverRef = new RemoteObjectRef(
            "EntregasService", 
            InetAddress.getByName("localhost"), 
            9999
        );
    }

    public void registrarCorrespondencia(Correspondencia c) throws Exception {
        byte[] args = MessageSerializer.serialize(c);
        proxy.doOperation(serverRef, "registrarCorrespondencia", args);
    }

    public double consultarPreco(String codigo) throws Exception {
        byte[] args = MessageSerializer.serialize(codigo);
        byte[] result = proxy.doOperation(serverRef, "consultarPreco", args);
        return MessageSerializer.deserialize(result, Double.class);
    }

    public List<Correspondencia> listarCorrespondencias() throws Exception {
        byte[] result = proxy.doOperation(serverRef, "listarCorrespondencias", new byte[0]);
        return MessageSerializer.deserialize(result, List.class);
    }

    public boolean entregar(String codigo) throws Exception {
        byte[] args = MessageSerializer.serialize(codigo);
        byte[] result = proxy.doOperation(serverRef, "entregar", args);
        return MessageSerializer.deserialize(result, Boolean.class);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            ClienteCorreios cliente = new ClienteCorreios();

            while (true) {
                System.out.println("\n--- MENU ---");
                System.out.println("1. Registrar Carta");
                System.out.println("2. Registrar Encomenda");
                System.out.println("3. Registrar Telegrama");
                System.out.println("4. Listar Correspondências");
                System.out.println("5. Consultar Preço");
                System.out.println("6. Entregar Correspondência");
                System.out.println("0. Sair");
                System.out.print("Opção: ");
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.print("Código: ");
                        String codC = sc.nextLine();
                        System.out.print("Destinatário: ");
                        String destC = sc.nextLine();
                        System.out.print("Endereço: ");
                        String endC = sc.nextLine();
                        System.out.print("Está selada (true/false)? ");
                        boolean selada = sc.nextBoolean();
                        sc.nextLine();
                        Carta carta = new Carta(codC, destC, endC, selada);
                        cliente.registrarCorrespondencia(carta);
                        System.out.println("Carta registrada.");
                        break;
                    case 2:
                        System.out.print("Código: ");
                        String codE = sc.nextLine();
                        System.out.print("Destinatário: ");
                        String destE = sc.nextLine();
                        System.out.print("Endereço: ");
                        String endE = sc.nextLine();
                        System.out.print("Peso (kg): ");
                        double peso = sc.nextDouble();
                        sc.nextLine();
                        Encomenda encomenda = new Encomenda(codE, destE, endE, peso);
                        cliente.registrarCorrespondencia(encomenda);
                        System.out.println("Encomenda registrada.");
                        break;
                    case 3:
                        System.out.print("Código: ");
                        String codT = sc.nextLine();
                        System.out.print("Destinatário: ");
                        String destT = sc.nextLine();
                        System.out.print("Endereço: ");
                        String endT = sc.nextLine();
                        System.out.print("Número de palavras: ");
                        int palavras = sc.nextInt();
                        sc.nextLine();
                        Telegrama telegrama = new Telegrama(codT, destT, endT, palavras);
                        cliente.registrarCorrespondencia(telegrama);
                        System.out.println("Telegrama registrado.");
                        break;
                    case 4:
                        List<Correspondencia> lista = cliente.listarCorrespondencias();
                        for (Object obj : lista) {
                            System.out.println("Correspondência: " + obj.toString());
                        }
                        break;
                    case 5:
                        System.out.print("Informe o código: ");
                        String codigoPreco = sc.nextLine();
                        double preco = cliente.consultarPreco(codigoPreco);
                        System.out.println("Preço: R$" + preco);
                        break;
                    case 6:
                        System.out.print("Informe o código da correspondência a entregar: ");
                        String codigoEntregar = sc.nextLine();
                        boolean sucesso = cliente.entregar(codigoEntregar);
                        System.out.println(sucesso ? "Entregue com sucesso." : "Código não encontrado.");
                        break;
                    case 0:
                        System.out.println("Encerrando cliente.");
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.getMessage());
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}