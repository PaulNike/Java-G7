package com.codigo.ms_registros.client;

import com.codigo.ms_registros.aggregates.response.ResponseReniec;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "client-reniec", url = "https://api.apis.net.pe/v2/reniec/")
public interface ClientReniec {

    @GetMapping("/dni")
    ResponseReniec getPersonaReniec(@RequestParam("numero") String numero,
                                    @RequestHeader("Authorization") String authorization);
}
