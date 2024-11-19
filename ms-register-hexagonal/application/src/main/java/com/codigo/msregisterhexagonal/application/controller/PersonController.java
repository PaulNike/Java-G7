package com.codigo.msregisterhexagonal.application.controller;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDto;
import com.codigo.msregisterhexagonal.domain.ports.in.PersonServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonServiceIn personServiceIn;

    @PostMapping("/{dni}")
    public ResponseEntity<PersonDto> createPerson(
            @PathVariable("dni") String dni){
        return ResponseEntity
                .ok(personServiceIn.createPersonIn(dni));
    }

}
