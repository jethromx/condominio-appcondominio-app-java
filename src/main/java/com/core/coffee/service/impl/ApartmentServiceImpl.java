package com.core.coffee.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.core.coffee.dto.CreateApartmentDto;
import com.core.coffee.dto.GetApartmentDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateApartmentDto;
import com.core.coffee.entity.Apartment;
import com.core.coffee.entity.Condominium;
import com.core.coffee.entity.User;
import com.core.coffee.enums.Status;
import com.core.coffee.exception.CustomException;
import com.core.coffee.repository.ApartmentRepository;
import com.core.coffee.service.ApartmentService;
import com.core.coffee.service.CondominiumService;
import com.core.coffee.service.UserService;
import com.core.coffee.util.Constants;
import com.core.coffee.util.MapperUtil;


@Service
public class ApartmentServiceImpl  extends ApartmentService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentServiceImpl.class);
    private static final String LOGLINE = ApartmentServiceImpl.class.getName() + " - {} - {}";

    private static final String ENTITY = "Apartment";

 

    private final MapperUtil mapperUtil;
    private final ApartmentRepository apartmentRepository;
    private final UserService userService;

    private final CondominiumService condominiumService;

    public ApartmentServiceImpl(
        MapperUtil mapperUtil,
        ApartmentRepository apartmentRepository,
        UserService userService,
        CondominiumService condominiumService
        ) {
        this.mapperUtil = mapperUtil;
        this.apartmentRepository = apartmentRepository;
        this.condominiumService = condominiumService;
        this.userService = userService;
        
    }


    @Override
    public ServiceResponse<?> create(CreateApartmentDto dto,String condominiumId) {
        LOGGER.info(LOGLINE, Constants.CREATE+ENTITY, Constants.IN);

        // Check if the condominium exists
        Condominium condominium = this.condominiumService.validateExistCondominium(condominiumId);

        // Check if the user exists
        User user = this.userService.validateExistUser(dto.getUser());

        // Check if the apartment already exists
        apartmentRepository.findByNameAndCondominium_IdAndActiveIsTrue(dto.getName(),condominiumId).ifPresent( (apartment) -> {
            LOGGER.warn(LOGLINE, Constants.CREATE+ENTITY, Constants.API_APARTMENT_ALREADY_EXISTS);
            throw new CustomException(Constants.API_APARTMENT_ALREADY_EXISTS, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });


        // Create the apartment
        Apartment apartment = mapperUtil.map(dto, Apartment.class);
        apartment.setCondominium(condominium);   
        apartment.setUser(user);     
        apartment = apartmentRepository.save(apartment);

        GetApartmentDto dtoOut = mapperUtil.map(apartment, GetApartmentDto.class);

        LOGGER.info(LOGLINE, Constants.CREATE, Constants.OUT);

        return new ServiceResponse<>(Status.OK, dtoOut,Constants.CREATE_APARTMENT, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<?> delete(String id) {
        LOGGER.info(LOGLINE, Constants.DELETE+ENTITY, Constants.IN);

        // Check if the apartment exists
        Apartment apartment = validateExistApartment(id);

        // Check if the condominium exists       
        this.condominiumService.validateExistCondominium(apartment.getCondominium().getId());
        
        apartment.setActive(false);
        apartmentRepository.save(apartment);

        

        LOGGER.info(LOGLINE, Constants.DELETE+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK,null, Constants.DELETE_APARTMENT, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<?> getItem(String id) {
        LOGGER.info(LOGLINE, Constants.GET+ENTITY, Constants.IN);
        
        // Check if the apartment exists
        Apartment apartment = validateExistApartment(id);

        // Check if the condominium exists       
        this.condominiumService.validateExistCondominium(apartment.getCondominium().getId());
        
        GetApartmentDto dtoOut = mapperUtil.map(apartment, GetApartmentDto.class);

        LOGGER.info(LOGLINE, Constants.GET+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, dtoOut, Constants.GET_APARTMENT, HttpStatus.OK);
        
    }

    @Override
    public ServiceResponse<PagedResponse<?>> getAll(int page, int size, String condominiumId) {
        LOGGER.info(LOGLINE, Constants.GETS, Constants.IN);

        // Check if the condominium exists
        this.condominiumService.validateExistCondominium(condominiumId);

        LOGGER.info(LOGLINE, condominiumId, Constants.OUT);
        Pageable pageable = PageRequest.of(page, size);
        Page<Apartment> items = apartmentRepository.findByCondominium_IdAndActiveIsTrue(pageable, condominiumId);
                         
        List<GetApartmentDto> dtoOut = items.stream().map(item -> {
            GetApartmentDto dto = mapperUtil.map(item, GetApartmentDto.class);
            return dto;
        }).collect(Collectors.toList());

        PagedResponse<GetApartmentDto> pagedResponse = new PagedResponse<>(dtoOut, items.getNumber(), items.getSize(), items.getTotalElements(), items.getTotalPages(), items.isLast());

       
        LOGGER.info(LOGLINE, Constants.GETS+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, pagedResponse, Constants.GETS_APARTMENT, HttpStatus.OK);        
    }

    @Override
    public ServiceResponse<?> update(String apartmentId, UpdateApartmentDto dto) {
        
        LOGGER.info(LOGLINE, Constants.UPDATE+ENTITY, Constants.IN);

        // Check if the apartment exists
        Apartment item = validateExistApartment(apartmentId);

        // Check if the condominium exists     
        this.condominiumService.validateExistCondominium(item.getCondominium().getId());

        item =(Apartment)mapperUtil.merge(dto, item);
        item = apartmentRepository.save(item);
        GetApartmentDto dtoOut = mapperUtil.map(item, GetApartmentDto.class);

        LOGGER.info(LOGLINE, Constants.UPDATE+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, dtoOut, Constants.UPDATE_APARTMENT, HttpStatus.OK);
    }

    @Override
    public Apartment validateExistApartment(String id){
        return apartmentRepository.findByIdAndActiveIsTrue(id).orElseThrow( ()->{
            LOGGER.warn(LOGLINE, ENTITY, Constants.API_APARTMENT_NOT_FOUND);
            throw new CustomException(Constants.API_APARTMENT_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });
    }


    


    
}
