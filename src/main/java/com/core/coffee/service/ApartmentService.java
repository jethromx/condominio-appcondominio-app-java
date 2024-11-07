package com.core.coffee.service;

import com.core.coffee.dto.CreateApartmentDto;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateAparmentDto;
import com.core.coffee.entity.Apartment;

public interface ApartmentService extends GenericService<CreateApartmentDto, String> {

    public ServiceResponse<Apartment> create(CreateApartmentDto entity,String condominuimId);
    public ServiceResponse<Apartment> update(String apartmentId, UpdateAparmentDto apartment);


}
