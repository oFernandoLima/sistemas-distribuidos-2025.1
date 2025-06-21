package com.app.protocol;

import java.io.Serializable;
import java.net.InetAddress;

public class RemoteObjectRef implements Serializable {
    private String objectName;
    private InetAddress host;
    private int port;

    public RemoteObjectRef(String objectName, InetAddress host, int port) {
        this.objectName = objectName;
        this.host = host;
        this.port = port;
    }

    public String getObjectName() { return objectName; }
    public InetAddress getHost() { return host; }
    public int getPort() { return port; }
}
