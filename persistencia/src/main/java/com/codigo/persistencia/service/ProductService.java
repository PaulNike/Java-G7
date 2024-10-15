package com.codigo.persistencia.service;

import com.codigo.persistencia.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductEntity guardarProducto(ProductEntity productEntity);
    ProductEntity obtenerProductoPorId(Long id);
    List<ProductEntity> obtenerTodosLosProductos();
    ProductEntity actualizarProducto(Long id, ProductEntity productEntity);
    void eliminarProducto(Long id);

}
