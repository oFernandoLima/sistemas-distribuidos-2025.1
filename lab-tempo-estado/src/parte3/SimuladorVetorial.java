package parte3;

public class SimuladorVetorial {
    public static void main(String[] args) {
        System.out.println("=== PARTE 3: Relógios Vetoriais ===");
        System.out.println("Objetivo: Determinar a concorrência entre eventos usando vetores");
        System.out.println("Regras:");
        System.out.println("- Evento local: V[i][i]++");
        System.out.println("- Envio: envia cópia de V[i]");
        System.out.println("- Recepção: V[i][j] = max(V[i][j], Vmsg[j]) para todo j, depois V[i][i]++");
        System.out.println();

        // Portas para os 3 processos
        int[] portas = { 8021, 8022, 8023 };

        // Cria e inicia os 3 processos
        ProcessoVetorial[] processos = new ProcessoVetorial[3];
        for (int i = 0; i < 3; i++) {
            processos[i] = new ProcessoVetorial(i, portas[i], portas);
            processos[i].start();
        }

        try {
            // Executa por 20 segundos (mais tempo para observar concorrência)
            Thread.sleep(20000);

            System.out.println("\n=== FINALIZANDO SIMULAÇÃO ===");

            // Para todos os processos
            for (ProcessoVetorial processo : processos) {
                processo.parar();
                processo.interrupt();
            }

            // Aguarda finalização
            for (ProcessoVetorial processo : processos) {
                processo.join(2000);
            }

            System.out.println("\nObservação: Relógios vetoriais permitem detectar:");
            System.out.println("1. Relação happened-before (A → B)");
            System.out.println("2. Eventos concorrentes (A || B)");
            System.out.println("3. Ordem causal precisa entre eventos");

        } catch (InterruptedException e) {
            System.err.println("Simulação interrompida");
        }
    }
}
