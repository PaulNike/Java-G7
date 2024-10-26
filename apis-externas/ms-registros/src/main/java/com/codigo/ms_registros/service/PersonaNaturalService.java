package com.codigo.ms_registros.service;

import com.codigo.ms_registros.entity.PersonaNaturalEntity;

import java.io.IOException;

public interface PersonaNaturalService {
    PersonaNaturalEntity guardar(String dni) throws IOException;
}
