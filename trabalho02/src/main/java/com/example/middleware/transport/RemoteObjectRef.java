package com.example.middleware.transport;

import java.io.Serializable;

public class RemoteObjectRef implements Serializable {
    public String serviceName;

    public RemoteObjectRef(String serviceName) {
        this.serviceName = serviceName;
    }
}
