package com.codigo.restTemplate.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /* METODOS UTILIZANDO TRY CATCH PARA ENCAPUSALR EXEPCIONES */
    /*public static <T> String convertirAString(T objeto){
        try {
            return OBJECT_MAPPER.writeValueAsString(objeto);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error al convertir la Clase a Cadena(String) : "+ e);
        }
    }

    public static <T> T convertirDesdeString(String json, Class<T> tipoClase){
        try {
            return OBJECT_MAPPER.readValue(json,tipoClase);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error al convertir desde String a la Clase: "+ e);
        }
    }*/

    /* Mismos metodos pero con un maejo diferente de encapsular errores,
    este metodo usar la firma de excepciones: throws*/
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
