package br.com.javapi.beertime.vehicles.websocket.endpoint;

import br.com.javapi.beertime.vehicles.common.bean.Field;

public interface SupervisorWebSocket {
    void notifyNewField(Field field);
}
