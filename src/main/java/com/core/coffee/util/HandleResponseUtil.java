package com.core.coffee.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.core.coffee.dto.ServiceResponse;
import com.core.coffee.enums.Status;
import com.core.coffee.exception.CustomException;

public class HandleResponseUtil {
    
    private static final String LOGLINE = HandleResponseUtil.class.getName() + " - {} - {}";
    private static final Logger LOGGER = LoggerFactory.getLogger(HandleResponseUtil.class);

    

     /**
     * Método para manejar la respuesta de los servicios.
     * 
     * @param response Respuesta del servicio.
     * @param method Método que se está ejecutando.
     * @return ResponseEntity con el resultado de la operación.
     */
    @SuppressWarnings("null")
	public static ResponseEntity<?> handle(ServiceResponse<?> response,String method) {
        if (response.getStatus().equals(Status.OK) && response.getResponseObject() != null) {         
            LOGGER.info(LOGLINE, method, Constants.OUT);
            return new ResponseEntity<>(response.getResponseObject(), response.getHttpCode());
        } else if (response.getStatus().equals(Status.KO) && response.getResponseObject() != null) {
            LOGGER.error(LOGLINE, method, Constants.ERROR);
            return new ResponseEntity<>(response.getResponseObject(), response.getHttpCode());
        }else if (response.getStatus().equals(Status.OK) && response.getResponseObject() == null) {
            LOGGER.error(LOGLINE, method, Constants.ERROR);
            return new ResponseEntity<>(null, response.getHttpCode());
        }else if (response.getStatus().equals(Status.KO) && response.getResponseObject() == null) {
            LOGGER.error(LOGLINE, method, Constants.ERROR);
            return new ResponseEntity<>(null, response.getHttpCode());
        }
        else {
                LOGGER.error(LOGLINE, method, Constants.ERROR);
                throw  new CustomException(Constants.GENERIC_ERROR, Constants.ERROR_CODE.GENERIC_ERROR.getValue());               
        }
    }
}
