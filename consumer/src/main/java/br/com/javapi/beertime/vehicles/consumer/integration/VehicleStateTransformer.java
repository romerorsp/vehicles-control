package br.com.javapi.beertime.vehicles.consumer.integration;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

import br.com.javapi.beertime.vehicles.common.bean.Message;

@Component
public class VehicleStateTransformer implements GenericTransformer<String, Message<?>> {

    @Override
    public Message<?> transform(String jsonString) {
        // TODO Transform a jsonString into a Message
        return null;
    }
}