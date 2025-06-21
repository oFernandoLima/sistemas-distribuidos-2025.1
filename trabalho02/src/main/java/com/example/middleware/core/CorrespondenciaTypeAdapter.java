package com.example.middleware.core;

import com.google.gson.*;
import com.example.correios.modelo.*;
import java.lang.reflect.Type;

public class CorrespondenciaTypeAdapter implements JsonSerializer<Correspondencia>, JsonDeserializer<Correspondencia> {

    @Override
    public JsonElement serialize(Correspondencia src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }

    @Override
    public Correspondencia deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Verifica se tem o campo "type" (nosso formato)
        if (jsonObject.has("type")) {
            String type = jsonObject.get("type").getAsString();
            JsonElement element = jsonObject.get("properties");

            try {
                switch (type) {
                    case "Carta":
                        return context.deserialize(element, Carta.class);
                    case "Encomenda":
                        return context.deserialize(element, Encomenda.class);
                    case "Telegrama":
                        return context.deserialize(element, Telegrama.class);
                    default:
                        throw new JsonParseException("Unknown element type: " + type);
                }
            } catch (Exception e) {
                throw new JsonParseException(e);
            }
        } else {
            // Tenta determinar o tipo pela presença de campos específicos
            if (jsonObject.has("selada")) {
                return context.deserialize(jsonObject, Carta.class);
            } else if (jsonObject.has("peso")) {
                return context.deserialize(jsonObject, Encomenda.class);
            } else if (jsonObject.has("numeroPalavras")) {
                return context.deserialize(jsonObject, Telegrama.class);
            } else {
                throw new JsonParseException("Cannot determine Correspondencia type from JSON");
            }
        }
    }
}
