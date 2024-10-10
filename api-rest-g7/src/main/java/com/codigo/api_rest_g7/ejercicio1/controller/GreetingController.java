package com.codigo.api_rest_g7.ejercicio1.controller;

import com.codigo.api_rest_g7.ejercicio1.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greet")
public class GreetingController {
/*
    private final GreetingService greetingService;
    public GreetingController( GreetingService greetingService) {
        this.greetingService = greetingService;
    }*/

    //Primera forma de hacer el saludo
    @Autowired
    @Qualifier("greetingServiceImpl")
    private GreetingService greetingService;

    //SegundaForma forma de hacer el saludo
    private final GreetingService greetingService2;

    public GreetingController(@Qualifier("greetingServiceImpl2") GreetingService greetingService2) {
        this.greetingService2 = greetingService2;
    }


    @GetMapping("/gt1")
    public String greet(){
        return greetingService.greet();
    }
    @GetMapping("/gt2")
    public String greet2(){
        return greetingService2.greet();
    }


}
