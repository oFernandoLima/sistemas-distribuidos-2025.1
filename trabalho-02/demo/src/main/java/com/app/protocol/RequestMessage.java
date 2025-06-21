package com.app.protocol;

import java.io.Serializable;
import java.net.InetAddress;

public class RequestMessage implements Serializable {
    private int requestId;
    private String objectReference;
    private String methodId;
    private byte[] arguments;

    // Campos adicionais para resposta
    public InetAddress clientHost;
    public int clientPort;

    public RequestMessage(int requestId, String objectReference, String methodId, byte[] arguments) {
        this.requestId = requestId;
        this.objectReference = objectReference;
        this.methodId = methodId;
        this.arguments = arguments;
    }

    // Getters
    public int getRequestId() { return requestId; }
    public String getObjectReference() { return objectReference; }
    public String getMethodId() { return methodId; }
    public byte[] getArguments() { return arguments; }
}