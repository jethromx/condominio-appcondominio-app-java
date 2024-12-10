package com.core.coffee.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Document(collection = "events")
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event  extends GenericEntity{
    @Id
    private String id;

   
    @DocumentReference( lazy = true)
    private Condominium condominium;

    @DocumentReference( lazy = true)
    private Apartment apartment;

    private String name;
    private String description;
    private String location;
    private Date dateStart;
    private Date dateEnd;    
    
    
        
}
