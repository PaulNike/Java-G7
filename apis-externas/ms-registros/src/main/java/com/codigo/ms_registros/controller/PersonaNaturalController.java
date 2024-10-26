package com.codigo.ms_registros.controller;

import com.codigo.ms_registros.entity.PersonaNaturalEntity;
import com.codigo.ms_registros.service.PersonaNaturalService;
import jakarta.annotation.Nonnull;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/personaNatural")
public class PersonaNaturalController {

    private final PersonaNaturalService personaNaturalService;
    public PersonaNaturalController(PersonaNaturalService personaNaturalService) {
        this.personaNaturalService = personaNaturalService;
    }

    @PostMapping
    public ResponseEntity<PersonaNaturalEntity> guardarPersona(
            @RequestParam("dni") String dni) throws IOException {
        PersonaNaturalEntity personaNatural = personaNaturalService.guardar(dni);
        return new ResponseEntity<>(personaNatural, HttpStatus.CREATED);
    }
}
