import parte1.SimuladorEventosDistribuidos;
import parte2.SimuladorLamport;
import parte3.SimuladorVetorial;

import java.util.Scanner;

public class LaboratorioTempoEstado {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("LABORATÓRIO TEMPO E ESTADO GLOBAL");
        System.out.println("======================================");
        System.out.println();
        System.out.println("Escolha qual parte executar:");
        System.out.println("1. Parte 1 - Simulando Eventos Distribuídos (Relógio Físico)");
        System.out.println("2. Parte 2 - Relógio Lógico de Lamport");
        System.out.println("3. Parte 3 - Relógios Vetoriais");
        System.out.println("4. Executar todas as partes sequencialmente");
        System.out.println("0. Sair");
        System.out.println();
        System.out.print("Opção: ");

        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                executarParte1();
                break;
            case 2:
                executarParte2();
                break;
            case 3:
                executarParte3();
                break;
            case 4:
                executarTodasPartes();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida!");
        }

        scanner.close();
    }

    private static void executarParte1() {
        System.out.println("\n>>> EXECUTANDO PARTE 1 <<<");
        SimuladorEventosDistribuidos.main(new String[] {});
        aguardarEnter();
    }

    private static void executarParte2() {
        System.out.println("\n>>> EXECUTANDO PARTE 2 <<<");
        SimuladorLamport.main(new String[] {});
        aguardarEnter();
    }

    private static void executarParte3() {
        System.out.println("\n>>> EXECUTANDO PARTE 3 <<<");
        SimuladorVetorial.main(new String[] {});
        aguardarEnter();
    }

    private static void executarTodasPartes() {
        executarParte1();
        executarParte2();
        executarParte3();

        System.out.println("\n======================================");
        System.out.println("RESUMO DAS OBSERVAÇÕES:");
        System.out.println("======================================");
        System.out.println();
        System.out.println("PARTE 1 (Relógio Físico):");
        System.out.println("- Timestamps físicos podem não refletir ordem causal");
        System.out.println("- Eventos podem parecer fora de ordem");
        System.out.println();
        System.out.println("PARTE 2 (Relógio de Lamport):");
        System.out.println("- Mantém ordem causal: se A → B, então L(A) < L(B)");
        System.out.println("- Não detecta concorrência (L(A) < L(B) não implica A → B)");
        System.out.println();
        System.out.println("PARTE 3 (Relógios Vetoriais):");
        System.out.println("- Detecta tanto ordem causal quanto concorrência");
        System.out.println("- Permite determinar se eventos são concurrent es ou causalmente relacionados");
        System.out.println("- Mais preciso para detectar dependências entre eventos");
    }

    private static void aguardarEnter() {
        System.out.println("\nPressione Enter para continuar...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignora exceção
        }
    }
}
