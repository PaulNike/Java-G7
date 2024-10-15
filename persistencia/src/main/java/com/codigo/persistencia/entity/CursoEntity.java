package com.codigo.persistencia.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "curso")
@Getter
@Setter
public class CursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;

    @ManyToMany(mappedBy = "cursos")
    @JsonBackReference
    //@JsonIgnore
    private Set<EstudianteEntity> estudiantes;

}
