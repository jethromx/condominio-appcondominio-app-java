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

import com.core.coffee.dto.CreateCondominiumDto;
import com.core.coffee.dto.GetCondominiumDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateCondominiumDto;
import com.core.coffee.entity.Condominium;
import com.core.coffee.entity.User;
import com.core.coffee.enums.Status;
import com.core.coffee.exception.CustomException;
import com.core.coffee.repository.CondominiumRepository;
import com.core.coffee.repository.CustomRepository;
import com.core.coffee.service.CondominiumService;
import com.core.coffee.util.Constants;
import com.core.coffee.util.MapperUtil;

@Service
public class CondominiumServiceImpl  extends CondominiumService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CondominiumServiceImpl.class);
    private static final String LOGLINE = CondominiumServiceImpl.class.getName() + " - {} - {}";

    private static final String ENTITY = "Condominium";




    private final CondominiumRepository condominiumRepository; 
    private final CustomRepository customRepository;
    private final MapperUtil mapperUtil;

    public CondominiumServiceImpl(
        CondominiumRepository condominiumRepository,
        CustomRepository customRepository,
        MapperUtil mapperUtil
        ) {
        this.condominiumRepository = condominiumRepository;
        this.customRepository = customRepository;
        this.mapperUtil = mapperUtil;
        
    }

    @Override
    public ServiceResponse<PagedResponse<?>> getAll(int page, int size) {
        LOGGER.info(LOGLINE, Constants.GETS+ENTITY, Constants.IN);

        Pageable pageable = PageRequest.of(page, size);
        Page<Condominium> items = condominiumRepository.findAll(pageable);

        List<GetCondominiumDto> dtoOut = items.stream().map(item -> {
            GetCondominiumDto dto = mapperUtil.map(item, GetCondominiumDto.class);
            return dto;
        }).collect(Collectors.toList());

        PagedResponse<GetCondominiumDto> pagedResponse = new PagedResponse<>(dtoOut, items.getNumber(), items.getSize(), items.getTotalElements(), items.getTotalPages(), items.isLast());


        LOGGER.info(LOGLINE, Constants.GETS+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, pagedResponse, Constants.GET_CONDOMINIUMS, HttpStatus.OK);

    }

    @Override
    public ServiceResponse<?> create(CreateCondominiumDto dto) {
        LOGGER.info(LOGLINE, Constants.CREATE+ENTITY, Constants.IN);

        Condominium entity = this.mapperUtil.map(dto, Condominium.class);

        condominiumRepository.findByNameAndActiveIsTrue(dto.getName()).ifPresent(item -> {
            LOGGER.warn(LOGLINE, Constants.CREATE+ENTITY, Constants.API_CONDOMINIUM_ALREADY_EXISTS);
            throw new CustomException(Constants.API_CONDOMINIUM_ALREADY_EXISTS, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });        

        entity.setAdministrator(User.builder().id(dto.getAdministrator()).build());
        String collectionName =entity.getName().toLowerCase().trim().replace(" ", "_");
        customRepository.createCollection(collectionName);

        Condominium savedItem = condominiumRepository.save(entity);
        GetCondominiumDto dtoOut = mapperUtil.map(savedItem, GetCondominiumDto.class);

        LOGGER.info(LOGLINE, Constants.CREATE+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, dtoOut, Constants.CREATE_CONDOMINIUM, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<Condominium> update(String condominiumId, UpdateCondominiumDto dto) {
        LOGGER.info(LOGLINE, Constants.UPDATE+ENTITY, Constants.IN);

        Condominium item = validateExistCondominium(condominiumId);
        
        item = (Condominium) this.mapperUtil.merge(dto, item);
        Condominium savedItem = condominiumRepository.save(item);
    
        LOGGER.info(LOGLINE, Constants.UPDATE+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, savedItem, Constants.UPDATE_CONDOMINIUM, HttpStatus.OK);
    }


    @Override
    public ServiceResponse<?> delete(String condominiumId) {
        LOGGER.info(LOGLINE, Constants.DELETE+ENTITY, Constants.IN);

        Condominium item = validateExistCondominium(condominiumId);

        item.setActive(false);        
        condominiumRepository.save(item);        
        return new ServiceResponse<>(Status.OK, null, Constants.DELETE_CONDOMINIUM, HttpStatus.OK);       
    }

    @Override
    public ServiceResponse<?> getItem(String condominiumId) {
        LOGGER.info(LOGLINE, Constants.GET+ENTITY, Constants.IN);        

        Condominium item = validateExistCondominium(condominiumId);

        GetCondominiumDto dtoOut = mapperUtil.map(item, GetCondominiumDto.class);        

        LOGGER.info(LOGLINE, Constants.GET+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, dtoOut, Constants.GET_CONDOMINIUM, HttpStatus.OK);
    }    


    public Condominium validateExistCondominium(String condominiumId){
        return  condominiumRepository.findByIdAndActiveIsTrue(condominiumId).orElseThrow(() ->{
            LOGGER.warn(LOGLINE, ENTITY, Constants.API_CONDOMINIUM_NOT_FOUND);
            throw new CustomException(Constants.API_CONDOMINIUM_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());            
        } ); 
    }

    
}
