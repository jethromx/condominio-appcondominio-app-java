package com.core.coffee.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CreateApartmentDto {

    @NotBlank(message = "Campo user obligatorio")
    private String user;   
    
    @NotBlank(message = "Campo name obligatorio")
    private String name;

    private String description;

   
    private int floor;

    
    private int number;

    private String phoneContac;
    private String emailContact;
    
}
