package com.codigo.msregisterhexagonal.domain.ports.out;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDto;

public interface PersonServiceOut {
    PersonDto createPersonOut(String dni);
}
