package com.core.coffee.service;

import com.core.coffee.dto.CreateApartmentDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateApartmentDto;
import com.core.coffee.entity.Apartment;

public abstract class ApartmentService extends GenericService<CreateApartmentDto, String> {

    
    public abstract ServiceResponse<?> create(CreateApartmentDto entity,String condominuimId);
    public abstract ServiceResponse<?> update(String apartmentId, UpdateApartmentDto apartment);
    public abstract ServiceResponse<PagedResponse<?>> getAll(int page, int size, String condominiumId);
    public abstract Apartment validateExistApartment(String id);

    @Override
    public ServiceResponse<?> create(CreateApartmentDto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }


    @Override
    public ServiceResponse<PagedResponse<?>> getAll(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }


}
