package com.core.coffee.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.core.coffee.dto.CreatePaymentDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdatePaymentDto;
import com.core.coffee.entity.Payment;
import com.core.coffee.enums.Status;
import com.core.coffee.exception.CustomException;
import com.core.coffee.repository.PaymentRepository;
import com.core.coffee.service.PaymentService;
import com.core.coffee.util.Constants;
import com.core.coffee.util.MapperUtil;

@Service
public class PaymentServiceImpl extends PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private static final String LOGLINE = PaymentServiceImpl.class.getName() + " - {} - {}";

    private static final String CREATE = "createPayment";
    private static final String UPDATE = "updatePayment";
    private static final String DELETE = "deletePayment";
    private static final String GET = "getPayment";

    private static final String CREATE_PAYMENT = "Payment Created Successful";
    private static final String UPDATE_PAYMENT = "Payment Updated Successful";
    private static final String DELETE_PAYMENT = "Payment Deleted Successful";
    private static final String GET_PAYMENT = "Payment Found Successful";


    private final PaymentRepository paymentRepository;
    private final MapperUtil mapperUtil;

    public PaymentServiceImpl(
        PaymentRepository paymentRepository,
        MapperUtil mapperUtil
        ) {
        this.paymentRepository = paymentRepository;
        this.mapperUtil = mapperUtil;
        
    }




    @Override
    public ServiceResponse<?> create(CreatePaymentDto dto) {
         LOGGER.info(LOGLINE, CREATE, Constants.IN);

        Payment entity = this.mapperUtil.map(dto, Payment.class);

        Payment savedItem = paymentRepository.save(entity);

        LOGGER.info(LOGLINE, CREATE, Constants.OUT);
        return new ServiceResponse<>(Status.OK, savedItem, CREATE_PAYMENT, HttpStatus.OK);

    }

    @Override
    public ServiceResponse<?> delete(String id) {
        LOGGER.info(LOGLINE, DELETE, Constants.IN);

        Payment entity = paymentRepository.findByIdAndActiveIsTrue(id)
            .orElseThrow(() -> {
                LOGGER.warn(LOGLINE, DELETE, Constants.API_PAYMENT_NOT_FOUND);
                throw new CustomException(Constants.API_PAYMENT_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
            });

        paymentRepository.delete(entity);

        LOGGER.info(LOGLINE, DELETE, Constants.OUT);
        return new ServiceResponse<>(Status.OK, DELETE_PAYMENT, DELETE_PAYMENT, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<?> getItem(String id) {
        LOGGER.info(LOGLINE, GET, Constants.IN);

        Payment entity = paymentRepository.findByIdAndActiveIsTrue(id)
            .orElseThrow(() -> {
                LOGGER.warn(LOGLINE, GET, Constants.API_PAYMENT_NOT_FOUND);
                throw new CustomException(Constants.API_PAYMENT_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
            });

        LOGGER.info(LOGLINE, GET, Constants.OUT);
        return new ServiceResponse<>(Status.OK, entity, GET_PAYMENT, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<PagedResponse<?>> getAll(int page, int size) {
        LOGGER.info(LOGLINE, GET, Constants.IN);

        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> items = paymentRepository.findAll(pageable);

        PagedResponse<Payment> pagedResponse = new PagedResponse<>(items);

        LOGGER.info(LOGLINE, GET, Constants.OUT);
        return new ServiceResponse<>(Status.OK, pagedResponse, GET_PAYMENT, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<?> update(String id, UpdatePaymentDto entity) {
        LOGGER.info(LOGLINE, UPDATE, Constants.IN);

        Payment item = paymentRepository.findByIdAndActiveIsTrue(id)
            .orElseThrow(() -> {
                LOGGER.warn(LOGLINE, UPDATE, Constants.API_PAYMENT_NOT_FOUND);
                throw new CustomException(Constants.API_PAYMENT_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
            });

        Payment savedItem = paymentRepository.save(item);

        LOGGER.info(LOGLINE, UPDATE, Constants.OUT);
        return new ServiceResponse<>(Status.OK, savedItem, UPDATE_PAYMENT, HttpStatus.OK);
    }
    // This class is empty. It is used to test the code scanner.
    
}
