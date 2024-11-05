package com.core.coffee.service;

import com.core.coffee.dto.LoginDto;
import com.core.coffee.dto.RegisterDto;
import com.core.coffee.dto.TokenResponse;

public interface AuthService {
    public TokenResponse register(RegisterDto request);

    public TokenResponse login(LoginDto resquest);

    public TokenResponse refresh(String token);
    
}
