package com.core.coffee.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.Cacheable;
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
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String LOGLINE = UserServiceImpl.class.getName() + " - {} - {}";

    private static final String CREATE = "createUser";
    private static final String UPDATE = "updateUser";
    private static final String DELETE = "deleteUser";
    private static final String GET = "getUser";
    private static final String GETS = "getUsers";

    private static final String CREATE_USER = "User Created Successful";
    private static final String UPDATE_USER = "User Updated Successful";
    private static final String DELETE_USER = "User Deleted Successful";
    private static final String GET_USER = "User Found Successful";
    private static final String GET_USERS = "Users Found Successful";




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
        LOGGER.info(LOGLINE, CREATE, Constants.IN);
        
        User user = this.mapperUtil.map(createUserDto, User.class);

        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            LOGGER.warn(LOGLINE, CREATE, Constants.API_USER_ALREADY_EXISTS);
            throw  new CustomException(Constants.API_USER_ALREADY_EXISTS, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        user = this.userRepository.save(user);

        LOGGER.info(LOGLINE, CREATE, Constants.OUT);
        return new ServiceResponse<User>(Status.OK, user, CREATE_USER, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<User> update(String userId,UpdateUserDto updateUserDto) {
        LOGGER.info(LOGLINE, UPDATE, Constants.IN);
        
        User user = this.mapperUtil.map(updateUserDto, User.class);

        userRepository.findById(userId).orElseThrow(() -> {
            LOGGER.warn(LOGLINE, UPDATE, Constants.API_USER_NOT_FOUND);
            throw  new CustomException(Constants.API_USER_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        user = this.userRepository.save(user);
        LOGGER.info(LOGLINE, UPDATE, Constants.OUT);

        return new ServiceResponse<User>(Status.OK, user, UPDATE_USER, HttpStatus.OK);
    }

    @Override
    public ServiceResponse<User> delete(String userId) {
        LOGGER.info(LOGLINE, DELETE, Constants.IN);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            LOGGER.warn(LOGLINE, DELETE, Constants.API_USER_NOT_FOUND);
            throw  new CustomException(Constants.API_USER_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });        

        userRepository.deleteById(userId);        
        LOGGER.info(LOGLINE, DELETE, Constants.OUT);

        return new ServiceResponse<User>(Status.OK, user, DELETE_USER, HttpStatus.CREATED);
        
    }

    @Override
    @Cacheable(value = "usersCache", key = "#userId")
    public ServiceResponse<?> getItem(String userId) {
        LOGGER.info(LOGLINE, GET, Constants.IN);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            LOGGER.warn(LOGLINE, GET, Constants.API_USER_NOT_FOUND);
            throw  new CustomException(Constants.API_USER_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });

        LOGGER.info(LOGLINE, GET, Constants.OUT);
        return new ServiceResponse<User>(Status.OK, user, GET_USER, HttpStatus.OK);
    }

    @Override
    @Cacheable(value = "usersCache")
    public ServiceResponse<PagedResponse<?>> getAll(int page, int size) {
        LOGGER.info(LOGLINE, GETS, Constants.IN);

        
        
        

        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);
        PagedResponse<User> pagedResponse = new PagedResponse<>(usersPage);

        LOGGER.info(LOGLINE, GETS, Constants.OUT);
        return new ServiceResponse<>(Status.OK, pagedResponse, GET_USERS, HttpStatus.OK);
    }

}
