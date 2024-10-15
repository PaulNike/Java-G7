package com.codigo.persistencia.service;

import com.codigo.persistencia.entity.ClienteEntity;

import java.util.List;

public interface ClienteService {
    ClienteEntity guardarCliente(ClienteEntity cliente);
    ClienteEntity obtenerClientePorId(Long id);
    List<ClienteEntity> obtenerTodosLosClientes();
    ClienteEntity actualizarCliente(Long id, ClienteEntity cliente);
    void eliminarCliente(Long id);
}
