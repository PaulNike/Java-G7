package com.codigo.ms_seguridad.config;

import com.codigo.ms_seguridad.service.JwtService;
import com.codigo.ms_seguridad.service.UsuarioSerice;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioSerice usuarioSerice;
        @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
            final String tokenExtraidoHeader = request.getHeader("Authorization");
            final String tokenLimpio;
            final String userEmail;

            //Validar el encabezado de la solicitud, validamos el token
            if(!StringUtils.hasText(tokenExtraidoHeader)
               || !StringUtils.startsWithIgnoreCase(tokenExtraidoHeader, "Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }
            //Limpiamos el token de la palabra Bearer
            tokenLimpio = tokenExtraidoHeader.substring(7);
            //Extraemos el usuario(username) del token
            userEmail = jwtService.extractUsername(tokenLimpio);

            //Validamos si El usuario no es nulo Y no se encuentre autenticado
            if(Objects.nonNull(userEmail) &&
                    SecurityContextHolder.getContext().getAuthentication() == null){
                //Definiendo un contexto de Seguridad vacio (Empty)
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                //Recuperando los detalles del usuario desde base de datos
                UserDetails userDetails = usuarioSerice.userDetailsService().loadUserByUsername(userEmail);
                //validamos el token(Que no este expirado y que pertenezca al usuario)
                if (jwtService.validateToken(tokenLimpio, userDetails) &&
                        !jwtService.isRefreshToken(tokenLimpio)){
                    //Creamos un TOKEN DE AUTENTICACION a travez de UsernamePasswordAuthenticationToken (aqui requerimos
                    // de colocar los detalles del usuario,credenciales, roles/ permisos)
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                    //Estoy asignando los detalles de la solicitud osea del Request a mi Token de auth.
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //Asignando la autenticación a mi contextos creado anteriormente,
                    securityContext.setAuthentication(authenticationToken);
                    //Asigno mi contextoDeSeguridad al Holder de Seguridad.
                    SecurityContextHolder.setContext(securityContext);
                }
            }
            //TODO OK, CONTINUA CON LA EJECUCIÓN DELA SOLICITUD
            filterChain.doFilter(request,response);

    }
}
