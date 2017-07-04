package br.com.javapi.beertime.vehicles.websocket.command;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import br.com.javapi.beertime.vehicles.common.bean.Field;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.ANY, setterVisibility = Visibility.NONE)
@Component(value=NewFieldCommand.BEAN_NAME)
public class NewFieldCommand implements Command<Field>{

    public static final String BEAN_NAME = CommandTypes.PREFIX_NAME + "NEW_FIELD";
    
    private Field payload;

    @Override
    public String getName() {
        return CommandTypes.NEW_FIELD.toString();
    }

    @Override
    public Field getPayload() {
        return payload;
    }

    @Override
    public void setPayload(Field payload) {
        this.payload = payload; 
    }
}
