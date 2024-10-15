package com.codigo.persistencia.service.impl;

import com.codigo.persistencia.entity.ClienteEntity;
import com.codigo.persistencia.entity.PedidoEntity;
import com.codigo.persistencia.repository.ClienteRepository;
import com.codigo.persistencia.repository.PedidoRepository;
import com.codigo.persistencia.service.PedidoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public PedidoEntity guardarPedido(Long clienteId, PedidoEntity pedido) {
        ClienteEntity clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NoSuchElementException("Error Cliente no existe"));
        pedido.setCliente(clienteExistente);
        return pedidoRepository.save(pedido);
    }

    @Override
    public PedidoEntity obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("Pedido no encontrado"));
    }

    @Override
    public List<PedidoEntity> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public PedidoEntity actualizarPedido(Long id, Long idCliente, PedidoEntity pedido) {
        PedidoEntity pedidoExistente = obtenerPedidoPorId(id);
        //ClienteEntity clienteExistente = clienteRepository.findById(idCliente).orElseThrow(() -> new RuntimeException("Erorr cliente a relacionar no existe"));
        pedidoExistente.setDescripcion(pedido.getDescripcion());
        //pedidoExistente.setCliente(clienteExistente);
        return pedidoRepository.save(pedidoExistente);
    }

    @Override
    public void eliminarPedido(Long id) {
        PedidoEntity pedidoExistente = obtenerPedidoPorId(id);
        pedidoRepository.delete(pedidoExistente);
    }
}
