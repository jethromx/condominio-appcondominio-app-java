package com.core.coffee.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.core.coffee.dto.CreateUserDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateUserDto;
import com.core.coffee.entity.User;
import com.core.coffee.enums.Status;
import com.core.coffee.exception.CustomException;
import com.core.coffee.repository.UserRepository;
import com.core.coffee.service.UserService;
import com.core.coffee.util.Constants;
import com.core.coffee.util.MapperUtil;

@Service
public class UserServiceImpl extends UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String LOGLINE = UserServiceImpl.class.getName() + " - {} - {}";

 
    private static final String ENTITY = "User";


    private final UserRepository userRepository;
    private MapperUtil mapperUtil;

    
    public UserServiceImpl(
        UserRepository userRepository,
        MapperUtil mapperUtil
        ) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public ServiceResponse<User> create(CreateUserDto createUserDto) {
        LOGGER.info(LOGLINE, Constants.CREATE+ENTITY, Constants.IN);
        
        User user = this.mapperUtil.map(createUserDto, User.class);

        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            LOGGER.warn(LOGLINE, Constants.CREATE+ENTITY, Constants.API_USER_ALREADY_EXISTS);
            throw  new CustomException(Constants.API_USER_ALREADY_EXISTS, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        user = this.userRepository.save(user);

        LOGGER.info(LOGLINE, Constants.CREATE+ENTITY, Constants.OUT);
        return new ServiceResponse<User>(Status.OK, user, Constants.CREATE_USER, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<User> update(String userId,UpdateUserDto updateUserDto) {
        LOGGER.info(LOGLINE, Constants.UPDATE+ENTITY, Constants.IN);
        
        User user = this.mapperUtil.map(updateUserDto, User.class);

        userRepository.findById(userId).orElseThrow(() -> {
            LOGGER.warn(LOGLINE, Constants.UPDATE+ENTITY, Constants.API_USER_NOT_FOUND);
            throw  new CustomException(Constants.API_USER_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        user = this.userRepository.save(user);
        LOGGER.info(LOGLINE, Constants.UPDATE+ENTITY, Constants.OUT);

        return new ServiceResponse<User>(Status.OK, user, Constants.UPDATE_USER, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<User> delete(String userId) {
        LOGGER.info(LOGLINE, Constants.DELETE+ENTITY, Constants.IN);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            LOGGER.warn(LOGLINE, Constants.DELETE+ENTITY, Constants.API_USER_NOT_FOUND);
            throw  new CustomException(Constants.API_USER_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });        

        userRepository.deleteById(userId);        
        LOGGER.info(LOGLINE, Constants.DELETE+ENTITY, Constants.OUT);

        return new ServiceResponse<User>(Status.OK, user, Constants.DELETE_USER, HttpStatus.CREATED);
        
    }

    @Override
    public ServiceResponse<?> getItem(String userId) {
        LOGGER.info(LOGLINE, Constants.GET+ENTITY, Constants.IN);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            LOGGER.warn(LOGLINE, Constants.GET+ENTITY, Constants.API_USER_NOT_FOUND);
            throw  new CustomException(Constants.API_USER_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        LOGGER.info(LOGLINE, Constants.GET+ENTITY, Constants.OUT);
        return new ServiceResponse<User>(Status.OK, user, Constants.GET_USER, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<PagedResponse<?>> getAll(int page, int size) {
        LOGGER.info(LOGLINE, Constants.GETS+ENTITY, Constants.IN);

        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);
        PagedResponse<User> pagedResponse = new PagedResponse<>(usersPage);

        LOGGER.info(LOGLINE, Constants.GETS+ENTITY, Constants.OUT);
        return new ServiceResponse<>(Status.OK, pagedResponse, Constants.GET_USERS, HttpStatus.OK);
    }

    @Override
    public User validateExistUser(String id){
        return  userRepository.findByIdAndActiveIsTrue(id).orElseThrow( ()->{
            LOGGER.warn(LOGLINE, ENTITY, Constants.API_USER_NOT_FOUND);
            throw new CustomException(Constants.API_USER_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });
    }

}
