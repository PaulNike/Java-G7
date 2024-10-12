package com.codigo.api_rest_g7.ejercicio7.controller;

import com.codigo.api_rest_g7.ejercicio7.service.CalculatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculate")
public class CalculatorController {
    private final CalculatorService calculatorServiceMulti;
    private final CalculatorService calculatorServiceAddition;

    public CalculatorController(@Qualifier("multiplicationServiceImpl") CalculatorService calculatorServiceMulti,
                                @Qualifier("additionServiceImpl") CalculatorService calculatorServiceAddition) {
        this.calculatorServiceMulti = calculatorServiceMulti;
        this.calculatorServiceAddition = calculatorServiceAddition;
    }


    @GetMapping("/addition")
    public String addition(@RequestParam double a, @RequestParam double b){
        return "Resultado: " + calculatorServiceAddition.calculate(a,b);
    }
    @GetMapping("/multi")
    public String multi(@RequestParam double a, @RequestParam double b){
        return "Resultado: " + calculatorServiceMulti.calculate(a,b);
    }
}
