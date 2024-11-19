package com.codigo.msregisterhexagonal.infraestructure.mapper;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDto;
import com.codigo.msregisterhexagonal.infraestructure.entity.PersonEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PersonMapper {
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public PersonDto mapToDto(PersonEntity personEntity){
        return MODEL_MAPPER.map(personEntity, PersonDto.class);
    }
    public PersonEntity mapToEntity(PersonDto personDto){
        return MODEL_MAPPER.map(personDto, PersonEntity.class);
    }

}
