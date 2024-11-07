package com.core.coffee.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.core.coffee.dto.CreatePaymentDto;
import com.core.coffee.dto.ErrorDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdatePaymentDto;
import com.core.coffee.service.PaymentService;
import com.core.coffee.util.Constants;
import com.core.coffee.util.HandleResponseUtil;
import com.core.coffee.util.Validate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/${api.version}/payments")
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
    @ApiResponse(responseCode = "404", description = Constants.API_PAYMENT_NOT_FOUND,
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
@Tag(name = Constants.API_PAYMENT, description = Constants.API_PAYMENT_DESCRIPTION)
public class PaymentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
    private static final String LOGLINE = PaymentController.class.getName() + " - {} - {}";

    private PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }


    @PostMapping( value= "" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_CREATE_APARTMENT, description = Constants.API_CREATE_APARTMENT_DESCRIPTION)
    @ApiResponse(responseCode = "201", description = Constants.API_CREATE_APARTMENT,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+ MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> create(@Valid @RequestBody CreatePaymentDto dto, BindingResult bindingResult) {
        LOGGER.info(LOGLINE, Constants.METHOD_CREATE, Constants.IN);

        Validate.ValidateInput(bindingResult, Constants.METHOD_CREATE);
        
        ServiceResponse<?> response = service.create(dto);
        return HandleResponseUtil.handle(response, Constants.METHOD_CREATE);
    }



    @PutMapping( value= "/{idCondominium}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_UPDATE_CONDOMINIUM, description = Constants.API_UPDATE_CONDOMINIUM_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_UPDATE_CONDOMINIUM,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody UpdatePaymentDto dto,
            BindingResult bindingResult) {
        LOGGER.info(LOGLINE, Constants.METHOD_UPDATE, Constants.IN);

        Validate.ValidateInput(bindingResult, Constants.METHOD_UPDATE);

        ServiceResponse<?> response = service.update(id, dto);
        return HandleResponseUtil.handle(response, Constants.METHOD_UPDATE);
    }


    @DeleteMapping( value= "/{idCondominium}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_DELETE_CONDOMINIUM, description = Constants.API_DELETE_CONDOMINIUM_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_DELETE_CONDOMINIUM,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> delete(@PathVariable String id) {
        LOGGER.info(LOGLINE, Constants.METHOD_DELETE, Constants.IN);

        Validate.ValidateInputId(id, Constants.METHOD_DELETE);

        ServiceResponse<?> response= service.delete(id);
        return HandleResponseUtil.handle(response, Constants.METHOD_DELETE);
    }

    @GetMapping( value= "/{idCondominium}" ,  produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_GET_CONDOMINIUM, description = Constants.API_GET_CONDOMINIUM_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_GET_CONDOMINIUM,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> getItem(@PathVariable String id) {
        LOGGER.info(LOGLINE, Constants.METHOD_GET, Constants.IN);

        Validate.ValidateInputId(id, Constants.METHOD_GET);

        ServiceResponse<?> response= service.getItem(id);
        return HandleResponseUtil.handle(response, Constants.METHOD_GET);
    }


    @GetMapping(  produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_GET_CONDOMINIUMS, description = Constants.API_GET_CONDOMINIUMS_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_GET_CONDOMINIUMS,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+ MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)       
    })
     public ResponseEntity<?> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size)
    {
        LOGGER.info(LOGLINE, Constants.METHOD_GET, Constants.IN);

        ServiceResponse<PagedResponse<?>> response = service.getAll(page, size);
        return HandleResponseUtil.handle(response, Constants.METHOD_GET);
    }
    



    
    
}
