package com.codigo.persistencia.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCategoria {
    private Long categoriaId;
    private String descripcion;
    private String estado;
    private int stock;
}
