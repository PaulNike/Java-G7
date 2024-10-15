package com.codigo.persistencia.service.impl;

import com.codigo.persistencia.entity.ClienteEntity;
import com.codigo.persistencia.entity.PedidoEntity;
import com.codigo.persistencia.repository.ClienteRepository;
import com.codigo.persistencia.repository.PedidoRepository;
import com.codigo.persistencia.service.ClienteService;
import com.codigo.persistencia.service.PedidoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final PedidoService pedidoService;

    public ClienteServiceImpl(ClienteRepository clienteRepository, PedidoRepository pedidoRepository, PedidoService pedidoService) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.pedidoService = pedidoService;
    }


    @Override
    public ClienteEntity guardarCliente(ClienteEntity cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public ClienteEntity obtenerClientePorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Cliente no Encontrado"));
    }

    @Override
    public List<ClienteEntity> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public ClienteEntity actualizarCliente(Long id, ClienteEntity clienteActual) {
        ClienteEntity clienteExistente = obtenerClientePorId(id);
        clienteExistente.setNombre(clienteActual.getNombre());
        clienteExistente.setDireccionEntity(clienteActual.getDireccionEntity());

        //Gestionando Pedidos
        //gestionarPedidos(clienteExistente, clienteActual.getPedidos());
        clienteActual.setId(clienteExistente.getId());
        gestionarPedidos2(clienteActual.getPedidos());
        //clienteExistente.setPedidos(clienteActual.getPedidos());
        return clienteRepository.save(clienteExistente);
    }

    //CORRECTO
    private void gestionarPedidos(ClienteEntity clienteExistente, List<PedidoEntity> pedidosActualizados ){
        //extraer lso pedidos del cliente existente
        List<PedidoEntity> pedidosExistentes = clienteExistente.getPedidos();
        //Limpiamos la lista de pedidos para colocar los que vamos actualizar
        pedidosExistentes.clear();

        //Buscamos los nuevos pedidos
        for(PedidoEntity pedido : pedidosActualizados){
            if(pedido.getId() != null){
                PedidoEntity pedidoEncontrado = pedidoRepository.findById(pedido.getId())
                        .orElseThrow(() -> new NoSuchElementException("Error pedido no ubicad"));
                pedidoEncontrado.setDescripcion(pedido.getDescripcion());
                pedidosExistentes.add(pedidoEncontrado);
            }else {
                //Significa que no existe debe ser añadido
                pedido.setCliente(clienteExistente);
                pedidosExistentes.add(pedido);
            }
        }
    }

    //PECADOOOOOOOO
    private void gestionarPedidos2(List<PedidoEntity> pedidosActualizados){
        //Buscamos los nuevos pedidos
        for(PedidoEntity pedido : pedidosActualizados){
            if(pedido.getId() != null){
                    pedidoService.actualizarPedido(pedido.getId(),pedido.getCliente().getId(),pedido);
            }else {
                //Significa que no existe debe ser añadido
                pedidoService.guardarPedido(pedido.getCliente().getId(),pedido);
            }
        }

    }

    @Override
    public void eliminarCliente(Long id) {
        ClienteEntity clienteExistente = obtenerClientePorId(id);
        clienteRepository.delete(clienteExistente);
    }
}
