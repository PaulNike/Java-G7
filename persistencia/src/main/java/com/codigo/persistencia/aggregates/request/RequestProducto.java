package com.codigo.persistencia.aggregates.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RequestProducto {
    private String nombre;
    private Set<RequestCategoria> categorias;
}
