package com.core.coffee.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection = "condominiums")
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Condominium extends GenericEntity {

    @Id
    private String id;  
    
   @DocumentReference( lazy = true)
    private User administrator;
    
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
    private String numbersApartments;

    
}
