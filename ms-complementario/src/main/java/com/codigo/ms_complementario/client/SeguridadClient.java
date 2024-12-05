package com.codigo.ms_complementario.client;

import com.codigo.ms_complementario.response.UsuarioResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "ms-seguridad")
public interface SeguridadClient {

    @GetMapping("/apis/codigo/api/user/v1/all")
    List<UsuarioResponseClient> getInfoUsers(
            @RequestHeader("Authorization") String auth);
}
