package com.nizum.apirest.model.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserResponse {
    private String id;
    private Date created;
    private Date modified;
    private Date last_login;
    private String token;
    private Integer isactive;
}
