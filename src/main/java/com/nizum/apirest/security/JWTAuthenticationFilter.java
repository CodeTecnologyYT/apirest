package com.nizum.apirest.security;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor
@Log4j2
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Value(value = "${jwt.secret.key}")
    public String TOKEN_SECRET;

    @Value(value = "${security.token.bearer.prefix}")
    public String TOKEN_BEARER_PREFIX;

    @Value(value = "${security.header.authorization.key}")
    public String HEADER_AUTHORIZATION_KEY;

    @Autowired
    private JWTProvider jwtUtils;

    @Autowired
    private CustomUserSecurityService userSecurityService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String numeroDocumento;
            UserDetails userDetails;
            UsernamePasswordAuthenticationToken authentication;

            /* Obteniendo el token de los headers */
            String token = parseJwt(request);

            /* Verificando que el token no sea nulo y que sea válido */
            if (token != null && jwtUtils.validateToken(token)) {
                numeroDocumento = jwtUtils.getEmailFromToken(token); /* Obteniendo el correo desde el token */
                userDetails = userSecurityService.loadUserByUsername(numeroDocumento); /* Cargando el usuario por correo en el servicio personalizado */
                authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                /* Estableciendo el contexto de seguridad */
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            log.error("Ha ocurrido un error a la hora de filtrar el token");
            e.printStackTrace();
        }

        /* Añadiendo el filtro al conjunto de filtros */
        filterChain.doFilter(request, response);
    }

    /**
     * @param request Sirve para filtrar el token por los headers
     * @return Retorna el token
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth;
        String tokenIsNull = null;

        try {
            /* Obteniendo el header de autorización */
            headerAuth = request.getHeader(HEADER_AUTHORIZATION_KEY);

            /* Verificando que el token del header no sea nulo y comienza con el prefijo de token bearer */
            if (headerAuth != null && headerAuth.startsWith(TOKEN_BEARER_PREFIX + " ")) {
                return headerAuth.substring(7); /* Obteniendo el token */
            }

            return tokenIsNull;
        } catch (NullPointerException e) {
            log.warn("No existe un token en los headers");
            return tokenIsNull;
        }
    }

}
