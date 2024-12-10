package com.core.coffee.service;



import com.core.coffee.dto.CreateUserDto;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateUserDto;
import com.core.coffee.entity.User;



public abstract class  UserService extends GenericService<CreateUserDto, String> {

    public abstract ServiceResponse<User>  update(String userId,UpdateUserDto updateUserDto);
    public abstract User validateExistUser(String id);

}
