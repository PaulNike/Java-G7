package com.codigo.msregisterhexagonal.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "person")
@Builder
@Getter
@Setter
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numDoc;
    private String tipoDoc;
    private String nombres;
    private String apellidos;
    private Integer estado;
    private String usua_crea;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp date_crea;
    private String usua_upda;
    private Timestamp date_upda;
    private String usua_dele;
    private Timestamp date_dele;


}
