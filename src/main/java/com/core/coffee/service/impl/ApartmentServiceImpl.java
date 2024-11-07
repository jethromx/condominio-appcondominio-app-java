package com.core.coffee.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.core.coffee.dto.CreateApartmentDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateAparmentDto;
import com.core.coffee.entity.Apartment;
import com.core.coffee.entity.Condominium;
import com.core.coffee.entity.User;
import com.core.coffee.enums.Status;
import com.core.coffee.exception.CustomException;
import com.core.coffee.repository.ApartmentRepository;
import com.core.coffee.repository.CondominiumRepository;
import com.core.coffee.repository.UserRepository;
import com.core.coffee.service.ApartmentService;
import com.core.coffee.util.Constants;
import com.core.coffee.util.MapperUtil;


@Service
public class ApartmentServiceImpl  implements ApartmentService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentServiceImpl.class);
    private static final String LOGLINE = ApartmentServiceImpl.class.getName() + " - {} - {}";

    private static final String CREATE = "createApartment";
    private static final String UPDATE = "updateApartment";
    private static final String DELETE = "deleteApartment";
    private static final String GET = "getApartment";
    private static final String GETS = "getApartments";

    private static final String CREATE_APARTMENT = "Apartment Created Successful";
    private static final String UPDATE_APARTMENT = "Apartment Updated Successful";
    private static final String DELETE_APARTMENT = "Apartment Deleted Successful";
    private static final String GET_APARTMENT= "Apartment Found Successful";

    private final MapperUtil mapperUtil;
    private final ApartmentRepository apartmentRepository;
    private final CondominiumRepository condominiumRepository;
    private final UserRepository   userRepository;

    public ApartmentServiceImpl(
        MapperUtil mapperUtil,
        ApartmentRepository apartmentRepository,
        CondominiumRepository condominiumRepository,
        UserRepository userRepository
        ) {
        this.mapperUtil = mapperUtil;
        this.apartmentRepository = apartmentRepository;
        this.condominiumRepository = condominiumRepository;
        this.userRepository = userRepository;
        
    }


    @Override
    public ServiceResponse<Apartment> create(CreateApartmentDto dto,String condominiumId) {
        LOGGER.info(LOGLINE, CREATE, Constants.IN);

        // Check if the condominium exists
        Condominium condominium = condominiumRepository.findByIdAndActiveIsTrue(condominiumId).orElseThrow( ()->{
            LOGGER.warn(LOGLINE, CREATE, Constants.API_CONDOMINIUM_NOT_FOUND);
            throw new CustomException(Constants.API_CONDOMINIUM_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        // Check if the user exists
        User user = userRepository.findByIdAndActiveIsTrue(dto.getUser()).orElseThrow( ()->{
            LOGGER.warn(LOGLINE, CREATE, Constants.API_USER_NOT_FOUND);
            throw new CustomException(Constants.API_USER_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        // Check if the apartment already exists
        apartmentRepository.findByCondominiumAndNameAndActiveIsTrue(condominiumId,dto.getName()).ifPresent( (apartment) -> {
            LOGGER.warn(LOGLINE, CREATE, Constants.API_APARTMENT_ALREADY_EXISTS);
            throw new CustomException(Constants.API_APARTMENT_ALREADY_EXISTS, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });


        // Create the apartment
        Apartment apartment = mapperUtil.map(dto, Apartment.class);
        apartment.setCondominium(condominium);   
        apartment.setUser(user);     
        apartment = apartmentRepository.save(apartment);

        LOGGER.info(LOGLINE, CREATE, Constants.OUT);

        return new ServiceResponse<>(Status.OK, apartment,CREATE_APARTMENT, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<?> delete(String id) {
        LOGGER.info(LOGLINE, DELETE, Constants.IN);

        Apartment apartment = apartmentRepository.findById(id).orElseThrow( ()->{
            LOGGER.warn(LOGLINE, DELETE, Constants.API_APARTMENT_NOT_FOUND);
            throw new CustomException(Constants.API_APARTMENT_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });
        
        apartment.setActive(false);
        apartmentRepository.save(apartment);

        LOGGER.info(LOGLINE, DELETE, Constants.OUT);
        return new ServiceResponse<>(Status.OK,null, DELETE_APARTMENT, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<?> getItem(String id) {
        LOGGER.info(LOGLINE, GET, Constants.IN);

        Apartment apartment = apartmentRepository.findByIdAndActiveIsTrue(id).orElseThrow( ()->{
            LOGGER.warn(LOGLINE, GET, Constants.API_APARTMENT_NOT_FOUND);
            throw new CustomException(Constants.API_APARTMENT_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        LOGGER.info(LOGLINE, GET, Constants.OUT);
        return new ServiceResponse<>(Status.OK, apartment, GET_APARTMENT, HttpStatus.OK);
        
    }

    @Override
    public ServiceResponse<PagedResponse<?>> getAll(int page, int size) {
        LOGGER.info(LOGLINE, GETS, Constants.IN);

        Pageable pageable = PageRequest.of(page, size);
        Page<Apartment> items = apartmentRepository.findAll(pageable);

        PagedResponse<Apartment> pagedResponse = new PagedResponse<>(items);

        LOGGER.info(LOGLINE, GETS, Constants.OUT);
        return new ServiceResponse<>(Status.OK, pagedResponse, GETS, HttpStatus.OK);        
    }

    @Override
    public ServiceResponse<Apartment> update(String apartmentId, UpdateAparmentDto apartment) {
        
        LOGGER.info(LOGLINE, UPDATE, Constants.IN);

        Apartment item = apartmentRepository.findByIdAndActiveIsTrue(apartmentId).orElseThrow(() -> {
            LOGGER.warn(LOGLINE, UPDATE, Constants.API_APARTMENT_NOT_FOUND);
            throw new CustomException(Constants.API_APARTMENT_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        item = mapperUtil.map(apartment, Apartment.class);
        item = apartmentRepository.save(item);

        LOGGER.info(LOGLINE, UPDATE, Constants.OUT);
        return new ServiceResponse<>(Status.OK, item, UPDATE_APARTMENT, HttpStatus.OK);
    }


    @Override
    public ServiceResponse<?> create(CreateApartmentDto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    
    
}
