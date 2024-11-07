package com.core.coffee.service;



import com.core.coffee.dto.CreateUserDto;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateUserDto;
import com.core.coffee.entity.User;



public interface UserService extends GenericService<CreateUserDto, String> {

    public ServiceResponse<User>  update(String userId,UpdateUserDto updateUserDto);

}
