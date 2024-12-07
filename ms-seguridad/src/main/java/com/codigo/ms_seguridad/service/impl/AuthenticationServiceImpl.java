package com.codigo.ms_seguridad.service.impl;

import com.codigo.ms_seguridad.aggregates.constants.Constants;
import com.codigo.ms_seguridad.aggregates.request.SignInRefreshToken;
import com.codigo.ms_seguridad.aggregates.request.SignInRequest;
import com.codigo.ms_seguridad.aggregates.request.SignUpRequest;
import com.codigo.ms_seguridad.aggregates.response.SignInResponse;
import com.codigo.ms_seguridad.entity.Rol;
import com.codigo.ms_seguridad.entity.Role;
import com.codigo.ms_seguridad.entity.Usuario;
import com.codigo.ms_seguridad.repository.RolRepository;
import com.codigo.ms_seguridad.repository.UsuarioRepository;
import com.codigo.ms_seguridad.service.AuthenticationService;
import com.codigo.ms_seguridad.service.JwtService;
import com.codigo.ms_seguridad.service.UsuarioSerice;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioSerice usuarioSerice;


    @Override
    public Usuario signUpUser(SignUpRequest signUpRequest) {
        Usuario usuario = getUsuarioEntity(signUpRequest);
        usuario.setRoles(Collections.singleton(getRoles(Role.USER)));
        return usuarioRepository.save(usuario);
    }

    private Usuario getUsuarioEntity(SignUpRequest signUpRequest){
        return Usuario.builder()
                .nombres(signUpRequest.getNombres())
                .apellidos(signUpRequest.getApellidos())
                .email(signUpRequest.getEmail())
                .password(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()))
                .tipoDoc(signUpRequest.getTipoDoc())
                .numDoc(signUpRequest.getNumDoc())
                .isAccountNonExpired(Constants.STATUS_ACTIVE)
                .isAccountNonLocked(Constants.STATUS_ACTIVE)
                .isCredentialsNonExpired(Constants.STATUS_ACTIVE)
                .isEnabled(Constants.STATUS_ACTIVE)
                .build();
    }
    @Override
    public Usuario signUpAdmin(SignUpRequest signUpRequest) {
        Usuario usuario = getUsuarioEntity(signUpRequest);
        Set<Rol> roles = new HashSet<>();
        roles.add(getRoles(Role.USER));
        roles.add(getRoles(Role.ADMIN));
        usuario.setRoles(roles);
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> todos() {
        return usuarioRepository.findAll();
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),signInRequest.getPassword()
        ));
        var user = usuarioRepository.findByEmail(signInRequest.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("Error Usuario no encontrado en base de datos"));
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        return SignInResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public SignInResponse getTokenByRefresh(SignInRefreshToken signInRefreshToken) {
        //VALIDAMOS QUE SEA UN REFRESH TOKEN
        if (!jwtService.isRefreshToken(signInRefreshToken.getRefreshToken())){
            throw new RuntimeException("ERROR EL TOKEN INGRESADO NO ES: TYPE : REFRESH");
        }
        //EXTRAEMOS EL SUBJECT DEL TOKEN
        String userEmail = jwtService.extractUsername(signInRefreshToken.getRefreshToken());

        //BUSCAMOS AL SUJETO EN LA BASE DE DATOS:
        Usuario usuario = usuarioRepository.findByEmail(userEmail).orElseThrow(() ->
                new UsernameNotFoundException("No se encontro al usuario"));

        //VALIDAMOS QUE EL REFRESH LE PERTENEZCA AL USUARIO
        if(!jwtService.validateToken(signInRefreshToken.getRefreshToken(), usuario)){
            throw  new RuntimeException("Error el token no le pertenece al usuario");
        }
        //Generamos el nuevo token access
        String newToken = jwtService.generateToken(usuario);
        //si gustamos podemos generar un nuevo refresh token,
        // caso contrario devolvemos el mismo con el que generamos.

        return SignInResponse.builder()
                .token(newToken)
                .refreshToken(signInRefreshToken.getRefreshToken())
                .build();
    }

    @Override
    public boolean validateToken(String token) {
        final String jwt;
        final String userEmail;

        if(Objects.nonNull(token) && !token.trim().isEmpty()){
           jwt = token.substring(7); //Bearer
           userEmail = jwtService.extractUsername(jwt);
           if(Objects.nonNull(userEmail) && !userEmail.trim().isEmpty()){
               UserDetails userDetails = usuarioSerice
                       .userDetailsService()
                       .loadUserByUsername(userEmail);
               return jwtService.validateToken(jwt, userDetails);
           }
        }
        return false;
    }

    private Rol getRoles(Role rolBuscado){
        return rolRepository.findByNombreRol(rolBuscado.name())
                .orElseThrow(
                        () -> new RuntimeException(
                                "ERROR EL ROL NO EXISTE :" + rolBuscado.name()));
    }
}
