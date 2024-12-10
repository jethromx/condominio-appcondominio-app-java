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

import com.core.coffee.dto.CreateApartmentDto;
import com.core.coffee.dto.CreateCondominiumDto;
import com.core.coffee.dto.ErrorDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateApartmentDto;
import com.core.coffee.dto.UpdateCondominiumDto;
import com.core.coffee.service.ApartmentService;
import com.core.coffee.service.CondominiumService;
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
@RequestMapping("/${api.version}/condominium")
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
    @ApiResponse(responseCode = "404", description = Constants.API_CONDOMINIUM_NOT_FOUND,
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
@Tag(name = Constants.API_CONDOMINIUM, description = Constants.API_CONDOMINIUM_DESCRIPTION)
class CondominiumController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CondominiumController.class);
    private static final String LOGLINE = CondominiumController.class.getName() +" - {} - {}";  

    private static final String CREATE_APARTMENT = "createApartment";
    private static final String UPDATE_APARTMENT = "updateApartment";
    private static final String DELETE_APARTMENT = "deleteApartment";
    private static final String GET_APARTMENT = "getApartment";
    private static final String GET_APARTMENTS = "getApartments";

    private final CondominiumService condominiumService;
    private final ApartmentService apartmentService;
   


    public CondominiumController( CondominiumService condominiumService, ApartmentService apartmentService) {
        this.condominiumService = condominiumService;
        this.apartmentService = apartmentService;
    }

    
    /*
     * 
     * 
     * 
     */
    @GetMapping(  produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_GET_CONDOMINIUMS, description = Constants.API_GET_CONDOMINIUMS_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_GET_CONDOMINIUMS,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+ MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?>  getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) 
        {
            LOGGER.info(LOGLINE, Constants.METHOD_LIST ,Constants.IN);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return ResponseEntity.status(500).build();
            }

            ServiceResponse<PagedResponse<?>> response = condominiumService.getAll(page, size);
            return HandleResponseUtil.handle(response,Constants.METHOD_LIST);
       
    }


    /*
     * Condominium
     */
    @GetMapping( value= "/{idCondominium}" ,  produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_GET_CONDOMINIUM, description = Constants.API_GET_CONDOMINIUM_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_GET_CONDOMINIUM,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> getById(@PathVariable("idCondominium") String id) {
         LOGGER.info(LOGLINE, Constants.METHOD_GET, Constants.IN);

        Validate.ValidateInputId(id, Constants.METHOD_GET);

        ServiceResponse<?> response= condominiumService.getItem(id);
        return HandleResponseUtil.handle(response, Constants.METHOD_GET);
    }


    /*
     * Condominium
     */
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_CREATE_CONDOMINIUM, description = Constants.API_CREATE_CONDOMINIUM_DESCRIPTION)
    @ApiResponse(responseCode = "201", description = Constants.API_CREATE_CONDOMINIUM,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> create(@Valid @RequestBody CreateCondominiumDto dto,BindingResult bindingResult) {
        LOGGER.info(LOGLINE, Constants.METHOD_CREATE, Constants.IN);

        Validate.ValidateInput(bindingResult, Constants.METHOD_CREATE);

        ServiceResponse<?> response = condominiumService.create(dto);
        return HandleResponseUtil.handle(response, Constants.METHOD_CREATE);        
    }

    
    /*
     * Condominium
     */
    @PutMapping( value= "/{idCondominium}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_UPDATE_CONDOMINIUM, description = Constants.API_UPDATE_CONDOMINIUM_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_UPDATE_CONDOMINIUM,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> update(@PathVariable("idCondominium") String id, @Valid @RequestBody UpdateCondominiumDto dto,BindingResult bindingResult) {
        LOGGER.info(LOGLINE, UPDATE_APARTMENT, Constants.IN);
    
        Validate.ValidateInput(bindingResult, Constants.METHOD_UPDATE);
    
        ServiceResponse<?> response= condominiumService.update(id, dto);
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
    public ResponseEntity<?> delete(@PathVariable("idCondominium") String id) {
        LOGGER.info(LOGLINE, Constants.METHOD_DELETE, Constants.IN);

        Validate.ValidateInputId(id, Constants.METHOD_DELETE);

        ServiceResponse<?> response= condominiumService.delete(id);
        return HandleResponseUtil.handle(response, Constants.METHOD_DELETE);
       
    }



    /*
     * Apartment
     * 
     */
    @DeleteMapping( value= "/{idCondominium}/apartment/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_DELETE_APARTMENT, description = Constants.API_DELETE_APARTMENT_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_DELETE_APARTMENT,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> deleteApartment(@PathVariable("idCondominium") String idCondominium, @PathVariable("id") String id) {
        LOGGER.info(LOGLINE, DELETE_APARTMENT, Constants.IN);

        Validate.ValidateInputId(id, DELETE_APARTMENT);

        ServiceResponse<?> response= apartmentService.delete(id);
        return HandleResponseUtil.handle(response, DELETE_APARTMENT);
       
    }

    @GetMapping( value= "/{idCondominium}/apartment/{id}" ,  produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_GET_APARTMENT, description = Constants.API_GET_APARTMENT_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_GET_APARTMENT,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> getApartment(@PathVariable("id") String id) {
        LOGGER.info(LOGLINE, GET_APARTMENT, Constants.IN);

        Validate.ValidateInputId(id, GET_APARTMENT);

        ServiceResponse<?> response= apartmentService.getItem(id);
        return HandleResponseUtil.handle(response, GET_APARTMENT);
    }


    @GetMapping( value= "/{idCondominium}/apartment" ,  produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_GET_APARTMENTS, description = Constants.API_GET_APARTMENTS_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_GET_APARTMENTS,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> getApartments(@PathVariable("idCondominium") String idCondominium,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        LOGGER.info(LOGLINE, GET_APARTMENTS, Constants.IN);

        Validate.ValidateInputId(idCondominium, GET_APARTMENTS);

        ServiceResponse<PagedResponse<?>> response = apartmentService.getAll(page, size,idCondominium);
        return HandleResponseUtil.handle(response, GET_APARTMENTS);
    }


    @PostMapping( value= "/{idCondominium}/apartment" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_CREATE_APARTMENT, description = Constants.API_CREATE_APARTMENT_DESCRIPTION)
    @ApiResponse(responseCode = "201", description = Constants.API_CREATE_APARTMENT,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+ MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+ MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> createApartment(@PathVariable("idCondominium") String idCondominium, @Valid @RequestBody CreateApartmentDto apartment,BindingResult bindingResult) {
        LOGGER.info(LOGLINE, CREATE_APARTMENT, Constants.IN);

        Validate.ValidateInput(bindingResult, CREATE_APARTMENT);

        ServiceResponse<?> response = apartmentService.create(apartment,idCondominium);
        return HandleResponseUtil.handle(response, CREATE_APARTMENT);        
    }

    
    
    @PutMapping( value= "/{idCondominium}/apartment/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_UPDATE_CONDOMINIUM, description = Constants.API_UPDATE_CONDOMINIUM_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_UPDATE_CONDOMINIUM,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> updateApartment(@PathVariable("idCondominium") String id, @Valid @RequestBody UpdateApartmentDto dto,BindingResult bindingResult) {
        LOGGER.info(LOGLINE, UPDATE_APARTMENT, Constants.IN);
    
        Validate.ValidateInput(bindingResult, Constants.METHOD_UPDATE);
    
        ServiceResponse<?> response= apartmentService.update(id, dto);
        return HandleResponseUtil.handle(response, Constants.METHOD_UPDATE);
        
    }


}
