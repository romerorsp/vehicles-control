package br.com.javapi.beertime.vehicles.websocket.endpoint;

import br.com.javapi.beertime.vehicles.common.dto.VehicleStateDTO;

public interface VehiclesWebSocket {

    public void notifyVehicleState(VehicleStateDTO sto);

    public void notifyVehicleRemoved(VehicleStateDTO dto);
}
