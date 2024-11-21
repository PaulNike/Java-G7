package com.codigo.ms_seguridad.controller;

import com.codigo.ms_seguridad.aggregates.request.SignUpRequest;
import com.codigo.ms_seguridad.entity.Usuario;
import com.codigo.ms_seguridad.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/authentication/v1/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

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

    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> getALl(){
        return ResponseEntity.ok(authenticationService.todos());
    }

}
