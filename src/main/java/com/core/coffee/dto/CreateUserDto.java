package com.core.coffee.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
        
        
        @NotBlank(message = "Campo obligatorio")
        @Size(min = 2, message = "El Campo debe tener al menos 2 caracteres")
        private String name;

        @NotBlank(message = "Campo obligatorio")
        @Size(min = 2, message = "El Campo debe tener al menos 2 caracteres")
        private String lastname;

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe ser válido")
        private String email;


        @NotBlank(message = "El Campo es obligatorio")
        @Size(min = 2, message = "El Campo debe tener al menos 2 caracteres")
        private String password;


        @NotBlank(message = "El Campo es obligatorio")
        private List<String> roles;
 
}
