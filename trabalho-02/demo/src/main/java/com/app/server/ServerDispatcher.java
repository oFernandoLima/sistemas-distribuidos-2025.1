package com.app.server;

import java.net.*;
import java.util.List;

import com.app.domain.Correspondencia;
import com.app.domain.LojaCorreios;
import com.app.protocol.MessageSerializer;
import com.app.protocol.ReplyMessage;
import com.app.protocol.RequestMessage;

public class ServerDispatcher {
    private DatagramSocket socket;
    private LojaCorreios loja;
    private boolean running;

    public ServerDispatcher(int port, String nomeLoja) throws Exception {
        this.socket = new DatagramSocket(port);
        this.loja = new LojaCorreios(nomeLoja);
        this.running = true;
    }

    public RequestMessage getRequest() throws Exception {
        byte[] buffer = new byte[8192];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        
        byte[] data = new byte[packet.getLength()];
        System.arraycopy(buffer, 0, data, 0, packet.getLength());
        
        RequestMessage request = MessageSerializer.deserialize(data, RequestMessage.class);
        // Armazenar informações do cliente para resposta
        request.clientHost = packet.getAddress();
        request.clientPort = packet.getPort();
        
        return request;
    }

    public void sendReply(byte[] reply, InetAddress clientHost, int clientPort) throws Exception {
        DatagramPacket packet = new DatagramPacket(reply, reply.length, clientHost, clientPort);
        socket.send(packet);
    }

    public void processRequests() {
        while (running) {
            try {
                RequestMessage request = getRequest();
                byte[] result = executeMethod(request);
                
                ReplyMessage reply = new ReplyMessage(request.getRequestId(), result);
                byte[] replyData = MessageSerializer.serialize(reply);
                
                sendReply(replyData, request.clientHost, request.clientPort);
                
            } catch (Exception e) {
                System.err.println("Erro processando requisição: " + e.getMessage());
            }
        }
    }

    private byte[] executeMethod(RequestMessage request) throws Exception {
        String methodId = request.getMethodId();
        byte[] arguments = request.getArguments();

        switch (methodId) {
            case "registrarCorrespondencia":
                Correspondencia c = MessageSerializer.deserialize(arguments, Correspondencia.class);
                loja.adicionarCorrespondencia(c);
                return MessageSerializer.serialize("OK");

            case "consultarPreco":
                String codigo = MessageSerializer.deserialize(arguments, String.class);
                for (Correspondencia corr : loja.getCorrespondencias()) {
                    if (corr.getCodigo().equals(codigo)) {
                        return MessageSerializer.serialize(corr.calcularPreco());
                    }
                }
                return MessageSerializer.serialize(0.0);

            case "listarCorrespondencias":
                List<Correspondencia> lista = loja.getCorrespondencias();
                return MessageSerializer.serialize(lista);

            case "entregar":
                String codigoEntregar = MessageSerializer.deserialize(arguments, String.class);
                boolean removido = loja.getCorrespondencias().removeIf(
                    corr -> corr.getCodigo().equals(codigoEntregar)
                );
                return MessageSerializer.serialize(removido);

            default:
                throw new Exception("Método não encontrado: " + methodId);
        }
    }

    public void stop() {
        running = false;
        socket.close();
    }
}