package com.codigo.msregisterhexagonal.infraestructure.adapters;

import com.codigo.msregisterhexagonal.domain.aggregates.constants.Constants;
import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDto;
import com.codigo.msregisterhexagonal.domain.aggregates.response.ResponseReniec;
import com.codigo.msregisterhexagonal.domain.ports.out.PersonServiceOut;
import com.codigo.msregisterhexagonal.infraestructure.dao.PersonRepository;
import com.codigo.msregisterhexagonal.infraestructure.entity.PersonEntity;
import com.codigo.msregisterhexagonal.infraestructure.mapper.PersonMapper;
import com.codigo.msregisterhexagonal.infraestructure.rest.ReniecClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class PersonAdapter implements PersonServiceOut {

    private final PersonRepository personRepository;
    private final ReniecClient reniecClient;
    private final PersonMapper personMapper;

    @Value("${token.api}")
    private String token;
    @Override
    public PersonDto createPersonOut(String dni) {
        PersonEntity person = getEntity(dni);
        PersonEntity dato = personRepository.save(person);
        PersonDto dto = personMapper.mapToDto(dato);
        log.info("dato del dto: " + dto.toString() );
        return dto;
    }

    private PersonEntity getEntity(String dni){
        ResponseReniec responseReniec = execute(dni);
        if(Objects.nonNull(responseReniec)){
            return PersonEntity.builder()
                    .nombres(responseReniec.getNombres())
                    .apellidos(responseReniec.getApellidoPaterno() + " " +
                            responseReniec.getApellidoMaterno())
                    .numDoc(responseReniec.getNumeroDocumento())
                    .tipoDoc(responseReniec.getTipoDocumento())
                    .estado(Constants.ACTIVE)
                    .usua_crea(Constants.USU_ADMIN)
                    .date_crea(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return null;
    }

    private ResponseReniec execute(String dni){
        String head = "Bearer "+ token;
        return reniecClient.getInfoReniec(dni,head);
    }
}
