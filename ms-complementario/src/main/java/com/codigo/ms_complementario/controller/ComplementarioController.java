package com.codigo.ms_complementario.controller;

import com.codigo.ms_complementario.client.SeguridadClient;
import com.codigo.ms_complementario.response.UsuarioResponseClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/complementario/v1")
@RequiredArgsConstructor
public class ComplementarioController {
    private final SeguridadClient seguridadClient;

    @GetMapping("/info-users")
    public ResponseEntity<List<UsuarioResponseClient>> getInfoUsers(
            @RequestHeader("Authorization") String auth){
        return ResponseEntity.ok(seguridadClient.getInfoUsers(auth));

    }
}
