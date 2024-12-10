package com.core.coffee.service;


import com.core.coffee.dto.CreateEventDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateEventDto;
import com.core.coffee.entity.Event;

public abstract class  EventService extends GenericService<CreateEventDto, String> {

    public abstract ServiceResponse<?> update(String id, UpdateEventDto entity);
    public abstract ServiceResponse<?> createEvent(CreateEventDto dto ,String condominiumId);
    public abstract ServiceResponse<PagedResponse<?>> getAll(int page, int size, String condominiumId) ;
    public abstract Event validateExistEvent(String id);

    @Override
    public ServiceResponse<?> create(CreateEventDto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public ServiceResponse<PagedResponse<?>> getAll(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }
    
    
}
