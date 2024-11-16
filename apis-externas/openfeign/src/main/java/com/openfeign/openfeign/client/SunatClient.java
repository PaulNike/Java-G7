package com.openfeign.openfeign.client;

import com.openfeign.openfeign.aggregates.constants.Constants;
import com.openfeign.openfeign.aggregates.response.ResponseSunat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "apis-client", url = Constants.BASE_URL)
public interface SunatClient {
    @GetMapping("/v2/sunat/ruc/full")
    ResponseSunat getInfoSunat(@RequestParam("numero") String numero,
                               @RequestHeader("Authorization") String token);

    @GetMapping("/v2/reniec/dni")
    ResponseSunat getInfoReniec(@RequestParam("numero") String numero,
                               @RequestHeader("Authorization") String token);
}
