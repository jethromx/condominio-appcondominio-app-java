package com.core.coffee.service;


import com.core.coffee.dto.CreateCondominiumDto;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateCondominiumDto;
import com.core.coffee.entity.Condominium;

public abstract class CondominiumService extends GenericService<CreateCondominiumDto, String>  {

    public abstract  ServiceResponse<?> update(String condominiumId, UpdateCondominiumDto condominium);
    public abstract Condominium validateExistCondominium(String condominiumId);
    
    
    
}
