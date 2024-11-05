package com.core.coffee.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;


import com.core.coffee.util.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String LOGLINE = GlobalExceptionHandler.class.getName() +" - {} - {}";  

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        LOGGER.info(LOGLINE, MethodArgumentNotValidException.class.getName(), Constants.IN);

        Map<String, String> error = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE);
        String s = formatter.format(Calendar.getInstance().getTime());
        error.put(Constants.KEY_TIMESTAMP, s);
        error.put(Constants.KEY_ERROR, ex.getMessage());

        LOGGER.error(LOGLINE, MethodArgumentNotValidException.class.getName(), ex.getMessage());
        LOGGER.info(LOGLINE, MethodArgumentNotValidException.class.getName(), Constants.OUT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(HttpMessageNotReadableException ex) {
        LOGGER.info(LOGLINE, HttpMessageNotReadableException.class.getName(), Constants.IN);

        Map<String, String> error = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE);
        String s = formatter.format(Calendar.getInstance().getTime());
        error.put(Constants.KEY_TIMESTAMP, s);
        error.put(Constants.KEY_ERROR, ex.getMessage());
        error.put(Constants.KEY_ERROR_CODE, Constants.ERROR_CODE.BAD_REQUEST.getValue());


        LOGGER.error(LOGLINE, HttpMessageNotReadableException.class.getName(), ex.getMessage());
        LOGGER.info(LOGLINE, HttpMessageNotReadableException.class.getName(), Constants.OUT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        LOGGER.info(LOGLINE, NotFoundException.class.getName(), Constants.IN);

        Map<String, String> error = new HashMap<>();        
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE);
        String s = formatter.format(Calendar.getInstance().getTime());
        error.put(Constants.KEY_TIMESTAMP, s);
        error.put(Constants.KEY_ERROR, ex.getMessage());
        error.put(Constants.KEY_ERROR_CODE, Constants.ERROR_CODE.NOT_FOUND.getValue());

        LOGGER.error(LOGLINE, NotFoundException.class.getName(), ex.getMessage());
        LOGGER.info(LOGLINE, NotFoundException.class.getName(), Constants.OUT);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NoResourceFoundException ex) {
        LOGGER.info(LOGLINE, NoResourceFoundException.class.getName(), Constants.IN);
        
        Map<String, String> error = new HashMap<>();        
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE);
        String s = formatter.format(Calendar.getInstance().getTime());
        error.put(Constants.KEY_TIMESTAMP, s);
        error.put(Constants.KEY_ERROR, ex.getMessage());
        error.put(Constants.KEY_ERROR_CODE, Constants.ERROR_CODE.RESOURCE_NOT_FOUND.getValue());

        LOGGER.error(LOGLINE, NoResourceFoundException.class.getName(), ex.getMessage());
        LOGGER.info(LOGLINE, NoResourceFoundException.class.getName(), Constants.OUT);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

       
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleCustomException(CustomException ex) {
        LOGGER.info(LOGLINE, CustomException.class.getName(), Constants.IN);

        Map<String, String> error = new HashMap<>();       
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE);
        String s = formatter.format(ex.getTimestamp());
        error.put(Constants.KEY_TIMESTAMP, s);
        error.put(Constants.KEY_ERROR, ex.getMessage());
        error.put(Constants.KEY_ERROR_CODE, ex.getErrorCode());

        LOGGER.error(LOGLINE, CustomException.class.getName(), ex.getMessage());
        LOGGER.info(LOGLINE, CustomException.class.getName(), Constants.OUT);

        if(ex.getErrorCode().equals(Constants.ERROR_CODE.INTERNAL_SERVER_ERROR.getValue())) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(RuntimeException ex) {
        LOGGER.info(LOGLINE, RuntimeException.class.getName(), Constants.IN);

        Map<String, String> error = new HashMap<>();        
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE);
        String s = formatter.format(Calendar.getInstance().getTime());
        error.put(Constants.KEY_TIMESTAMP, s);
        error.put(Constants.KEY_ERROR, Constants.GENERIC_ERROR);
        error.put(Constants.KEY_ERROR_CODE, Constants.ERROR_CODE.INTERNAL_SERVER_ERROR.getValue());

        LOGGER.error(LOGLINE, RuntimeException.class.getName(), ex.getMessage());
        LOGGER.info(LOGLINE, RuntimeException.class.getName(), Constants.OUT);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(HttpRequestMethodNotSupportedException ex) {
        LOGGER.info(LOGLINE, RuntimeException.class.getName(), Constants.IN);

        Map<String, String> error = new HashMap<>();        
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE);
        String s = formatter.format(Calendar.getInstance().getTime());
        error.put(Constants.KEY_TIMESTAMP, s);
        error.put(Constants.KEY_ERROR, ex.getMessage());
        error.put(Constants.KEY_ERROR_CODE, Constants.ERROR_CODE.INTERNAL_SERVER_ERROR.getValue());

        LOGGER.error(LOGLINE, RuntimeException.class.getName(), ex.getMessage());
        LOGGER.info(LOGLINE, RuntimeException.class.getName(), Constants.OUT);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(HttpMediaTypeNotSupportedException ex) {
        LOGGER.info(LOGLINE, RuntimeException.class.getName(), Constants.IN);

        Map<String, String> error = new HashMap<>();        
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE);
        String s = formatter.format(Calendar.getInstance().getTime());
        error.put(Constants.KEY_TIMESTAMP, s);
        error.put(Constants.KEY_ERROR, ex.getMessage());
        error.put(Constants.KEY_ERROR_CODE, Constants.ERROR_CODE.BAD_REQUEST.getValue());

        LOGGER.error(LOGLINE, RuntimeException.class.getName(), ex.getMessage());
        LOGGER.info(LOGLINE, RuntimeException.class.getName(), Constants.OUT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(MissingServletRequestParameterException ex) {
        LOGGER.info(LOGLINE, RuntimeException.class.getName(), Constants.IN);

        Map<String, String> error = new HashMap<>();        
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE);
        String s = formatter.format(Calendar.getInstance().getTime());
        error.put(Constants.KEY_TIMESTAMP, s);
        error.put(Constants.KEY_ERROR, ex.getMessage());
        error.put(Constants.KEY_ERROR_CODE, Constants.ERROR_CODE.BAD_REQUEST.getValue());

        LOGGER.error(LOGLINE, RuntimeException.class.getName(), ex.getMessage());
        LOGGER.info(LOGLINE, RuntimeException.class.getName(), Constants.OUT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(AuthenticationException ex) {
        LOGGER.info(LOGLINE, RuntimeException.class.getName(), Constants.IN);

        Map<String, String> error = new HashMap<>();        
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE);
        String s = formatter.format(Calendar.getInstance().getTime());
        error.put(Constants.KEY_TIMESTAMP, s);
        error.put(Constants.KEY_ERROR, ex.getMessage());
        error.put(Constants.KEY_ERROR_CODE, Constants.ERROR_CODE.FORBIDDEN.getValue());

        LOGGER.error(LOGLINE, RuntimeException.class.getName(), ex.getMessage());
        LOGGER.info(LOGLINE, RuntimeException.class.getName(), Constants.OUT);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }


    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(MissingRequestHeaderException ex) {
        LOGGER.info(LOGLINE, RuntimeException.class.getName(), Constants.IN);

        Map<String, String> error = new HashMap<>();        
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.FORMAT_DATE);
        String s = formatter.format(Calendar.getInstance().getTime());
        error.put(Constants.KEY_TIMESTAMP, s);
        error.put(Constants.KEY_ERROR, ex.getMessage());
        error.put(Constants.KEY_ERROR_CODE, Constants.ERROR_CODE.FORBIDDEN.getValue());

        LOGGER.error(LOGLINE, RuntimeException.class.getName(), ex.getMessage());
        LOGGER.info(LOGLINE, RuntimeException.class.getName(), Constants.OUT);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
    


    
}
