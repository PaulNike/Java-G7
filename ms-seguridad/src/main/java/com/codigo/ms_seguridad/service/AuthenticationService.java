package com.codigo.ms_seguridad.service;

import com.codigo.ms_seguridad.aggregates.request.SignUpRequest;
import com.codigo.ms_seguridad.entity.Usuario;

import java.util.List;

public interface AuthenticationService {

    //SIGNUP --> REGISTRARSE
    Usuario signUpUser(SignUpRequest signUpRequest);
    Usuario signUpAdmin(SignUpRequest signUpRequest);
    List<Usuario> todos();

}
