package com.core.coffee.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection = "apartments")
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Apartment extends GenericEntity {

    @Id
    private String id;
    
    @DocumentReference( lazy = true)
    private User user;
    
    private String owner;

    @DocumentReference( lazy = true)
    private Condominium condominium;
    
    private String name;
    
    private String description;

    private int floor;
    private int number;
    private String phoneContact;
    private String emailContact;

    
}
