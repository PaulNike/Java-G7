package com.codigo.persistencia.controller;

import com.codigo.persistencia.aggregates.request.RequestCurso;
import com.codigo.persistencia.entity.CursoEntity;
import com.codigo.persistencia.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }
    @PostMapping
    public ResponseEntity<CursoEntity> crearCurso(@RequestBody RequestCurso curso) {
        CursoEntity nuevoCurso = cursoService.guardarCurso(curso);
        return new ResponseEntity<>(nuevoCurso, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CursoEntity> obtenerTodosLosCursos() {
        return cursoService.obtenerTodosLosCursos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoEntity> actualizarCurso(@PathVariable Long id, @RequestBody CursoEntity curso) {
        CursoEntity cursoActualizado = cursoService.actualizarCurso(id, curso);
        return new ResponseEntity<>(cursoActualizado, HttpStatus.OK);
    }
}

