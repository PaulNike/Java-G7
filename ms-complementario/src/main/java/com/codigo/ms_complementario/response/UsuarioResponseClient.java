package com.codigo.ms_complementario.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseClient {
    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String password;
    private String tipoDoc;
    private String numDoc;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;
}
