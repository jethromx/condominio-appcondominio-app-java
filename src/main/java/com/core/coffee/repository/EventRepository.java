package com.core.coffee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.core.coffee.entity.Event;

public interface EventRepository extends MongoRepository<Event, String> {

    Optional<Event> findByIdAndActiveIsTrue(String id);
    Optional<Event> findByCondominiumAndNameAndActiveIsTrue(String condominiumId,String name);

    List<Event> findAllByCondominiumAndActiveIsTrue(String condominiumId);
    List<Event> findAllByActiveIsTrue(String condominiumId);
    List<Event> findAllByCondominiumAndApartmentAndActiveIsTrue(String condominiumId,String apartmentId);


    
}
