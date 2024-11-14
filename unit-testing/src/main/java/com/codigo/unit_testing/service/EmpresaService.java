package com.codigo.unit_testing.service;

import com.codigo.unit_testing.aggregates.request.EmpresaRequest;
import com.codigo.unit_testing.aggregates.response.BaseResponse;
import com.codigo.unit_testing.entity.Empresa;
import org.springframework.http.ResponseEntity;


public interface EmpresaService {

    ResponseEntity<BaseResponse<Empresa>> crear(EmpresaRequest request);
    ResponseEntity<BaseResponse<Empresa>>obtenerEmpresa(Long id);
    ResponseEntity<BaseResponse<Empresa>> obtenerTodos();
    ResponseEntity<BaseResponse<Empresa>> actualizar(Long id, EmpresaRequest request);
    ResponseEntity<BaseResponse<Empresa>> delete(Long id);
    ResponseEntity<BaseResponse<Empresa>> obtenerEmpresaXNumDoc(String numDocu);

}
