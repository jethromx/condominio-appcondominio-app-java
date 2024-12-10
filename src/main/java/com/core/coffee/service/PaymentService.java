package com.core.coffee.service;

import com.core.coffee.dto.CreatePaymentDto;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdatePaymentDto;

public abstract class  PaymentService extends GenericService<CreatePaymentDto, String> {

    public abstract ServiceResponse<?> update(String id, UpdatePaymentDto entity);
    
    
}
