package com.core.coffee.service;

import com.core.coffee.dto.CreatePaymentDto;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdatePaymentDto;

public interface PaymentService extends GenericService<CreatePaymentDto, String> {

    public ServiceResponse<?> update(String id, UpdatePaymentDto entity);
    
    
}
