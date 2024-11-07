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

import com.core.coffee.dto.CreateEventDto;
import com.core.coffee.dto.ErrorDto;
import com.core.coffee.dto.PagedResponse;
import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.dto.UpdateEventDto;
import com.core.coffee.dto.UpdateUserDto;
import com.core.coffee.service.EventService;
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
@RequestMapping("/${api.version}/condominium/{idCondominium}/events")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = Constants.API_INVALID_REQUEST, 
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
        schema = @Schema(implementation = ErrorDto.class)), headers = {
                @Header(name = "Content-Type: " + MediaType.APPLICATION_JSON_VALUE),
                @Header(name = "Accept: " + MediaType.APPLICATION_JSON_VALUE)
        }),
        @ApiResponse(responseCode = "403", description = Constants.API_ACCESS_DENIED, 
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
        schema = @Schema(implementation = ErrorDto.class)), headers = {
                @Header(name = "Content-Type: " + MediaType.APPLICATION_JSON_VALUE),
                @Header(name = "Accept: " + MediaType.APPLICATION_JSON_VALUE)
        }),
        @ApiResponse(responseCode = "404", description = Constants.API_EVENT_NOT_FOUND, 
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
        schema = @Schema(implementation = ErrorDto.class)), headers = {
                @Header(name = "Content-Type: " + MediaType.APPLICATION_JSON_VALUE),
                @Header(name = "Accept: " + MediaType.APPLICATION_JSON_VALUE)
        }),
        @ApiResponse(responseCode = "500", description = Constants.GENERIC_ERROR, 
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
        schema = @Schema(implementation = ErrorDto.class)), headers = {
                @Header(name = "Content-Type: " + MediaType.APPLICATION_JSON_VALUE),
                @Header(name = "Accept: " + MediaType.APPLICATION_JSON_VALUE)
        })
})
@Tag(name = Constants.API_EVENT, description = Constants.API_EVENT_DESCRIPTION)
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);
    private static final String LOGLINE = EventController.class.getName() + " - {} - {}";

    private EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_CREATE_EVENT, description = Constants.API_CREATE_EVENT_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_CREATE_EVENT, 
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
    schema = @Schema(implementation = ServiceResponse.class)), headers = {
            @Header(name = "Content-Type: " + MediaType.APPLICATION_JSON_VALUE),
            @Header(name = "Accept: " + MediaType.APPLICATION_JSON_VALUE)
    })
    public ResponseEntity<?> create(@Valid @PathVariable String idCondominium, @RequestBody CreateEventDto dto, BindingResult bindingResult) {
        LOGGER.info(LOGLINE, Constants.METHOD_CREATE, Constants.IN);

        Validate.ValidateInput(bindingResult, Constants.METHOD_CREATE);
        
        ServiceResponse<?> response = service.createEvent(dto, idCondominium);
        return HandleResponseUtil.handle(response, Constants.METHOD_CREATE);
    }



    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_UPDATE_EVENT, description = Constants.API_UPDATE_EVENT_DESCRIPTION, 
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = Constants.API_UPDATE_EVENT_DESCRIPTION, 
            required = true, 
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UpdateUserDto.class))))
    @ApiResponse(responseCode = "200", description = Constants.API_UPDATE_EVENT, 
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
    schema = @Schema(implementation = ServiceResponse.class)), headers = {
            @Header(name = "Content-Type: " + MediaType.APPLICATION_JSON_VALUE),
            @Header(name = "Accept: " + MediaType.APPLICATION_JSON_VALUE)
    })
    public ResponseEntity<?> update(@PathVariable String idCondominium, @PathVariable String id, @Valid @RequestBody UpdateEventDto dto,
            BindingResult bindingResult) {
        LOGGER.info(LOGLINE, Constants.METHOD_UPDATE, Constants.IN);

        Validate.ValidateInput(bindingResult, Constants.METHOD_UPDATE);

        ServiceResponse<?> response = service.update(id, dto);
        return HandleResponseUtil.handle(response, Constants.METHOD_UPDATE);
    }


    @DeleteMapping(value="/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_DELETE_EVENT, description = Constants.API_DELETE_EVENT_DESCRIPTION)
    @ApiResponse(responseCode = "204", description = Constants.API_DELETE_EVENT,
                content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ServiceResponse.class)
                                    ),
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

    @GetMapping( value= "/{id}" ,  produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_GET_EVENT, description = Constants.API_GET_EVENT_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_GET_USER,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
    })
    public ResponseEntity<?> getUser(@PathVariable String id) {
        LOGGER.info(LOGLINE, Constants.METHOD_GET, Constants.IN);

        Validate.ValidateInputId(id, Constants.METHOD_GET);

        ServiceResponse<?> response= service.getItem(id);
        return HandleResponseUtil.handle(response, Constants.METHOD_GET);
    }

    @GetMapping(  produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.API_GET_EVENTS, description = Constants.API_GET_EVENTS_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.API_GET_EVENTS,
    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
    headers={ 
        @Header(name = "Content-Type: "+MediaType.APPLICATION_JSON_VALUE),
        @Header(name = "Accept: "+MediaType.APPLICATION_JSON_VALUE)       
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
