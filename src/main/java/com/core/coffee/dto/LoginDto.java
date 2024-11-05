package com.core.coffee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Campo email obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;
    
    @NotBlank(message = "Campo password obligatorio")
    @Size(min = 6, message = "El Campo debe tener al menos 6 caracteres")
    private String password;
    
}
