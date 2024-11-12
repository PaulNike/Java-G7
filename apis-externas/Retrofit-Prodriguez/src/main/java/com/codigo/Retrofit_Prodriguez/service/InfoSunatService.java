package com.codigo.Retrofit_Prodriguez.service;

import com.codigo.Retrofit_Prodriguez.aggregates.response.ResponseSunat;

import java.io.IOException;

public interface InfoSunatService {
    ResponseSunat getInfoSunat(String ruc) throws IOException;
}
