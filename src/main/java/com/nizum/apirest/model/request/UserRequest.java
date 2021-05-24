package com.nizum.apirest.model.request;

import com.nizum.apirest.constant.StatusEnum;
import com.nizum.apirest.model.entities.PhoneEntity;
import com.nizum.apirest.model.entities.UserEntity;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest{

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotBlank(message = "El email no puede estar vacío")
    @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9-]{2,}$", message = "El correo no tiene el formato correcto")
    private String email;
    @NotBlank(message = "El password no puede estar vacío")
    private String password;
    @NotEmpty(message = "El phones no puede estar vacío")
    private List<PhoneRequest> phones;


    public static UserEntity getConvertionUserEntity(UserRequest userRequest){
        List<PhoneEntity> phones =userRequest.phones.stream().map(phone -> PhoneRequest.getConvertionPhoneEntity(phone)).collect(Collectors.toList());
        return UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .email(userRequest.email)
                .name(userRequest.name)
                .password(userRequest.password)
                .status(StatusEnum.STATUS_ACTIVE.getCode())
                .phones(userRequest.phones.stream().map(phone -> PhoneRequest.getConvertionPhoneEntity(phone)).collect(Collectors.toList()))
                .build();
    }

}
