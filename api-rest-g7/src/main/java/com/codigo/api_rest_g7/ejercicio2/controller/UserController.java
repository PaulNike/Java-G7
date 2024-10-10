package com.codigo.api_rest_g7.ejercicio2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/name/{name}/age/{age}")
    public String greetUser(@PathVariable String name, @PathVariable int age){
        //  /user/Paul/age/27
       return "Hola usuario: "+ name + " Edad: "+ age;
    }

}
