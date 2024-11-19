package com.codigo.msregisterhexagonal.domain.ports.in;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDto;

public interface PersonServiceIn {
    PersonDto createPersonIn(String dni);
}
