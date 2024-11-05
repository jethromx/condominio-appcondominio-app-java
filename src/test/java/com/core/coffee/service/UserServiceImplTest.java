package com.core.coffee.service;


import com.core.coffee.dto.CreateUserDto;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.entity.User;
import com.core.coffee.repository.UserRepository;
import com.core.coffee.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private CreateUserDto userDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setName("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setActive(true);

        userDto = new CreateUserDto();
        
        userDto.setName("John");
        userDto.setLastname("Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("password");
        
    }
/*
    @Test
    void testGetUserById_UserExists() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));      

        ServiceResponse<User> result = userService.getUser( "1");
        assertEquals(userDto.getEmail(), result.getResponseObject().getEmail());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUser("1"));
    }
    
    @Test
    void testCreateUser_Success() {
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(userDto, User.class)).thenReturn(user);

        ServiceResponse<User> result = userService.createUser(userDto);
        assertEquals(userDto.getEmail(), result.getResponseObject().getEmail());
    }

    
    @Test
    void testUpdateUser_Success() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(userDto, User.class)).thenReturn(user);

        ServiceResponse<User> result = userService.updateUser(userDto);
        assertEquals(userDto.getEmail(), result.getResponseObject().getEmail());
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        ServiceResponse<User> result = userService.deleteUser("1");
        assertEquals(user.getEmail(), result.getResponseObject().getEmail());
    }*/

   
}
