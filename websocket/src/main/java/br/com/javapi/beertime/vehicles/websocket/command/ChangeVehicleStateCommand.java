package br.com.javapi.beertime.vehicles.websocket.command;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import br.com.javapi.beertime.vehicles.common.dto.VehicleStateDTO;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.ANY, setterVisibility = Visibility.NONE)
@Component(value=ChangeVehicleStateCommand.BEAN_NAME)
public class ChangeVehicleStateCommand implements Command<VehicleStateDTO>{

    public static final String BEAN_NAME = CommandTypes.PREFIX_NAME + "CHANGE_VEHICLE_STATE";
    
    private VehicleStateDTO payload;

    @Override
    public String getName() {
        return CommandTypes.CHANGE_VEHICLE_STATE.toString();
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
