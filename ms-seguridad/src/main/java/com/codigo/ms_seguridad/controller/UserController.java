package com.codigo.ms_seguridad.controller;

import com.codigo.ms_seguridad.entity.Usuario;
import com.codigo.ms_seguridad.service.UsuarioSerice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/v1/")
@RequiredArgsConstructor
public class UserController {

    private final UsuarioSerice usuarioSerice;
    @GetMapping("/saludo")
    public ResponseEntity<String> getSaludo(){
        return ResponseEntity.ok("Hola Userrr!!");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> getInfo(){
        return ResponseEntity.ok(usuarioSerice.getInfoUser());
    }
}
