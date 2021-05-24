package com.nizum.apirest.security;

import com.nizum.apirest.model.entities.UserEntity;
import com.nizum.apirest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserSecurityService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user;
        Optional<UserEntity> result = userRepository.findByEmail(username); // Buscando el usuario por correo

        /* Verificando si el usuario existe */
        if (result.isPresent()) {
            user = result.get(); // Obteniendo el usuario
            return CustomUserSecurity.build(user);
        } else {
            throw new UsernameNotFoundException("Usuario con email " + username + " no encontrado");
        }
    }
}
