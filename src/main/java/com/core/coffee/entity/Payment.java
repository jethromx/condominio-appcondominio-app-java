package com.core.coffee.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/*
 * 
 * Pagos realizados por parte de un departamento, como cuotas de mantenimiento 
 */
@Document(collection = "payments")
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends GenericEntity {

    @Id
    private String id;
    @DBRef
    private User user;

    private Double amount;

    @DBRef(lazy = true)
    private MaintenanceFee maintenanceFee; // cuota de mantenimiento

    private String status; //Paid, Pending, Overdue

    private String notes;

    private String paymentMethod; 

    private Apartment apartment;

   
    
}
