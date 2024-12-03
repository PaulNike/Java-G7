package com.codigo.ms_seguridad.controller;

import com.codigo.ms_seguridad.aggregates.request.SignInRefreshToken;
import com.codigo.ms_seguridad.aggregates.request.SignInRequest;
import com.codigo.ms_seguridad.aggregates.request.SignUpRequest;
import com.codigo.ms_seguridad.aggregates.response.SignInResponse;
import com.codigo.ms_seguridad.entity.Usuario;
import com.codigo.ms_seguridad.service.AuthenticationService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("api/authentication/v1/")
@RequiredArgsConstructor
@RefreshScope
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Value("${dato.propiedad}")
    private String datoPropiedad;
    @PostMapping("/signupuser")
    public ResponseEntity<Usuario> signUpUser(
            @RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService
                .signUpUser(signUpRequest));
    }
    @PostMapping("/signupadmin")
    public ResponseEntity<Usuario> signUpAdmin(
            @RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService
                .signUpAdmin(signUpRequest));
    }

    //OBTENER UN ACCESS TOKEN POR MEDIO DE LOGIN
    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(
            @RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService
                .signIn(signInRequest));
    }
    //OBTENER UN ACCESS TOKEN PO MEDIO DE REFRESHTOKEN
    @PostMapping("/refreshToken")
    public ResponseEntity<SignInResponse> signInRefresh(
            @RequestBody SignInRefreshToken signInRefreshToken){
        return ResponseEntity.ok(authenticationService
                .getTokenByRefresh(signInRefreshToken));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> getALl(){
        return ResponseEntity.ok(authenticationService.todos());
    }

    @GetMapping("/clave")
    public ResponseEntity<String> getClaveFirma(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String dato = Base64.getEncoder().encodeToString(key.getEncoded());
        return ResponseEntity.ok(dato);
    }

    @GetMapping("/prueba")
    public ResponseEntity<String> getPrueba(){
        return ResponseEntity.ok(datoPropiedad);
    }
}
