package com.core.coffee.util;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapperUtil.class);
    private static final String LOGLINE = MapperUtil.class.getName() + " - {} - {}";

    


    private ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T map(Object source, Class<T> target) {
        LOGGER.info(LOGLINE, "map", "Mapping object to target class");

        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        T mapped = modelMapper.map(source, target);
        
        return mapped;
    }
    
}
