package com.core.coffee.dto;


import com.fasterxml.jackson.annotation.JsonInclude;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class GetApartmentDto extends CreateApartmentDto {

    private String user;       

    private String name;

    private String description;
   
    private int floor;
    
    private int number;

    private String phoneContac;

    private String emailContact;
    
    private String id;

    private String condominium;
}
