package com.openfeign.openfeign.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openfeign.openfeign.aggregates.response.ResponseSunat;

public interface InfoSunatService {
    ResponseSunat getInfoSunat(String ruc) throws JsonProcessingException;
}
