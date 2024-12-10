package com.core.coffee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import com.core.coffee.entity.Event;

public interface EventRepository extends MongoRepository<Event, String> {

    Optional<Event> findByIdAndActiveIsTrue(String id);
    Optional<Event> findByCondominiumAndNameAndActiveIsTrue(String condominiumId,String name);

    Page<Event> findAllByCondominium_IdAndActiveIsTrue(Pageable pageable,String condominiumId);
    List<Event> findAllByActiveIsTrue(String condominiumId);
    Optional<Event> findAllByCondominium_IdAndApartment_IdAndNameAndActiveIsTrue(String condominiumId,String apartmentId,String name);
    

    


    
}
