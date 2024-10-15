package com.codigo.persistencia.controller;

import com.codigo.persistencia.entity.ClienteEntity;
import com.codigo.persistencia.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteEntity> crearCliente(@RequestBody ClienteEntity cliente) {
        ClienteEntity nuevoCliente = clienteService.guardarCliente(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> obtenerCliente(@PathVariable Long id) {
        ClienteEntity cliente = clienteService.obtenerClientePorId(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping
    public List<ClienteEntity> obtenerTodosLosClientes() {
        return clienteService.obtenerTodosLosClientes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteEntity> actualizarCliente(@PathVariable Long id, @RequestBody ClienteEntity cliente) {
        ClienteEntity clienteActualizado = clienteService.actualizarCliente(id, cliente);
        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
