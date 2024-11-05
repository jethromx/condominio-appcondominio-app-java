package com.core.coffee.dto;

import org.springframework.http.HttpStatus;

import com.core.coffee.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServiceResponse<T> {

    private final Status status;

    private final T responseObject;

    private final String message;

    private HttpStatus httpCode;

   
   

}
