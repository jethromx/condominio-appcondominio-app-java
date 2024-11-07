package com.core.coffee.service;


import com.core.coffee.dto.CreateEventDto;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateEventDto;

public interface EventService extends GenericService<CreateEventDto, String> {

    public ServiceResponse<?> update(String id, UpdateEventDto entity);
    public ServiceResponse<?> createEvent(CreateEventDto dto ,String condominiumId);
    
    
}
