package com.nizum.apirest.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum{
    STATUS_ACTIVE(1),
    STATUS_INACTIVE(0);
    private final int code;
}
