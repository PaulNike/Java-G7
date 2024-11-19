package com.codigo.msregisterhexagonal.domain.impl;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDto;
import com.codigo.msregisterhexagonal.domain.ports.in.PersonServiceIn;
import com.codigo.msregisterhexagonal.domain.ports.out.PersonServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonServiceIn {

    private final PersonServiceOut personServiceOut;
    @Override
    public PersonDto createPersonIn(String dni) {
        return personServiceOut.createPersonOut(dni);
    }
}
