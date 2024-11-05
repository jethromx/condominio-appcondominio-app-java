package com.core.coffee.service;



import com.core.coffee.dto.CreateUserDto;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateUserDto;
import com.core.coffee.entity.User;



import org.springframework.data.domain.Page;


public interface UserService {

    public ServiceResponse<User>  createUser(CreateUserDto createUserDto);
    
        public ServiceResponse<User>  updateUser(String userId,UpdateUserDto updateUserDto);
    
        public ServiceResponse<User>  deleteUser(String userId);
    
        public ServiceResponse<User>  getUser(String userId);
    
        public ServiceResponse<Page<User>> getUsers(int page, int size);


}
