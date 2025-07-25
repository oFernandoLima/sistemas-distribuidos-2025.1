package parte2;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ProcessoLamport extends Thread {
    private int id;
    private int porta;
    private int[] portasPares;
    private Random random;
    private ServerSocket serverSocket;
    private int relogioLogico; // L(p)

    public ProcessoLamport(int id, int porta, int[] portasPares) {
        this.id = id;
        this.porta = porta;
        this.portasPares = portasPares;
        this.random = new Random();
        this.relogioLogico = 0;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(porta);
            System.out.println("Processo " + id + " iniciado na porta " + porta +
                    " [Relógio: " + relogioLogico + "]");

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

    private synchronized void incrementarRelogio() {
        relogioLogico++;
    }

    private synchronized void atualizarRelogio(int relogioRecebido) {
        relogioLogico = Math.max(relogioLogico, relogioRecebido) + 1;
    }

    private synchronized int obterRelogio() {
        return relogioLogico;
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
                int relogioEnviado = Integer.parseInt(partes[1]);

                // Aplica regra de Lamport: L(p) = max(L(recebido), L(p)) + 1
                atualizarRelogio(relogioEnviado);

                System.out.println("Processo " + id + " [L=" + obterRelogio() +
                        "] RECEBEU: " + mensagem + " (relógio enviado: " + relogioEnviado + ")");

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
                // Incrementa relógio antes de enviar
                incrementarRelogio();

                // Escolhe um processo aleatório para enviar mensagem
                int processoDestino = random.nextInt(portasPares.length);
                if (processoDestino != id) {
                    enviarMensagem(portasPares[processoDestino],
                            "Mensagem " + i + " do processo " + id, obterRelogio());
                }

                // Sleep aleatório entre envios
                Thread.sleep(random.nextInt(2000) + 1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void enviarMensagem(int portaDestino, String mensagem, int relogioAtual) {
        try {
            Socket socket = new Socket("localhost", portaDestino);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Envia mensagem junto com o relógio lógico
            writer.println(mensagem + "|" + relogioAtual);

            System.out.println("Processo " + id + " [L=" + relogioAtual +
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

                // Incrementa relógio para evento interno
                incrementarRelogio();

                System.out.println("Processo " + id + " [L=" + obterRelogio() +
                        "] EVENTO INTERNO: " + i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
