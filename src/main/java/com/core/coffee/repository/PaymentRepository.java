package com.core.coffee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.core.coffee.entity.Payment;

public interface PaymentRepository  extends MongoRepository<Payment, String> {

    Optional<Payment> findByIdAndActiveIsTrue(String id);
   // Optional<Event> findByCondominiumAndNameAndActiveIsTrue(String condominiumId,String name);

    List<Payment> findAllByUserAndActiveIsTrue(String user);
   
    //List<Payment> findAllByCondominiumAndApartmentAndActiveIsTrue(String condominiumId,String apartmentId);
  //  List<Payment> findAllByApartmentAndActiveIsTrue(String apartmentId);
    
}
