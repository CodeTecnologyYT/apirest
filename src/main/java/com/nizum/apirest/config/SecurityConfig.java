package com.nizum.apirest.config;

import com.nizum.apirest.security.JWTAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* Modificamos para activar los CORS y inhabilitar el CSRF */
        http.cors().and().csrf().disable();

        /* Modificamos el sessionManager para que no genere cookies */
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        /* Configuracion en caso se produzca al acceder un endpoint no autorizado */
        http.exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    "No tiene Autorizacion"
                            );
                        }
                )
                .and();

        /* Control de Enpoints */
        http.authorizeRequests()
                /* Endpoints públicos*/
                .antMatchers("/", "/v1/users").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                /* Endpoints privados */
                .anyRequest().authenticated()
                .and();

        /* Desactivamos el frameOption por default */
        http.headers().frameOptions().disable()
                .and();

        /* Agregamos el filtro del json web token */
        http.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
