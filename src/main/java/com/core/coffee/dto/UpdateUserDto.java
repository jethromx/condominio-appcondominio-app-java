package com.core.coffee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
        
        private String name;
        private String email;
        private String password;
        private String role;
        private boolean active;
}
