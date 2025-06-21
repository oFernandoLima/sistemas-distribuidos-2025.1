import java.rmi.Naming;
import java.util.Scanner;
import java.util.List;

public class ClienteCorreios {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // Localiza o serviço remoto
            Entregas servico = (Entregas) Naming.lookup("rmi://localhost:1099/EntregasService");

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
                        servico.registrarCorrespondencia(carta);
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
                        servico.registrarCorrespondencia(encomenda);
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
                        servico.registrarCorrespondencia(telegrama);
                        System.out.println("Telegrama registrado.");
                        break;
                    case 4:
                        List<Correspondencia> lista = servico.listarCorrespondencias();
                        for (Correspondencia c : lista) {
                            System.out.println("[" + c.getClass().getSimpleName() + "] Código: " + c.codigo
                                    + ", Destinatário: " + c.destinatario);
                        }
                        break;
                    case 5:
                        System.out.print("Informe o código: ");
                        String codigoPreco = sc.nextLine();
                        double preco = servico.consultarPreco(codigoPreco);
                        System.out.println("Preço: R$" + preco);
                        break;
                    case 6:
                        System.out.print("Informe o código da correspondência a entregar: ");
                        String codigoEntregar = sc.nextLine();
                        boolean sucesso = servico.entregar(codigoEntregar);
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
