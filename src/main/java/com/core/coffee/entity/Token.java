package com.core.coffee.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.core.coffee.enums.TokenType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection = "tokens")
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token extends GenericEntity  {

    @Id
    private String id;  
    private boolean expirated;
    private boolean revoked;  

    @DBRef
    private User user;
    private String token;
    private String refreshToken;
    private TokenType tokenType;

    
    
    


    
}
