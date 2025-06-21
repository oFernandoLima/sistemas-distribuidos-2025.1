package com.example.middleware.core;

import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.correios.modelo.Correspondencia;
import java.util.List;
import java.lang.reflect.Type;

public class Marshaller {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Correspondencia.class, new CorrespondenciaTypeAdapter())
            .create();

    // Serializa objeto para JSON e transforma em byte[]
    public static byte[] marshall(Object obj) {
        String json = gson.toJson(obj);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    // Desserializa byte[] para objeto da classe especificada
    public static <T> T unmarshall(byte[] data, Class<T> clazz) {
        String json = new String(data, StandardCharsets.UTF_8).trim();
        return gson.fromJson(json, clazz);
    }

    // Método especial para deserializar listas com tipos genéricos
    public static <T> List<T> unmarshallList(byte[] data, Class<T> elementType) {
        String json = new String(data, StandardCharsets.UTF_8).trim();
        Type listType = com.google.gson.reflect.TypeToken.getParameterized(List.class, elementType).getType();
        return gson.fromJson(json, listType);
    }
}
