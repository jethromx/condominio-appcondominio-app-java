package com.core.coffee.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.core.coffee.dto.CreateUserDto;
import com.core.coffee.dto.ErrorDto;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateUserDto;
import com.core.coffee.entity.User;
import com.core.coffee.service.UserService;
import com.core.coffee.util.Constants;
import com.core.coffee.util.HandleResponseUtil;
import com.core.coffee.util.Validate;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;




@RestController
@RequestMapping("/${api.version}/users")
@ApiResponses( value = {
    @ApiResponse(responseCode = "400" , description = Constants.API_INVALID_REQUEST,    
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)),
    headers={ 
        @Header(name = "Content-Type: "+ MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)       
    }),
    @ApiResponse(responseCode = "403", description = Constants.API_ACCESS_DENIED,
    content = @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)        
    }),
    @ApiResponse(responseCode = "404", description = Constants.API_USER_NOT_FOUND,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)),
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
@Tag(name = Constants.API_USERS, description = Constants.API_DESCRIPTION)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final String LOGLINE = UserController.class.getName() +" - {} - {}";  

    private UserService userService;


    public UserController( UserService userService) {      
        this.userService = userService;
    }
        

        /**
         * Método para crear un nuevo usuario.
         * 
         * @param user DTO con la información del usuario a crear.
         * @param bindingResult Resultado de la validación del DTO.
         * @return ResponseEntity con el resultado de la operación.
         */
        @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = Constants.API_CREATE_USER, description = Constants.API_CREATE_USER_DESCRIPTION)       
        @ApiResponse(responseCode = "200", description = Constants.API_CREATE_USER,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceResponse.class)
                            ),
                    headers={ 
                            @Header(name = "Content-Type: "+ MediaType.APPLICATION_JSON_VALUE),
                            @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)       
                        })    
        public ResponseEntity<?> createUser(@Valid  @RequestBody CreateUserDto user,BindingResult bindingResult) {
            LOGGER.info(LOGLINE, Constants.METHOD_CREATE, Constants.IN);
    
            Validate.ValidateInput(bindingResult, Constants.METHOD_CREATE);           
    
            ServiceResponse<User> response= userService.createUser(user);
            return HandleResponseUtil.handle(response, Constants.METHOD_CREATE);
        }


        /**
         * Método para actualizar un usuario existente.
         * 
         * @param id Identificador del usuario a actualizar.
         * @param user DTO con la información del usuario a actualizar.
         * @param bindingResult Resultado de la validación del DTO.
         * @return ResponseEntity con el resultado de la operación.
         */
        @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation( summary = Constants.API_UPDATE_USER, description = Constants.API_UPDATE_USER_DESCRIPTION,
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                description = Constants.API_UPDATE_USER_DESCRIPTION,                    
                                required = true,
                                content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UpdateUserDto.class))                    
                                ))
        @ApiResponse(responseCode = "200", description = Constants.API_UPDATE_USER,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceResponse.class)
                            ),
                        headers={ 
                            @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
                            @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
                        })
        public ResponseEntity<?> updateUser(@PathVariable String id, @Valid  @RequestBody UpdateUserDto user,BindingResult bindingResult) {
            LOGGER.info(LOGLINE, Constants.METHOD_UPDATE, Constants.IN);
    
            Validate.ValidateInput(bindingResult, Constants.METHOD_UPDATE);
    
            ServiceResponse<User> response= userService.updateUser( id,user);
            return HandleResponseUtil.handle(response, Constants.METHOD_UPDATE);
        }


        /**
         * Método para eliminar un usuario existente.
         * 
         * @param id Identificador del usuario a eliminar.
         * @return ResponseEntity con el resultado de la operación.
         */
        @DeleteMapping(value="/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = Constants.API_DELETE_USER, description = Constants.API_DELETE_USER_DESCRIPTION)
        @ApiResponse(responseCode = "204", description = Constants.API_DELETE_USER,
                    content = @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ServiceResponse.class)
                                        ),
                    headers={ 
                        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
                        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
                    })
        public ResponseEntity<?> deleteUser(@PathVariable String id) {
            LOGGER.info(LOGLINE, Constants.METHOD_DELETE, Constants.IN);

            Validate.ValidateInputId(id, Constants.METHOD_DELETE);
    
            ServiceResponse<User> response= userService.deleteUser(id);
            return HandleResponseUtil.handle(response, Constants.METHOD_DELETE);
        }


        /**
         * Método para obtener un usuario por su identificador.
         * 
         * @param id Identificador del usuario a obtener.
         * @return ResponseEntity con el resultado de la operación.
         */
        @GetMapping( value= "/{id}" ,  produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = Constants.API_GET_USER, description = Constants.API_GET_USER_DESCRIPTION)
        @ApiResponse(responseCode = "200", description = Constants.API_GET_USER,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
        headers={ 
            @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
            @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
        })
        public ResponseEntity<?> getUser(@PathVariable String id) {
            LOGGER.info(LOGLINE, Constants.METHOD_GET, Constants.IN);

            Validate.ValidateInputId(id, Constants.METHOD_GET);
    
            ServiceResponse<User> response= userService.getUser(id);
            return HandleResponseUtil.handle(response, Constants.METHOD_GET);
        }


        /**
         * Método para obtener todos los usuarios.
         * 
         * @return ResponseEntity con el resultado de la operación.
         */
        @GetMapping(  produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(summary = Constants.API_GET_USERS, description = Constants.API_GET_USERS_DESCRIPTION)
        @ApiResponse(responseCode = "200", description = Constants.API_GET_USERS,
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
        headers={ 
            @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
            @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
        })
        public ResponseEntity<?> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
        {
            LOGGER.info(LOGLINE, Constants.METHOD_GET, Constants.IN);
    
            ServiceResponse<Page<User>> response = userService.getUsers(page, size);
            return HandleResponseUtil.handle(response, Constants.METHOD_GET);
        }


       

    
    




    
}
