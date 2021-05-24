package com.nizum.apirest.service;

import com.nizum.apirest.constant.RolesEnum;
import com.nizum.apirest.constant.StatusEnum;
import com.nizum.apirest.model.entities.RolEntity;
import com.nizum.apirest.model.entities.UserEntity;
import com.nizum.apirest.model.request.UserRequest;
import com.nizum.apirest.model.response.UserResponse;
import com.nizum.apirest.repository.RolRepository;
import com.nizum.apirest.repository.UserRepository;
import com.nizum.apirest.security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTProvider jwtProvider;

    public UserResponse createUser(UserRequest userRequest) {
        Integer userState;
        String tokenGenerated;
        Optional<UserEntity> userEntity;
        RolEntity userRolFound;
        Set<RolEntity> setRolInsert;
        UserEntity userRegistered;
        UserEntity userEntityCreated;
        UsernamePasswordAuthenticationToken userProtected;
        Authentication authentication;

        userEntity = userRepository.findByEmail(userRequest.getEmail());
        if (userEntity.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya fue registrado");
        userRolFound = rolRepository.findByName(RolesEnum.ROLE_USER.toString())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Usuario sin ningun Rol Asignado"));

        setRolInsert = new HashSet<>();
        setRolInsert.add(userRolFound);

        String passwordOld = userRequest.getPassword();
        String password = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(password);
        userEntityCreated = UserRequest.getConvertionUserEntity(userRequest);
        userEntityCreated.setRoles(setRolInsert);
        userRepository.save(userEntityCreated);
        userRegistered = userRepository.findByEmail(userRequest.getEmail()).orElse(null);
        tokenGenerated = jwtProvider.getToken(userRequest.getEmail());
        return UserResponse.builder().id(userRegistered.getId()).created(userRegistered.getDateCreate())
                .modified(userRegistered.getDateModified()).last_login(userRegistered.getDateLastLogin())
                .isactive(userRegistered.getStatus()).token(tokenGenerated).build();
    }


}
