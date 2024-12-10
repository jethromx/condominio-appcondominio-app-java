package com.core.coffee.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends GenericEntity {
        @Id
        private String id;        
        private String name;
        private String lastname;
        @Indexed
        private String email;

        @JsonIgnore
        private String password;
        private List<String> roles; //rol: Rol del usuario (propietario, residente, admin, superadmin)

        @DocumentReference( lazy = true)    
        private Condominium condominium;
        @DocumentReference( lazy = true)   
        private Apartment apartment;
        
        private String phone;

        

       
    
}
