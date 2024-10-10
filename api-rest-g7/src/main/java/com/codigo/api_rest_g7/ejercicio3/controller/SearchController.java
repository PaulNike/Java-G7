package com.codigo.api_rest_g7.ejercicio3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @GetMapping("/products")
    public String searchProducts(@RequestParam String clave,
                                 @RequestParam(required = false,
                                         defaultValue = "OTROS") String category){
        /*String dato = "";
        if(category != null){
            dato = category;
        }else{
            dato="";
        }*/
        return "Buscando los productos con la clave: " + clave +
                (category != null ? " con Categoria: " + category:"");
    }
}
