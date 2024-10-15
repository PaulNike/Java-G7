package com.codigo.persistencia.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "estudiante")
@Getter
@Setter
public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @ManyToMany
    @JoinTable(
         name = "estudiante_curso",
         joinColumns = @JoinColumn(name = "estudiante_id"),
         inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    @JsonManagedReference
    private Set<CursoEntity> cursos;

}
