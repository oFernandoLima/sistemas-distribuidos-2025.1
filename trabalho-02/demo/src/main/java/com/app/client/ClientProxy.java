package com.app.client;

import java.net.*;
import com.app.protocol.*;

public class ClientProxy {
    private int requestCounter = 0;
    
    public byte[] doOperation(RemoteObjectRef o, String methodId, byte[] arguments) {
        try {
            // Criar socket UDP para comunicação
            DatagramSocket socket = new DatagramSocket();
            
            // Criar mensagem de requisição
            RequestMessage request = new RequestMessage(
                ++requestCounter, 
                o.getObjectName(), 
                methodId, 
                arguments
            );
            
            // Serializar mensagem
            byte[] requestData = MessageSerializer.serialize(request);
            
            // Enviar requisição
            DatagramPacket packet = new DatagramPacket(
                requestData, 
                requestData.length, 
                o.getHost(), 
                o.getPort()
            );
            socket.send(packet);
            
            // Receber resposta
            byte[] buffer = new byte[8192];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);
            
            // Deserializar resposta
            byte[] responseData = new byte[responsePacket.getLength()];
            System.arraycopy(buffer, 0, responseData, 0, responsePacket.getLength());
            
            ReplyMessage reply = MessageSerializer.deserialize(responseData, ReplyMessage.class);
            
            socket.close();
            return reply.getResult();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro na invocação remota: " + e.getMessage(), e);
        }
    }
}