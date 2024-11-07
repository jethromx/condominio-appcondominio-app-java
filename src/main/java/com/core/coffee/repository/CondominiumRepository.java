package com.core.coffee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.core.coffee.entity.Condominium;



@Repository
public interface CondominiumRepository extends MongoRepository<Condominium, String> {

    Optional<Condominium> findByNameAndActiveIsTrue(String name);
    Optional<Condominium> findByIdAndActiveIsTrue(String id);

    List<Condominium> findAllByActiveIsTrue();

        
}
