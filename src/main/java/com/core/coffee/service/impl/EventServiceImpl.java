package com.core.coffee.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.core.coffee.dto.CreateEventDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateEventDto;
import com.core.coffee.entity.Apartment;
import com.core.coffee.entity.Condominium;
import com.core.coffee.entity.Event;
import com.core.coffee.enums.Status;
import com.core.coffee.exception.CustomException;
import com.core.coffee.repository.EventRepository;
import com.core.coffee.service.EventService;
import com.core.coffee.util.Constants;
import com.core.coffee.util.MapperUtil;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);
    private static final String LOGLINE = EventServiceImpl.class.getName() + " - {} - {}";

    private static final String CREATE = "createEvent";
    private static final String UPDATE = "updateEvent";
    private static final String DELETE = "deleteEvent";
    private static final String GET = "getEvent";
    private static final String GETS = "getEvents";

    private static final String CREATE_EVENT = "Event Created Successful";
    private static final String UPDATE_EVENT = "Event Updated Successful";
    private static final String DELETE_EVENT = "Event Deleted Successful";
    private static final String GET_EVENT = "Event Found Successful";
    private static final String GET_EVENTS = "Event Found Successful";

    final EventRepository eventRepository;
    final MapperUtil mapperUtil;

    public EventServiceImpl(EventRepository eventRepository, MapperUtil mapperUtil) {
        this.eventRepository = eventRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public ServiceResponse<?> createEvent(CreateEventDto dto, String condominiumId) {
        LOGGER.info(LOGLINE, CREATE, Constants.IN);

        eventRepository.findByCondominiumAndNameAndActiveIsTrue(condominiumId, dto.getName())
                .ifPresent(e -> {
                    LOGGER.warn(LOGLINE, CREATE, Constants.API_EVENT_ALREADY_EXISTS);
                    throw new CustomException(Constants.API_EVENT_ALREADY_EXISTS, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
                });

        Event entity = this.mapperUtil.map(dto, Event.class);
        Condominium condominium = new Condominium();
        condominium.setId(condominiumId);
        entity.setCondominium(condominium);

        Apartment apartment = new Apartment();
        apartment.setId(dto.getApartment());
        entity.setApartment(apartment);

        Event savedItem = eventRepository.save(entity);

        LOGGER.info(LOGLINE, CREATE, Constants.OUT);
        return new ServiceResponse<>(Status.OK, savedItem, CREATE_EVENT, HttpStatus.OK);
        

    }

    @Override
    public ServiceResponse<?> delete(String id) {
        LOGGER.info(LOGLINE, DELETE, Constants.IN);

        Event entity = eventRepository.findByIdAndActiveIsTrue(id)
                .orElseThrow(() -> {
                    LOGGER.warn(LOGLINE, DELETE, Constants.API_EVENT_NOT_FOUND);
                    throw new CustomException(Constants.API_EVENT_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
                });
        entity.setActive(false);
        eventRepository.save(entity);

        LOGGER.info(LOGLINE, DELETE, Constants.OUT);
        return new ServiceResponse<>(Status.OK, null, DELETE_EVENT, HttpStatus.OK);      
    }

    @Override
    public ServiceResponse<?> getItem(String id) {
        LOGGER.info(LOGLINE, GET, Constants.IN);

        Event entity = eventRepository.findByIdAndActiveIsTrue(id)
                .orElseThrow(() -> {
                    LOGGER.warn(LOGLINE, GET, Constants.API_EVENT_NOT_FOUND);
                    throw new CustomException(Constants.API_EVENT_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
                });

        LOGGER.info(LOGLINE, GET, Constants.OUT);
        return new ServiceResponse<>(Status.OK, entity, GET_EVENT, HttpStatus.OK);
       
    }

    @Override
    public ServiceResponse<PagedResponse<?>> getAll(int page, int size) {
        LOGGER.info(LOGLINE, GETS, Constants.IN);

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> items = eventRepository.findAll(pageable);

        PagedResponse<Event> pagedResponse = new PagedResponse<>(items);

        LOGGER.info(LOGLINE, GETS, Constants.OUT);
        return new ServiceResponse<>(Status.OK, pagedResponse, GET_EVENTS, HttpStatus.OK);
       
    }

    @Override
    public ServiceResponse<?> update(String id, UpdateEventDto entity) {
        LOGGER.info(LOGLINE, UPDATE, Constants.IN);

        Event item = eventRepository.findByIdAndActiveIsTrue(id)
                .orElseThrow(() ->{
                    LOGGER.warn(LOGLINE, UPDATE, Constants.API_EVENT_NOT_FOUND);
                    throw new CustomException(Constants.API_EVENT_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
                });

        Event savedItem = eventRepository.save(item);

        LOGGER.info(LOGLINE, UPDATE, Constants.OUT);
        return new ServiceResponse<>(Status.OK, savedItem, UPDATE_EVENT, HttpStatus.OK);
    }
    // This class is empty. It is used to test the code scanner.


    @Override
    public ServiceResponse<?> create(CreateEventDto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }
    
}
