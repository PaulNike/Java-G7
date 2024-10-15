package com.codigo.persistencia.repository;

import com.codigo.persistencia.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {
}
