package com.core.coffee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCondominiumDto {
    private String name;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String country;
    
    private String phone;

    private String email;
    
    private String website;

    private String contactPerson;

    private String contactPhone;

    private String contactEmail;
    
    private String notes;
    
}
