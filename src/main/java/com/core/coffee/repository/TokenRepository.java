package com.core.coffee.repository;

import com.core.coffee.entity.Token;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    List<Token> findAllValidIsFalseOrRevokedIsFalseByUserId(String userId);
    Optional<Token> findByToken(String token);
                    
    
}
