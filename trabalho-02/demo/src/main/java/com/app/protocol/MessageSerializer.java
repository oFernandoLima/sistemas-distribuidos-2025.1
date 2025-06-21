package com.app.protocol;

import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;

public class MessageSerializer {
    private static final Gson gson = new Gson();

    public static byte[] serialize(Object obj) {
        return gson.toJson(obj).getBytes(StandardCharsets.UTF_8);
    }

    public static <T> T deserialize(byte[] data, Class<T> clazz) {
        String json = new String(data, StandardCharsets.UTF_8);
        return gson.fromJson(json, clazz);
    }
}