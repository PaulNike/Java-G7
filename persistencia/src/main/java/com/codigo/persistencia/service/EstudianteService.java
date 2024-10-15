package com.codigo.persistencia.service;

import com.codigo.persistencia.aggregates.request.RequestEstudiante;
import com.codigo.persistencia.entity.EstudianteEntity;

import java.util.List;

public interface EstudianteService {

    EstudianteEntity guardarEstudiante(RequestEstudiante estudiante);
    EstudianteEntity obtenerEstudiantePorId(Long id);
    List<EstudianteEntity> obtenerTodosLosEstudiantes();
    EstudianteEntity actualizarEstudiante(Long id, RequestEstudiante estudiante);
    void eliminarEstudiante(Long id);

}
