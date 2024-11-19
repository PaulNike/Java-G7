package com.codigo.msregisterhexagonal.domain.aggregates.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class PersonDto {
    private Long id;
    private String numDoc;
    private String tipoDoc;
    private String nombres;
    private String apellidos;
    private Integer estado;
    private String usua_crea;
    private Timestamp date_crea;
    private String usua_upda;
    private Timestamp date_upda;
    private String usua_dele;
    private Timestamp date_dele;
}
