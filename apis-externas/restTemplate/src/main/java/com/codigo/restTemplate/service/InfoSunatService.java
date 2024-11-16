package com.codigo.restTemplate.service;

import com.codigo.restTemplate.aggregates.response.ResponseSunat;

import java.io.IOException;

public interface InfoSunatService {

    ResponseSunat getInfoSunat(String ruc) throws IOException;

}
