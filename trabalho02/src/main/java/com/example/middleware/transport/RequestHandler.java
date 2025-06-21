package com.example.middleware.transport;

import java.io.IOException;
import java.net.*;

public class RequestHandler {
    private DatagramSocket socket;
    private InetAddress clientAddress;
    private int clientPort;

    public RequestHandler(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public byte[] getRequest() throws IOException {
        byte[] buffer = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        // Salva o endereço e porta do cliente para a resposta
        this.clientAddress = packet.getAddress();
        this.clientPort = packet.getPort();

        // Retorna apenas os bytes realmente recebidos
        byte[] data = new byte[packet.getLength()];
        System.arraycopy(packet.getData(), packet.getOffset(), data, 0, packet.getLength());
        return data;
    }

    public void sendReply(byte[] reply) throws IOException {
        DatagramPacket packet = new DatagramPacket(reply, reply.length, clientAddress, clientPort);
        socket.send(packet);
    }
}
