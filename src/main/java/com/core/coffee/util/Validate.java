package com.core.coffee.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

import com.core.coffee.exception.CustomException;

public class Validate {
    private static final Logger LOGGER = LoggerFactory.getLogger(Validate.class);
    private static final String LOGLINE = Validate.class.getName() + " - {} - {}";

    private Validate() {
        throw new IllegalStateException("Validate class");
    }
    
     public static void ValidateInput(BindingResult bindingResult,String operation){
        if (bindingResult.hasErrors()) {   
            LOGGER.error(LOGLINE, operation, bindingResult.getAllErrors().get(0).getDefaultMessage());           
            throw  new CustomException(bindingResult.getAllErrors().get(0).getDefaultMessage(), Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());		
        } 
    }

    public static void ValidateInputId(String input,String operation){
        if(input == null || input.isEmpty()) {
            LOGGER.error(LOGLINE, Constants.METHOD_DELETE, Constants.ERROR);
            throw  new CustomException(Constants.INVALID_ID, Constants.ERROR_CODE.VALIDATIONS_ERROR.getValue());
        }
    }
}
