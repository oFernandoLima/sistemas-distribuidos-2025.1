package parte3;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Random;

public class ProcessoVetorial extends Thread {
    private int id;
    private int porta;
    private int[] portasPares;
    private Random random;
    private ServerSocket serverSocket;
    private int[] relogioVetorial; // V[i]
    private int numProcessos;

    public ProcessoVetorial(int id, int porta, int[] portasPares) {
        this.id = id;
        this.porta = porta;
        this.portasPares = portasPares;
        this.random = new Random();
        this.numProcessos = portasPares.length;
        this.relogioVetorial = new int[numProcessos];
        // Inicializa vetor com zeros
        Arrays.fill(relogioVetorial, 0);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(porta);
            System.out.println("Processo " + id + " iniciado na porta " + porta +
                    " [Vetor: " + Arrays.toString(relogioVetorial) + "]");

            // Thread para receber mensagens
            new Thread(this::receberMensagens).start();

            // Thread para enviar mensagens
            new Thread(this::enviarMensagens).start();

            // Simula eventos internos
            simularEventosInternos();

        } catch (IOException e) {
            System.err.println("Erro no processo " + id + ": " + e.getMessage());
        }
    }

    private synchronized void eventoLocal() {
        // Evento local: V[i][i]++
        relogioVetorial[id]++;
    }

    private synchronized void atualizarVetor(int[] vetorRecebido) {
        // Recepção: V[i][j] = max(V[i][j], Vmsg[j]) para todo j
        for (int j = 0; j < numProcessos; j++) {
            relogioVetorial[j] = Math.max(relogioVetorial[j], vetorRecebido[j]);
        }
        // Depois V[i][i]++
        relogioVetorial[id]++;
    }

    private synchronized int[] obterVetor() {
        return relogioVetorial.clone();
    }

    private void receberMensagens() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                String linha = reader.readLine();
                String[] partes = linha.split("\\|");
                String mensagem = partes[0];

                // Parse do vetor recebido
                String vetorStr = partes[1].substring(1, partes[1].length() - 1); // Remove [ ]
                String[] vetorPartes = vetorStr.split(", ");
                int[] vetorRecebido = new int[numProcessos];
                for (int i = 0; i < numProcessos; i++) {
                    vetorRecebido[i] = Integer.parseInt(vetorPartes[i]);
                }

                // Aplica regra dos relógios vetoriais
                atualizarVetor(vetorRecebido);

                System.out.println("Processo " + id + " [V=" + Arrays.toString(obterVetor()) +
                        "] RECEBEU: " + mensagem + " (vetor enviado: " + Arrays.toString(vetorRecebido) + ")");

                // Verifica relação de concorrência
                verificarConcorrencia(vetorRecebido);

                socket.close();

                // Sleep aleatório após recebimento
                Thread.sleep(random.nextInt(1000) + 500);
            }
        } catch (IOException | InterruptedException e) {
            // Thread interrompida ou erro de I/O
        }
    }

    private void enviarMensagens() {
        try {
            Thread.sleep(2000); // Aguarda outros processos iniciarem

            for (int i = 0; i < 5; i++) {
                // Evento local antes de enviar
                eventoLocal();

                // Escolhe um processo aleatório para enviar mensagem
                int processoDestino = random.nextInt(portasPares.length);
                if (processoDestino != id) {
                    enviarMensagem(portasPares[processoDestino],
                            "Mensagem " + i + " do processo " + id, obterVetor());
                }

                // Sleep aleatório entre envios
                Thread.sleep(random.nextInt(2000) + 1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void enviarMensagem(int portaDestino, String mensagem, int[] vetorAtual) {
        try {
            Socket socket = new Socket("localhost", portaDestino);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Envia mensagem junto com o vetor
            writer.println(mensagem + "|" + Arrays.toString(vetorAtual));

            System.out.println("Processo " + id + " [V=" + Arrays.toString(vetorAtual) +
                    "] ENVIOU: " + mensagem + " para porta " + portaDestino);

            socket.close();

            // Sleep aleatório após envio
            Thread.sleep(random.nextInt(500) + 200);

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    private void simularEventosInternos() {
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(random.nextInt(1500) + 1000);

                // Evento local
                eventoLocal();

                System.out.println("Processo " + id + " [V=" + Arrays.toString(obterVetor()) +
                        "] EVENTO INTERNO: " + i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void verificarConcorrencia(int[] vetorRecebido) {
        int[] meuVetor = obterVetor();

        // Verifica se um evento aconteceu antes do outro (happened-before)
        boolean meuAntes = true;
        boolean outroAntes = true;

        for (int i = 0; i < numProcessos; i++) {
            if (meuVetor[i] > vetorRecebido[i]) {
                outroAntes = false;
            }
            if (vetorRecebido[i] > meuVetor[i]) {
                meuAntes = false;
            }
        }

        if (meuAntes && !outroAntes) {
            System.out.println("    → Meu evento aconteceu ANTES do evento recebido");
        } else if (outroAntes && !meuAntes) {
            System.out.println("    → Evento recebido aconteceu ANTES do meu evento");
        } else if (!meuAntes && !outroAntes) {
            System.out.println("    → Eventos são CONCORRENTES (não há relação causal)");
        }
    }

    public void parar() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Erro ao fechar servidor: " + e.getMessage());
        }
    }
}
