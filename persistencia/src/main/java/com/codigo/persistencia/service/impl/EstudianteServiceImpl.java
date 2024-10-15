package com.codigo.persistencia.service.impl;

import com.codigo.persistencia.aggregates.request.RequestEstudiante;
import com.codigo.persistencia.entity.CursoEntity;
import com.codigo.persistencia.entity.EstudianteEntity;
import com.codigo.persistencia.repository.CursoRepository;
import com.codigo.persistencia.repository.EstudianteRepository;
import com.codigo.persistencia.service.EstudianteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository, CursoRepository cursoRepository) {
        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    public EstudianteEntity guardarEstudiante(RequestEstudiante estudiante) {
        EstudianteEntity estudianteEntity = new EstudianteEntity();
        estudianteEntity.setNombre(estudiante.getNombre());
        Set<CursoEntity> cursos = cargarCursosPorIds(estudiante.getCursos());
        estudianteEntity.setCursos(cursos);
        //estudianteEntity.setCursos(cargarCursosPorIds(estudiante.getCursos()));
        return estudianteRepository.save(estudianteEntity);
    }

    private Set<CursoEntity> cargarCursosPorIds(Set<Long> cursoIds){
        return cursoIds.stream()
                .map(this::buscarPorIdCursos)
                .collect(Collectors.toSet());
    }

    private CursoEntity buscarPorIdCursos(Long cursoId){
       return cursoRepository.findById(cursoId).orElseThrow(
                ()->new RuntimeException("Error Estudiante -> Curso no encontrado"));

    }

    @Override
    public EstudianteEntity obtenerEstudiantePorId(Long id) {
        return estudianteRepository.findById(id).orElseThrow(() -> new RuntimeException("Error estudiante no encontrado"));
    }

    @Override
    public List<EstudianteEntity> obtenerTodosLosEstudiantes() {
        return estudianteRepository.findAll();
    }

    @Override
    public EstudianteEntity actualizarEstudiante(Long id, RequestEstudiante estudiante) {
        //Recupero al Estudiante de BD
        EstudianteEntity estudianteExistente = obtenerEstudiantePorId(id);
        //Cambios los datos qeu sean necesarios:
        estudianteExistente.setNombre(estudiante.getNombre());
        //Manejar los Cursos
        Set<CursoEntity> cursos;
        cursos = cargarCursosPorIds(estudiante.getCursos());
        //Asigno los cursos a mi estudiante:
        estudianteExistente.setCursos(cursos);
        //guardo con todos los cambios
        return estudianteRepository.save(estudianteExistente);
    }

    @Override
    public void eliminarEstudiante(Long id) {

        EstudianteEntity estudianteEntity = obtenerEstudiantePorId(id);
        estudianteRepository.delete(estudianteEntity);

    }
}
