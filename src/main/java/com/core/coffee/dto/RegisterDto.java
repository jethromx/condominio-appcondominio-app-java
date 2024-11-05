package com.core.coffee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {

        @NotBlank(message = "Campo name obligatorio")
        @Size(min = 3, message = "El Campo debe tener al menos 3 caracteres")
        private String name;

        @NotBlank(message = "Campo lastname obligatorio")
        @Size(min = 3, message = "El Campo debe tener al menos 3 caracteres")
        private String lastname;

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe ser válido")
        private String email;


        @NotBlank(message = "El Campo password es obligatorio")
        @Size(min = 6, message = "El Campo password debe tener al menos 2 caracteres")        
        private String password;


       // @NotBlank(message = "El Campo es obligatorio")
       // private String role;
    
}
