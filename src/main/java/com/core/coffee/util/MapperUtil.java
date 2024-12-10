package com.core.coffee.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.core.coffee.dto.GetApartmentDto;
import com.core.coffee.dto.GetCondominiumDto;
import com.core.coffee.dto.GetEventDto;
import com.core.coffee.entity.Apartment;
import com.core.coffee.entity.Condominium;
import com.core.coffee.entity.Event;

@Component
public class MapperUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapperUtil.class);
    private static final String LOGLINE = MapperUtil.class.getName() + " - {} - {}";

    


    private final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        initializeMappings();
    }

    private void initializeMappings() {
        // Definir un PropertyMap personalizado para Apartment -> ApartmentDto
        PropertyMap<Apartment, GetApartmentDto> apartmentMap = new PropertyMap<Apartment, GetApartmentDto>() {
            protected void configure() {
                map().setUser(source.getUser().getId());
                map().setCondominium(source.getCondominium().getId());
            }
        };

        // Definir un PropertyMap personalizado para Condominium -> GetCondominiumDto
        PropertyMap<Condominium, GetCondominiumDto> condominiumMap = new PropertyMap<Condominium, GetCondominiumDto>() {
            protected void configure() {
                map().setAdministrator(source.getAdministrator().getId());
            }
        };

        PropertyMap<Event, GetEventDto> eventMap = new PropertyMap<Event, GetEventDto>() {
            protected void configure() {
                map().setApartment(source.getApartment().getId());
                map().setCondominium(source.getCondominium().getId());
            }
        };

        modelMapper.addMappings(apartmentMap);
        modelMapper.addMappings(condominiumMap);
        modelMapper.addMappings(eventMap);
    }

    public <T> T map(Object source, Class<T> target) {
        LOGGER.info(LOGLINE, "map", "Mapping object to target class");

        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        
        T mapped = modelMapper.map(source, target);
        
        return mapped;
    }

    public Object merge(Object source, Object target) {
        LOGGER.info(LOGLINE, "merge", "Merging object to target object");

        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        modelMapper.map(source, target);

        return target;
    }

  
    
}
