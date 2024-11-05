package com.core.coffee.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ErrorDto {
    String errorCode;
    String error;
    Date timestamp;
}
