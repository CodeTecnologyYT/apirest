package com.nizum.apirest.model.request;

import com.nizum.apirest.model.entities.PhoneEntity;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneRequest {
    @NotBlank(message = "El number no puede estar vacío")
    private String number;
    @NotBlank(message = "El citycode no puede estar vacío")
    private String citycode;
    @NotBlank(message = "El contrycode no puede estar vacío")
    private String contrycode;

    public static PhoneEntity getConvertionPhoneEntity(PhoneRequest phone){

        return PhoneEntity.builder()
                .id(UUID.randomUUID().toString())
                .number(phone.number)
                .cityCode(phone.citycode)
                .countryCode(phone.contrycode).build();
    }
}
