package com.codigo.persistencia.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class ProductoCategoriaId implements Serializable {

    private Long productoId;
    private Long categoriaId;
}
