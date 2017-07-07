package br.com.javapi.beertime.vehicles.websocket.command;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import br.com.javapi.beertime.vehicles.common.dto.VehicleStateDTO;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.ANY, setterVisibility = Visibility.NONE)
@Component(value=RemoveVehicleCommand.BEAN_NAME)
public class RemoveVehicleCommand implements Command<VehicleStateDTO>{

    public static final String BEAN_NAME = CommandTypes.PREFIX_NAME + "REMOVE_VEHICLE";
    
    private VehicleStateDTO payload;

    @Override
    public String getName() {
        return CommandTypes.REMOVE_VEHICLE.toString();
    }

    @Override
    public VehicleStateDTO getPayload() {
        return payload;
    }

    @Override
    public void setPayload(VehicleStateDTO payload) {
        this.payload = payload; 
    }
}
