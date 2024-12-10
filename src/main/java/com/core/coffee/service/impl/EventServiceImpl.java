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

import com.core.coffee.dto.CreateEventDto;
import com.core.coffee.dto.GetEventDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateEventDto;
import com.core.coffee.entity.Apartment;
import com.core.coffee.entity.Condominium;
import com.core.coffee.entity.Event;
import com.core.coffee.enums.Status;
import com.core.coffee.exception.CustomException;
import com.core.coffee.repository.EventRepository;
import com.core.coffee.service.ApartmentService;
import com.core.coffee.service.CondominiumService;
import com.core.coffee.service.EventService;
import com.core.coffee.util.Constants;
import com.core.coffee.util.MapperUtil;

@Service
public class EventServiceImpl extends EventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);
    private static final String LOGLINE = EventServiceImpl.class.getName() + " - {} - {}";

    private static final String ENTITY = "Event";

    final EventRepository eventRepository;
    
    final CondominiumService condominiumService;
    final ApartmentService apartmentService;
    final MapperUtil mapperUtil;

    public EventServiceImpl(
        EventRepository eventRepository, 
        ApartmentService apartmentService,
        CondominiumService condominiumService,
        MapperUtil mapperUtil
        ) {
        this.eventRepository = eventRepository;
        this.mapperUtil = mapperUtil;
        this.condominiumService = condominiumService;
        this.apartmentService = apartmentService;

    }


    @Override
    public ServiceResponse<?> createEvent(CreateEventDto dto, String condominiumId) {
        LOGGER.info(LOGLINE, Constants.CREATE+ENTITY, Constants.IN);

        // Check if the condominium exists
        this.condominiumService.validateExistCondominium(condominiumId);        

        // Check if the apartment exists
        Apartment apartment = this.apartmentService.validateExistApartment(dto.getApartment());

        eventRepository.findAllByCondominium_IdAndApartment_IdAndNameAndActiveIsTrue(condominiumId, dto.getApartment(), dto.getName()).ifPresent( e -> {
            LOGGER.warn(LOGLINE, Constants.CREATE+ENTITY, Constants.API_EVENT_ALREADY_EXISTS);
            throw new CustomException(Constants.API_EVENT_ALREADY_EXISTS, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        Event entity = this.mapperUtil.map(dto, Event.class);
        Condominium condominium = new Condominium();
        condominium.setId(condominiumId);

        entity.setCondominium(condominium);
        
        entity.setApartment(apartment);

        Event savedItem = eventRepository.save(entity);

        GetEventDto response = this.mapperUtil.map(savedItem, GetEventDto.class);

        LOGGER.info(LOGLINE, Constants.CREATE+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, response, Constants.CREATE_EVENT, HttpStatus.OK);
        

    }

    @Override
    public ServiceResponse<?> delete(String id) {
        LOGGER.info(LOGLINE, Constants.DELETE+ENTITY, Constants.IN);

        // Check if the event exists
        Event entity = validateExistEvent(id);

        // Check if the condominium exists
        this.condominiumService.validateExistCondominium(entity.getCondominium().getId());

        entity.setActive(false);
        eventRepository.save(entity);
        

        LOGGER.info(LOGLINE, Constants.DELETE+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, null, Constants.DELETE_EVENT, HttpStatus.OK);      
    }

    @Override
    public ServiceResponse<?> getItem(String id) {
        LOGGER.info(LOGLINE, Constants.GET+ENTITY, Constants.IN);        

        Event entity = validateExistEvent(id);

        GetEventDto dtoOut = mapperUtil.map(entity, GetEventDto.class);

        LOGGER.info(LOGLINE, Constants.GET+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, dtoOut, Constants.GET_EVENT, HttpStatus.OK);
       
    }

    @Override
    public ServiceResponse<PagedResponse<?>> getAll(int page, int size, String condominiumId) {

        LOGGER.info(LOGLINE, Constants.GETS+ENTITY, Constants.IN);

        // Check if the condominium exists
        this.condominiumService.validateExistCondominium(condominiumId);

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> items = eventRepository.findAllByCondominium_IdAndActiveIsTrue(pageable,condominiumId);

         List<GetEventDto> dtoOut = items.stream().map(item -> {
            GetEventDto dto = mapperUtil.map(item, GetEventDto.class);
            return dto;
        }).collect(Collectors.toList());

        PagedResponse<GetEventDto> pagedResponse = new PagedResponse<>(dtoOut, items.getNumber(), items.getSize(), items.getTotalElements(), items.getTotalPages(), items.isLast());

        LOGGER.info(LOGLINE, Constants.GETS+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, pagedResponse, Constants.GET_EVENTS, HttpStatus.OK);

    }

    

    @Override
    public ServiceResponse<?> update(String id, UpdateEventDto entity) {
        LOGGER.info(LOGLINE, Constants.UPDATE+ENTITY, Constants.IN);

        Event item = validateExistEvent(id);

        Event savedItem = eventRepository.save(item);

        GetEventDto dto = mapperUtil.map(savedItem, GetEventDto.class);

        LOGGER.info(LOGLINE, Constants.UPDATE+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, dto, Constants.UPDATE_EVENT, HttpStatus.OK);
    }
    

    @Override
    public Event validateExistEvent(String id){
        return eventRepository.findByIdAndActiveIsTrue(id)
        .orElseThrow(() ->{
            LOGGER.warn(LOGLINE, ENTITY, Constants.API_EVENT_NOT_FOUND);
            throw new CustomException(Constants.API_EVENT_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });
    }


   
    
}
