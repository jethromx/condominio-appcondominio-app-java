package com.core.coffee.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection = "maintenanceFees", language = "spanish", collation = "es_MX" )
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceFee extends GenericEntity{

    @Id
    private String id;

    private Double amount;
    private String description;
    private String status; //Paid, Pending, Overdue


    @DBRef(lazy = true)     
    private Apartment apartment;

    private Date expirationDate;
    



    
}
