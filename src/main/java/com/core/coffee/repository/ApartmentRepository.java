package com.core.coffee.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.core.coffee.entity.Apartment;



@Repository
public interface ApartmentRepository extends MongoRepository<Apartment, String> {

    Optional<Apartment> findByName(String name); 
    Optional<Apartment> findByNameAndCondominium_IdAndActiveIsTrue(String name,String condominiumId); 
   
    
    Optional<Apartment> findByIdAndActiveIsTrue(String id);   
    
    Page<Apartment> findByCondominium_IdAndActiveIsTrue(Pageable pageable,String condominiumId);
    
}
