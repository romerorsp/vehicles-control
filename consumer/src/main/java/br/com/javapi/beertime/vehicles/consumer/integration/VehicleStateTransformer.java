package br.com.javapi.beertime.vehicles.consumer.integration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.javapi.beertime.vehicles.common.dto.VehicleStateDTO;

@Component
public class VehicleStateTransformer implements GenericTransformer<String, VehicleStateDTO> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleStateTransformer.class);
    
    @Override
    public VehicleStateDTO transform(final String jsonString) {
        try {
            return MAPPER.readValue(jsonString, VehicleStateDTO.class);
        } catch (IOException e) {
            LOGGER.error("Could not parse String [{}]to type [{}]", jsonString, VehicleStateDTO.class, e);
            return null;
        }
    }
}