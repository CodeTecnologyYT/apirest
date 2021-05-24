package com.nizum.apirest.model.request;

import com.nizum.apirest.constant.StatusEnum;
import com.nizum.apirest.model.entities.PhoneEntity;
import com.nizum.apirest.model.entities.UserEntity;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;
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
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
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
