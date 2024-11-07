package com.core.coffee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CreateCondominiumDto {

    @NotBlank(message = "Campo name obligatorio")
    private String name;

    @NotBlank(message = "Campo address obligatorio")
    private String address;

    @NotBlank(message = "Campo city obligatorio")
    private String city;

    @NotBlank(message = "Campo state obligatorio")
    private String state;

    @NotBlank(message = "Campo zipCode obligatorio")
    private String zipCode;

    @NotBlank(message = "Campo country obligatorio")
    private String country;
    
    private String phone;

    private String email;
    
    private String website;

    @NotBlank(message = "Campo contactPerson obligatorio")
    private String contactPerson;

    @NotBlank(message = "Campo contactPhone obligatorio")
    private String contactPhone;

    @NotBlank(message = "Campo contactEmail obligatorio")
    private String contactEmail;
    
    private String notes;
    
}
