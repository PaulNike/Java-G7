package com.codigo.ms_seguridad.service;

import com.codigo.ms_seguridad.aggregates.request.SignInRefreshToken;
import com.codigo.ms_seguridad.aggregates.request.SignInRequest;
import com.codigo.ms_seguridad.aggregates.request.SignUpRequest;
import com.codigo.ms_seguridad.aggregates.response.SignInResponse;
import com.codigo.ms_seguridad.entity.Usuario;

import java.util.List;

public interface AuthenticationService {

    //SIGNUP --> REGISTRARSE
    Usuario signUpUser(SignUpRequest signUpRequest);
    Usuario signUpAdmin(SignUpRequest signUpRequest);
    List<Usuario> todos();

    //METODOS DE AUTENTICACION
    SignInResponse signIn(SignInRequest signInRequest);
    // OBTENER NEUVO TOKEN DESDE UN REFRESH TOKEN
    SignInResponse getTokenByRefresh(SignInRefreshToken signInRefreshToken);;

    //Validar token
    boolean validateToken(String token);

}
