package com.codigo.unit_testing.service.impl;

import com.codigo.unit_testing.aggregates.constants.Constants;
import com.codigo.unit_testing.aggregates.request.EmpresaRequest;
import com.codigo.unit_testing.aggregates.response.BaseResponse;
import com.codigo.unit_testing.dao.EmpresaRepository;
import com.codigo.unit_testing.entity.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class EmpresaServiceImplTest {
    @Mock
    private EmpresaRepository empresaRepository;
    @InjectMocks
    private EmpresaServiceImpl empresaServiceImpl;

    //Variables Generales: La sque vamos a poder usar en toda nuestra clase
    private Empresa empresa;
    private EmpresaRequest empresaRequest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        empresa = new Empresa();
        empresaRequest = new EmpresaRequest();
        empresaRequest.setNumeroDocumento("");
    }

    @Test
    void  testCrearEmpresaExiste(){
        //ARRANGE
            //Configurar el comportamiento del mock
            when(empresaRepository.existsByNumeroDocumento(anyString()))
                    .thenReturn(true);
        //ACT -> EJECUTAR NUESTRO METODO ESPECIFICO
        ResponseEntity<BaseResponse<Empresa>> response =
                empresaServiceImpl.crear(empresaRequest);

        //ASSERT
        assertEquals(Constants.CODE_EXIST, response.getBody().getCode());
        //assertEquals(4001, response.getBody().getCode());
        //assertEquals(Constants.MSJ_EXIST, response.getBody().getMessage());
        //assertTrue(response.getBody().getObjeto().isEmpty());
    }

    @Test
    void testCrearEmpresaNueva(){
        //Configurar el comportamiento del mock
        when(empresaRepository.existsByNumeroDocumento(anyString()))
                .thenReturn(false);
        when(empresaRepository.save(any())).thenReturn(empresa);

        //ACT -> EJECUTAR NUESTRO METODO ESPECIFICO
        ResponseEntity<BaseResponse<Empresa>> response = empresaServiceImpl.crear(empresaRequest);

        //ASSERT
        assertEquals(Constants.CODE_OK, response.getBody().getCode());
        assertEquals(Constants.MSJ_OK, response.getBody().getMessage());
        assertFalse(response.getBody().getObjeto().isEmpty());
        assertSame(empresa,response.getBody().getObjeto().get());

    }

    @Test
    void testActualizarEmpresaSuccess(){

        Long id = 1L;


        when(empresaRepository.existsById(id)).thenReturn(true);
        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresa));
        when(empresaRepository.save(any())).thenReturn(empresa);

        ResponseEntity<BaseResponse<Empresa>> response =
                empresaServiceImpl.actualizar(id,empresaRequest);

        assertNotNull(response);
        assertEquals(2001, response.getBody().getCode());
        assertEquals(Constants.MSJ_OK, response.getBody().getMessage());
        assertFalse(response.getBody().getObjeto().isEmpty());
        assertTrue(response.getBody().getObjeto().isPresent());
        assertSame(empresa, response.getBody().getObjeto().get());

    }

    @Test
    void testActualizarEmpresaUnsuccess(){
        Long id = 1L;
        when(empresaRepository.existsById(id))
                .thenReturn(false);

        ResponseEntity<BaseResponse<Empresa>> response =
                empresaServiceImpl.actualizar(id, empresaRequest);

        assertNotNull(response);
        assertEquals(Constants.CODE_EMPRESA_NO_EXIST, response.getBody().getCode());

    }

}