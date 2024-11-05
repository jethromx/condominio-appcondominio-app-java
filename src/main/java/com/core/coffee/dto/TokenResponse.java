package com.core.coffee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {

    @JsonProperty("access_token") 
    private String token;

    @JsonProperty("refresh_token")
    private String refreshToken;

   
}
