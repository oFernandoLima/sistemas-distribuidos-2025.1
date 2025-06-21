package com.example.middleware.transport;

import java.io.Serializable;

public class Request implements Serializable {
    public int messageType = 0;
    public int requestId;
    public RemoteObjectRef objectRef;
    public int methodId;
    public byte[] arguments;

    public Request(int requestId, RemoteObjectRef ref, int methodId, byte[] args) {
        this.requestId = requestId;
        this.objectRef = ref;
        this.methodId = methodId;
        this.arguments = args;
    }
}
