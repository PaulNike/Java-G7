package com.codigo.Retrofit_Prodriguez.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String convertirAString(T object)
            throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    public static <T> T convertirDesdeString(String datoDeRedis,
                                             Class<T> tipoClase)
            throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(datoDeRedis, tipoClase);
    }
}
