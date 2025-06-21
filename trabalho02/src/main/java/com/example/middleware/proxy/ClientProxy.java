package com.example.middleware.proxy;

import java.io.IOException;
import java.net.*;

import com.example.middleware.core.Marshaller;
import com.example.middleware.transport.*;

public class ClientProxy {
    public static byte[] doOperation(RemoteObjectRef o, int methodId, byte[] arguments)
            throws IOException, ClassNotFoundException {
        Request req = new Request(1, o, methodId, arguments);
        byte[] reqData = Marshaller.marshall(req);
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        int serverPort = 5052;

        DatagramPacket packet = new DatagramPacket(reqData, reqData.length, serverAddress, serverPort);
        socket.send(packet);

        byte[] buffer = new byte[4096];
        DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(responsePacket);

        Reply reply = Marshaller.unmarshall(responsePacket.getData(), Reply.class);
        socket.close();
        return reply.result;
    }
}
