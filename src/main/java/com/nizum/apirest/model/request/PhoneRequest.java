package com.nizum.apirest.model.request;

import com.nizum.apirest.model.entities.PhoneEntity;
import com.sun.istack.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneRequest {
    @NotNull
    private String number;
    @NotNull
    private String citycode;
    @NotNull
    private String contrycode;

    public static PhoneEntity getConvertionPhoneEntity(PhoneRequest phone){

        return PhoneEntity.builder()
                .id(UUID.randomUUID().toString())
                .number(phone.number)
                .cityCode(phone.citycode)
                .countryCode(phone.contrycode).build();
    }
}
