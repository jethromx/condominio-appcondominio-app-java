package com.core.coffee.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "charges")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Charge {
    private String id;

    private Condominium condominium;
    private Apartment apartment;
    private String chargeType;
    private Double amount;
    private String description;
    
    
}
