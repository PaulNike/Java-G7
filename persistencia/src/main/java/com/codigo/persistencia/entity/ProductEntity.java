package com.codigo.persistencia.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "product")
@Getter
@Setter
//@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //nombreProducto
    //@JsonProperty("name_producto")
    @Column(name = "nombre_producto", length = 100, unique = true)
    private String nombre_producto;

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Column(name = "created_by", nullable = false)
    private String created_by;

    @Column(name = "created_date", nullable = false)
    private Timestamp created_date;

    @Column(name = "update_by")
    private String update_by;

    @Column(name = "update_date")
    private Timestamp update_date;

    @Column(name = "delete_by")
    private String delete_by;

    @Column(name = "delete_date")
    private Timestamp delete_date;

}
