package com.codigo.ms_seguridad.service;

import com.codigo.ms_seguridad.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioSerice {
    UserDetailsService userDetailsService();

    List<Usuario> getInfoUser();
}
