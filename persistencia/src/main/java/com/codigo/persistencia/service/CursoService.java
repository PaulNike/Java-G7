package com.codigo.persistencia.service;

import com.codigo.persistencia.aggregates.request.RequestCurso;
import com.codigo.persistencia.entity.CursoEntity;

import java.util.List;

public interface CursoService {
    CursoEntity guardarCurso(RequestCurso request);
    CursoEntity obtenerCursoPorId(Long id);
    List<CursoEntity> obtenerTodosLosCursos();
    CursoEntity actualizarCurso(Long id, CursoEntity curso);
    void eliminarCurso(Long id);
}
