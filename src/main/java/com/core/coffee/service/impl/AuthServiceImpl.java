package com.core.coffee.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.core.coffee.dto.LoginDto;
import com.core.coffee.dto.RegisterDto;
import com.core.coffee.dto.TokenResponse;
import com.core.coffee.entity.Token;
import com.core.coffee.entity.User;
import com.core.coffee.enums.TokenType;
import com.core.coffee.exception.CustomException;
import com.core.coffee.repository.TokenRepository;
import com.core.coffee.repository.UserRepository;
import com.core.coffee.service.AuthService;
import com.core.coffee.util.Constants;
import com.core.coffee.util.JwtUtil;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    private static final String LOGLINE = AuthServiceImpl.class.getName() + " - {} - {}";

    private static final String LOGIN = "login";
    private static final String REGISTER = "register";
    private static final String REVOKE_ALL_USER = "revokeAlluserTOkens";
    private static final String REFRESH = "refresh";
    private static final String TOKEN_ENTITY ="Building Token entity";
    private static final String FOUNDING_USER ="Founding if exist user";
    private static final String FIND_BY_EMAIL = "findByEmail";
    private static final String SAVING_USER = "Saving User";
    private static final String SAVING_TOKEN = "Saving token";

    private static final String ROL_DEFAULT= "USER";


    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final AuthenticationManager autenticationManager;  

    @Autowired
    private ModelMapper modelMapper;


    public TokenResponse register(RegisterDto request) {
        LOGGER.info(LOGLINE, REGISTER, Constants.IN);               


        LOGGER.debug(LOGLINE, FOUNDING_USER, Constants.IN);
         userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            LOGGER.warn(LOGLINE, REGISTER, Constants.API_USER_ALREADY_EXISTS);
            throw  new CustomException(Constants.API_USER_ALREADY_EXISTS, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        });
        LOGGER.debug(LOGLINE, FOUNDING_USER, Constants.OUT);

        
        String passwordEncoded = passwordEncoder.encode(request.getPassword());

        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User user = this.modelMapper.map(request, User.class);
        user.setPassword(passwordEncoded);
        user.setActive(true);
        user.setRoles(List.of(ROL_DEFAULT));

        
        String jwtToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

       Token token = createEntityToken(user, jwtToken, refreshToken);


        LOGGER.debug(LOGLINE, SAVING_USER, Constants.IN);
        user = this.userRepository.save(user);
        LOGGER.debug(LOGLINE, SAVING_USER, Constants.OUT);

        LOGGER.debug(LOGLINE, SAVING_TOKEN, Constants.IN);
        tokenRepository.save(token);
        LOGGER.debug(LOGLINE, SAVING_TOKEN, Constants.OUT);


        LOGGER.debug(LOGLINE, REGISTER, Constants.OUT);
        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse login(LoginDto resquest) {
        LOGGER.info(LOGLINE, LOGIN, Constants.IN);

        LOGGER.debug(LOGLINE, FIND_BY_EMAIL, Constants.IN);
        Optional<User> userOp = userRepository.findByEmail(resquest.getEmail());

        if(userOp.isEmpty()) {
            LOGGER.warn(LOGLINE, FIND_BY_EMAIL, Constants.API_USER_NOT_FOUND);
            throw  new CustomException(Constants.API_USER_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        }

        LOGGER.debug(LOGLINE, FIND_BY_EMAIL, Constants.OUT);
        
        autenticationManager.authenticate( new UsernamePasswordAuthenticationToken(resquest.getEmail(), resquest.getPassword() ) );
        
        String jwtToken = jwtUtil.generateToken(userOp.get());
        String refreshToken = jwtUtil.generateRefreshToken(userOp.get());

        Token token = createEntityToken(userOp.get(), jwtToken, refreshToken);

        revokeAlluserTOkens(userOp.get());
        tokenRepository.save(token); 
        //saveUserToken(user, refres);

        LOGGER.info(LOGLINE, LOGIN, Constants.OUT);
        return new TokenResponse(jwtToken, refreshToken);
    }

    

    public TokenResponse refresh(String authHeader) {
        LOGGER.info(LOGLINE, REFRESH, Constants.IN);

        if(authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {
            throw new CustomException(Constants.API_TOKEN_INVALID, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());           
        }

        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtUtil.extractEmailUser(refreshToken);

        if(userEmail == null) {
            throw new CustomException(Constants.API_TOKEN_REFRESH_INVALID, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());           
        }

        final User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(Constants.API_USER_NOT_FOUND, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue()));

        if(!jwtUtil.isTokenValid(refreshToken, user)) {
            throw new CustomException(Constants.API_TOKEN_REFRESH_INVALID, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());           
        }
        final String accessToken = jwtUtil.generateToken(user);
        revokeAlluserTOkens(user);
        //saveUserToken(user, jwtToken);

        LOGGER.info(LOGLINE, REFRESH, Constants.OUT);
        return new TokenResponse(accessToken, refreshToken);


    }

    private void revokeAlluserTOkens(final User user) {
        LOGGER.info(LOGLINE, REVOKE_ALL_USER, Constants.IN);
        final List<Token> validUserTokens = tokenRepository.findAllValidIsFalseOrRevokedIsFalseByUserId(user.getId());
        if(!validUserTokens.isEmpty()) {
            for(Token token : validUserTokens) {
                LOGGER.debug(LOGLINE,"Updating tokens ID "+ token.getId(), Constants.IN);
                token.setRevoked(true);
                token.setRevoked(true);
                tokenRepository.save(token);
            }
            LOGGER.info(LOGLINE, REVOKE_ALL_USER, Constants.IN);
            tokenRepository.saveAll(validUserTokens);
        }
        LOGGER.info(LOGLINE, REVOKE_ALL_USER, Constants.OUT);
        
    }

    private Token createEntityToken(User user, String jwtToken, String refresToken) {
        LOGGER.debug(LOGLINE,TOKEN_ENTITY , Constants.IN);
        Token token = Token.builder()
        .user(user)
        .token(jwtToken)
        .refreshToken(refresToken)
        .expirated(false)
        .revoked(false)
        .tokenType(TokenType.BEARER)
        .build();
        LOGGER.debug(LOGLINE,TOKEN_ENTITY , Constants.OUT);
        return token;
    }

      
    
}
