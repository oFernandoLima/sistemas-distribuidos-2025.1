package com.app.protocol;

import java.io.Serializable;

public class ReplyMessage implements Serializable {
    private int requestId;
    private byte[] result;

    public ReplyMessage(int requestId, byte[] result) {
        this.requestId = requestId;
        this.result = result;
    }

    public int getRequestId() { return requestId; }
    public byte[] getResult() { return result; }
}