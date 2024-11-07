package com.core.coffee.entity;


import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
        private String password;
        private List<String> roles; //rol: Rol del usuario (propietario, residente, admin, superadmin)

        @DBRef(lazy = true)     
        private Condominium condominium;
        @DBRef(lazy = true)     
        private Apartment apartment;
        
        private String phone;

        

       
    
}
