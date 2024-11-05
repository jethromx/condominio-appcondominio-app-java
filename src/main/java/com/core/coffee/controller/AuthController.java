package com.core.coffee.controller;

import org.springframework.web.bind.annotation.RestController;

import com.core.coffee.dto.TokenResponse;
import com.core.coffee.exception.CustomException;
import com.core.coffee.service.AuthService;
import com.core.coffee.util.Constants;
import com.core.coffee.util.Validate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.coffee.dto.ErrorDto;
import com.core.coffee.dto.LoginDto;
import com.core.coffee.dto.RegisterDto;


@RestController
@RequestMapping("/${api.version}/auth")
@ApiResponses( value = {
    @ApiResponse(responseCode = "400" , description = Constants.API_INVALID_REQUEST,    
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,  schema = @Schema(implementation = ErrorDto.class)),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)       
    }),
    @ApiResponse(responseCode = "403", description = Constants.API_ACCESS_DENIED,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorDto.class)),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)        
    }),
    @ApiResponse(responseCode = "404", description = Constants.API_RESOURCE_NOT_FOUND,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorDto.class)),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)        
    }),
    @ApiResponse(responseCode = "500", description = Constants.GENERIC_ERROR,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)       
    } )
})
@Tag(name = Constants.API_AUTH, description = Constants.API_AUTH_DESCRIPTION)
@RequiredArgsConstructor
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private static final String LOGLINE = AuthController.class.getName() +" - {} - {}";  


    private static final String REGISTER = "REGISTER";
    private static final String LOGIN = "LOGIN";
    private static final String REFRESH = "REFRESH";

    private final AuthService service;

    
    /*
     * Register User
     * @param registerDto
     * @return tokenResponse
     * @throws CustomException
     * 
     */

    @PostMapping(value= "/register",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)  
    @Operation(summary = Constants.API_REGISTER, description = Constants.API_REGISTER_DESCRIPTION, 
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = Constants.API_REGISTER_DESCRIPTION,                    
                    required = true,
                    content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = RegisterDto.class))                    
                    )
                )
                     
     @ApiResponse(responseCode = "200", description = Constants.API_REGISTER_SUCCESS,
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = TokenResponse.class)
            ),
        headers={ 
            @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
            @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)       
    })    
    public ResponseEntity<TokenResponse> registerUser(@Valid @RequestBody RegisterDto request,BindingResult bindingResult) {
        LOGGER.info(LOGLINE, REGISTER, Constants.IN);

        Validate.ValidateInput(bindingResult,REGISTER); 

        final TokenResponse response =  service.register(request);

        LOGGER.info(LOGLINE, REGISTER, Constants.OUT);
        return ResponseEntity.ok(response);
    }



    /*
     * Login User
     * @param loginDto
     * @returnt tokenResponse
     * @throws CustomException
     * 
     */
    @PostMapping(value ="/login",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)  
    @Operation(summary = Constants.API_LOGIN, description = Constants.API_LOGIN_DESCRIPTION)       
    @ApiResponse(responseCode = "200", description = Constants.API_LOGIN_SUCCESS,
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = TokenResponse.class)
            ),
        headers={ 
            @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
            @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
    })    
    public ResponseEntity<TokenResponse> loginUser(@Valid @RequestBody LoginDto resquest,BindingResult bindingResult) {
        LOGGER.info(LOGLINE, LOGIN, Constants.IN);

        Validate.ValidateInput(bindingResult,LOGIN);

        final TokenResponse response = service.login(resquest);

        LOGGER.info(LOGLINE, LOGIN, Constants.OUT);
        return ResponseEntity.ok(response);
    }

   


    /*
     * Refresh Token
     * @param headerAutorization
     * @return tokenResponse
     * @throws CustomException
     * 
     */
    @PostMapping(value="/refresh", produces = MediaType.APPLICATION_JSON_VALUE)    
    @Operation(summary = Constants.API_REFRESH_TOKEN, description = Constants.API_REFRESH_TOKEN_DESCRIPTION)       
     @ApiResponse(responseCode = "200", description = Constants.API_REFRESH_TOKEN_SUCCESS,
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = TokenResponse.class)
            ),
        headers={ 
            @Header(name = "Content-Type: "+ MediaType.APPLICATION_JSON_VALUE),
            @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)       
    })  
    public ResponseEntity<TokenResponse> refreshToken(@RequestHeader(value= HttpHeaders.AUTHORIZATION, required = true )  String headerAutorization) {
        LOGGER.info(LOGLINE, REFRESH, Constants.IN);

        LOGGER.info(LOGLINE, headerAutorization, Constants.IN);

        
        if (headerAutorization == null || headerAutorization.isEmpty() || !headerAutorization.startsWith("Bearer ")) {
            LOGGER.error(LOGLINE, headerAutorization, Constants.ERROR);
            throw new CustomException(Constants.API_TOKEN_INVALID, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        }


        final TokenResponse response = service.refresh(headerAutorization);
        LOGGER.info(LOGLINE, REFRESH, Constants.OUT);
        return ResponseEntity.ok(response);
    }

  

    
}
