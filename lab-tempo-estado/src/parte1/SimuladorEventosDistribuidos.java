package parte1;

public class SimuladorEventosDistribuidos {
    public static void main(String[] args) {
        System.out.println("=== PARTE 1: Simulando Eventos Distribuídos ===");
        System.out.println("Objetivo: Mostrar que a ordem dos eventos pode parecer incorreta sem controle lógico");
        System.out.println();

        // Portas para os 3 processos
        int[] portas = { 8001, 8002, 8003 };

        // Cria e inicia os 3 processos
        Processo[] processos = new Processo[3];
        for (int i = 0; i < 3; i++) {
            processos[i] = new Processo(i, portas[i], portas);
            processos[i].start();
        }

        try {
            // Executa por 15 segundos
            Thread.sleep(15000);

            System.out.println("\n=== FINALIZANDO SIMULAÇÃO ===");

            // Para todos os processos
            for (Processo processo : processos) {
                processo.parar();
                processo.interrupt();
            }

            // Aguarda finalização
            for (Processo processo : processos) {
                processo.join(2000);
            }

            System.out.println("\nObservação: Note que os timestamps físicos podem não refletir");
            System.out.println("a ordem causal real dos eventos (causa-efeito).");

        } catch (InterruptedException e) {
            System.err.println("Simulação interrompida");
        }
    }
}
