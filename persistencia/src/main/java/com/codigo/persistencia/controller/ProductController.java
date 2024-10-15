package com.codigo.persistencia.controller;

import ch.qos.logback.core.net.server.Client;
import com.codigo.persistencia.entity.ProductEntity;
import com.codigo.persistencia.repository.ProductRepository;
import com.codigo.persistencia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<ProductEntity> crearProducto(
            @RequestBody ProductEntity productEntity){
        productEntity.setEstado(1);
        productEntity.setCreated_by("ADMIN");
        productEntity.setCreated_date(new Timestamp(System.currentTimeMillis()));
        //ProductEntity nuevoProducto = productService.guardarProducto(productEntity);
        ProductEntity nuevoProducto = productRepository.save(productEntity);

        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> obtenerProducto(@PathVariable Long id){
        ProductEntity producto = productService.obtenerProductoPorId(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @GetMapping
    public List<ProductEntity> obtenerTodosLosProductos(){
        return productService.obtenerTodosLosProductos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> actualizarProducto(
            @PathVariable Long id, @RequestBody ProductEntity productEntity){
        ProductEntity productoActualizado = productService.actualizarProducto(id,productEntity);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        productService.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
