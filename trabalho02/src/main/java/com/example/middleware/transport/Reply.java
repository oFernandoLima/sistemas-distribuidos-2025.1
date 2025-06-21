package com.example.middleware.transport;

import java.io.Serializable;

public class Reply implements Serializable {
    public int messageType = 1; // 1 = Reply
    public int requestId;
    public byte[] result;

    public Reply(int requestId, byte[] result) {
        this.requestId = requestId;
        this.result = result;
    }
}
