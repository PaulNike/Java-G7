package com.codigo.ms_seguridad.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/v1/")
public class AdminController {
    @GetMapping("/saludo")
    public ResponseEntity<String> getSaludo(){
        return ResponseEntity.ok("Hola Admin!!");
    }
}
