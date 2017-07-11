package br.com.javapi.beertime.vehicles.websocket.endpoint;

import br.com.javapi.beertime.vehicles.common.bean.Field;
import br.com.javapi.beertime.vehicles.common.dto.VehicleStateDTO;

public interface SupervisorWebSocket {

    void notifyVehicleState(VehicleStateDTO sto);

    void notifyNewField(Field field);
    
    void notifyVehicleRemoved(VehicleStateDTO dto);
}
