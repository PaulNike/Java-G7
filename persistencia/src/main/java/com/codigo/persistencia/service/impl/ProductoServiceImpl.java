package com.codigo.persistencia.service.impl;

import com.codigo.persistencia.aggregates.request.RequestProducto;
import com.codigo.persistencia.entity.CategoriaEntity;
import com.codigo.persistencia.entity.ProductoCategoriaEntity;
import com.codigo.persistencia.entity.ProductoEntity;
import com.codigo.persistencia.repository.CategoriaRepository;
import com.codigo.persistencia.repository.ProductoRepository;
import com.codigo.persistencia.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public ProductoEntity guardarProducto(RequestProducto requestProducto) {

        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setNombreProducto(requestProducto.getNombre());

        Set<ProductoCategoriaEntity> productoCategoriaEntity =
                requestProducto.getCategorias().stream().map( categoriaDto -> {
                    CategoriaEntity categoriaEntity = categoriaRepository.findById(categoriaDto.getCategoriaId())
                            .orElseThrow(() -> new RuntimeException("Error con la categoria"));
                    ProductoCategoriaEntity productoCategoria = new ProductoCategoriaEntity();
                    productoCategoria.setProducto(productoEntity);
                    productoCategoria.setCategoria(categoriaEntity);
                    productoCategoria.setEstado(categoriaDto.getEstado());
                    productoCategoria.setStock(categoriaDto.getStock());
                    return productoCategoria;
                })
                        .collect(Collectors.toSet());

        productoEntity.setProductoCategoria(productoCategoriaEntity);
        return productoRepository.save(productoEntity);
    }
}
