package com.core.coffee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.core.coffee.entity.Apartment;



@Repository
public interface ApartmentRepository extends MongoRepository<Apartment, String> {

    Optional<Apartment> findByCondominiumAndNameAndActiveIsTrue(String condominiumId,String name);
    Optional<Apartment> findByIdAndActiveIsTrue(String id);

    List<Apartment> findAllByCondominiumAndActiveIsTrue(String condominiumId);
    List<Apartment> findAllByActiveIsTrue(String condominiumId);
    
}
