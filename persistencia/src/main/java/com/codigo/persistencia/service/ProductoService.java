package com.codigo.persistencia.service;

import com.codigo.persistencia.aggregates.request.RequestProducto;
import com.codigo.persistencia.entity.ProductEntity;
import com.codigo.persistencia.entity.ProductoEntity;

public interface ProductoService {

    ProductoEntity guardarProducto(RequestProducto requestProducto);
}
