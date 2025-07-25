package parte2;

public class SimuladorLamport {
    public static void main(String[] args) {
        System.out.println("=== PARTE 2: Relógio Lógico de Lamport ===");
        System.out.println("Objetivo: Observar a consistência causal entre eventos");
        System.out.println("Regras:");
        System.out.println("- Evento interno: L(p)++");
        System.out.println("- Envio: envia L(p) atual");
        System.out.println("- Recepção: L(p) = max(L(recebido), L(p)) + 1");
        System.out.println();

        // Portas para os 3 processos
        int[] portas = { 8011, 8012, 8013 };

        // Cria e inicia os 3 processos
        ProcessoLamport[] processos = new ProcessoLamport[3];
        for (int i = 0; i < 3; i++) {
            processos[i] = new ProcessoLamport(i, portas[i], portas);
            processos[i].start();
        }

        try {
            // Executa por 15 segundos
            Thread.sleep(15000);

            System.out.println("\n=== FINALIZANDO SIMULAÇÃO ===");

            // Para todos os processos
            for (ProcessoLamport processo : processos) {
                processo.parar();
                processo.interrupt();
            }

            // Aguarda finalização
            for (ProcessoLamport processo : processos) {
                processo.join(2000);
            }

            System.out.println("\nObservação: Os relógios lógicos mantêm a ordem causal:");
            System.out.println("Se evento A acontece antes de B, então L(A) < L(B)");

        } catch (InterruptedException e) {
            System.err.println("Simulação interrompida");
        }
    }
}
