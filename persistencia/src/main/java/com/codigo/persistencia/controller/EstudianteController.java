package com.codigo.persistencia.controller;

import com.codigo.persistencia.aggregates.request.RequestEstudiante;
import com.codigo.persistencia.entity.EstudianteEntity;
import com.codigo.persistencia.service.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @PostMapping
    public ResponseEntity<EstudianteEntity> crearEstudiante(@RequestBody RequestEstudiante estudiante) {
        EstudianteEntity nuevoEstudiante = estudianteService.guardarEstudiante(estudiante);
        return new ResponseEntity<>(nuevoEstudiante, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteEntity> obtenerEstudiante(@PathVariable Long id) {
        EstudianteEntity estudiante = estudianteService.obtenerEstudiantePorId(id);
        return new ResponseEntity<>(estudiante, HttpStatus.OK);
    }

    @GetMapping
    public List<EstudianteEntity> obtenerTodosLosEstudiantes() {
        return estudianteService.obtenerTodosLosEstudiantes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteEntity> actualizarEstudiante(@PathVariable Long id, @RequestBody RequestEstudiante estudiante) {
        EstudianteEntity estudianteActualizado = estudianteService.actualizarEstudiante(id, estudiante);
        return new ResponseEntity<>(estudianteActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) {
        estudianteService.eliminarEstudiante(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
