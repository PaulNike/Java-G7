package com.codigo.persistencia.service.impl;

import com.codigo.persistencia.aggregates.request.RequestCurso;
import com.codigo.persistencia.entity.CursoEntity;
import com.codigo.persistencia.repository.CursoRepository;
import com.codigo.persistencia.service.CursoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    public CursoServiceImpl(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public CursoEntity guardarCurso(RequestCurso request) {
        CursoEntity curso = new CursoEntity();
        curso.setDescripcion(request.getDescripcion());
        return cursoRepository.save(curso);
    }

    @Override
    public CursoEntity obtenerCursoPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no Encontrado"));
    }

    @Override
    public List<CursoEntity> obtenerTodosLosCursos() {
        return cursoRepository.findAll();
    }

    @Override
    public CursoEntity actualizarCurso(Long id, CursoEntity curso) {
        CursoEntity cursoExiste = obtenerCursoPorId(id);
        cursoExiste.setDescripcion(curso.getDescripcion());
        return cursoRepository.save(cursoExiste);
    }

    @Override
    public void eliminarCurso(Long id) {
        CursoEntity cursoExiste = obtenerCursoPorId(id);
        cursoRepository.delete(cursoExiste);
    }
}
