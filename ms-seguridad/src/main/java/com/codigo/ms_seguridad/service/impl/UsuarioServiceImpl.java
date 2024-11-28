package com.codigo.ms_seguridad.service.impl;

import com.codigo.ms_seguridad.entity.Usuario;
import com.codigo.ms_seguridad.repository.UsuarioRepository;
import com.codigo.ms_seguridad.service.UsuarioSerice;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioSerice {

    private final UsuarioRepository usuarioRepository;
    @Override
    public UserDetailsService userDetailsService() {
      return new UserDetailsService() {
          @Override
          public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
              return usuarioRepository.findByEmail(username).orElseThrow(
                      ()-> new UsernameNotFoundException("USUARIO NO ENCONTRADO EN BASE DE DATOS"));
          }
      };
    }

    @Override
    public List<Usuario> getInfoUser() {
        return usuarioRepository.findAll();
    }
}
