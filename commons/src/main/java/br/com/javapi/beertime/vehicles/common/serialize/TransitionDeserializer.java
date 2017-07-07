package br.com.javapi.beertime.vehicles.common.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import br.com.javapi.beertime.vehicles.common.bean.Transition;
import br.com.javapi.beertime.vehicles.common.bean.Transitions;

public class TransitionDeserializer extends JsonDeserializer<Transition> {

    @Override
    public Transition deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        return Transitions.valueOf(parser.getText());
    }
}
