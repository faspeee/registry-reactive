package com.example.stream.quarkus.registry.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonConverter() {
    }

    public static <T> String toJson(T obj) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(obj);
    }
}
