package com.codigo.persistencia.repository;

import com.codigo.persistencia.entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<CursoEntity, Long> {

}
