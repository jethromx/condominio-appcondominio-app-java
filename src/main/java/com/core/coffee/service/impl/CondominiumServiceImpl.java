package com.core.coffee.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.core.coffee.dto.CreateCondominiumDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateCondominiumDto;
import com.core.coffee.entity.Condominium;
import com.core.coffee.enums.Status;
import com.core.coffee.exception.CustomException;
import com.core.coffee.repository.CondominiumRepository;
import com.core.coffee.service.CondominiumService;
import com.core.coffee.util.Constants;
import com.core.coffee.util.MapperUtil;

@Service
public class CondominiumServiceImpl  implements CondominiumService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CondominiumServiceImpl.class);
    private static final String LOGLINE = CondominiumServiceImpl.class.getName() + " - {} - {}";

    private static final String CREATE = "createCondominium";
    private static final String UPDATE = "updateCondominium";
    private static final String DELETE = "deleteCondominium";
    private static final String GET = "getCondominium";
    private static final String GETS = "getCondominiums";

    private static final String CREATE_CONDOMINIUM = "Condominium Created Successful";
    private static final String UPDATE_CONDOMINIUM = "Condominium Updated Successful";
    private static final String DELETE_CONDOMINIUM = "Condominium Deleted Successful";
    private static final String GET_CONDOMINIUM= "Condominium Found Successful";
    private static final String GET_CONDOMINIUMS = "Condominium Found Successful";




    private final CondominiumRepository condominiumRepository; 
    private final MapperUtil mapperUtil;

    public CondominiumServiceImpl(
        CondominiumRepository condominiumRepository,
        MapperUtil mapperUtil
        ) {
        this.condominiumRepository = condominiumRepository;
        this.mapperUtil = mapperUtil;
        
    }

    @Override
    @Cacheable(value="condominiumsCache")
    public ServiceResponse<PagedResponse<?>> getAll(int page, int size) {
        LOGGER.info(LOGLINE, GETS, Constants.IN);

        Pageable pageable = PageRequest.of(page, size);
        Page<Condominium> items = condominiumRepository.findAll(pageable);

        PagedResponse<Condominium> pagedResponse = new PagedResponse<>(items);


        LOGGER.info(LOGLINE, GETS, Constants.OUT);
        return new ServiceResponse<>(Status.OK, pagedResponse, GET_CONDOMINIUMS, HttpStatus.OK);

    }

    @Override
    public ServiceResponse<Condominium> create(CreateCondominiumDto dto) {
        LOGGER.info(LOGLINE, CREATE, Constants.IN);

        Condominium entity = this.mapperUtil.map(dto, Condominium.class);

        condominiumRepository.findByNameAndActiveIsTrue(dto.getName()).ifPresent(item -> {
            LOGGER.warn(LOGLINE, CREATE, Constants.API_CONDOMINIUM_ALREADY_EXISTS);
            throw new CustomException(Constants.API_CONDOMINIUM_ALREADY_EXISTS, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });        

        Condominium savedItem = condominiumRepository.save(entity);

        LOGGER.info(LOGLINE, CREATE, Constants.OUT);
        return new ServiceResponse<>(Status.OK, savedItem, CREATE_CONDOMINIUM, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<Condominium> update(String condominiumId, UpdateCondominiumDto condominium) {
        LOGGER.info(LOGLINE, UPDATE, Constants.IN);

        Condominium item = condominiumRepository.findById(condominiumId).orElseThrow(() -> {
            LOGGER.warn(LOGLINE, UPDATE, Constants.API_CONDOMINIUM_NOT_FOUND);
            throw new CustomException(Constants.API_CONDOMINIUM_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        Condominium savedItem = condominiumRepository.save(item);

        LOGGER.info(LOGLINE, UPDATE, Constants.OUT);
        return new ServiceResponse<>(Status.OK, savedItem, UPDATE_CONDOMINIUM, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<Condominium> delete(String condominiumId) {
        LOGGER.info(LOGLINE, DELETE, Constants.IN);

        Condominium item = condominiumRepository.findById(condominiumId).orElseThrow(() -> {
            LOGGER.warn(LOGLINE, DELETE, Constants.API_CONDOMINIUM_NOT_FOUND);
            throw new CustomException(Constants.API_CONDOMINIUM_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        item.setActive(false);
        condominiumRepository.save(item);

        LOGGER.info(LOGLINE, DELETE, Constants.OUT);
        return new ServiceResponse<>(Status.OK, null, DELETE_CONDOMINIUM, HttpStatus.OK);       
    }

    @Override
    @Cacheable(value="condominium", key="#condominiumId")
    public ServiceResponse<Condominium> getItem(String condominiumId) {
        LOGGER.info(LOGLINE, GET, Constants.IN);

        Condominium item = condominiumRepository.findById(condominiumId).orElseThrow(() ->{
            LOGGER.warn(LOGLINE, GET, Constants.API_CONDOMINIUM_NOT_FOUND);
            throw new CustomException(Constants.API_CONDOMINIUM_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());            
        } ); 

        if(!item.isActive()) {
                LOGGER.warn(LOGLINE, GET, Constants.API_CONDOMINIUM_NOT_FOUND);
                throw new CustomException(Constants.API_CONDOMINIUM_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
            }

        LOGGER.info(LOGLINE, GET, Constants.OUT);
        return new ServiceResponse<>(Status.OK, item, GET_CONDOMINIUM, HttpStatus.OK);
    }    
}
