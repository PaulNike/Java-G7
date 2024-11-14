package com.codigo.unit_testing.controller;


import com.codigo.unit_testing.aggregates.request.EmpresaRequest;
import com.codigo.unit_testing.aggregates.response.BaseResponse;
import com.codigo.unit_testing.entity.Empresa;
import com.codigo.unit_testing.service.EmpresaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/empresa/v1/")
@AllArgsConstructor
public class EmpresaController {


    private final EmpresaService service;


    @PostMapping
    public ResponseEntity<BaseResponse<Empresa>> registrar(@RequestBody EmpresaRequest empresaRequest){
        return service.crear(empresaRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Empresa>>obtenerUno(@PathVariable Long id){
        return service.obtenerEmpresa(id);
    }

    @GetMapping()
    public ResponseEntity<BaseResponse<Empresa>>obtenerTodos(){
        return service.obtenerTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Empresa>>actualizar(@PathVariable Long id, @RequestBody EmpresaRequest request){
        return service.actualizar(id,request);

    }
}
