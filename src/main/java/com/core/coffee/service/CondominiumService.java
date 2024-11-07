package com.core.coffee.service;


import com.core.coffee.dto.CreateCondominiumDto;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateCondominiumDto;
import com.core.coffee.entity.Condominium;

public interface CondominiumService extends GenericService<CreateCondominiumDto, String>  {

    public ServiceResponse<Condominium> update(String condominiumId, UpdateCondominiumDto condominium);
    
    
    
}
