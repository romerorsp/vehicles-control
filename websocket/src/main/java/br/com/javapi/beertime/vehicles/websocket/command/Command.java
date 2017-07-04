package br.com.javapi.beertime.vehicles.websocket.command;

public interface Command<T> {

    String getName();
    
    T getPayload();

    void setPayload(T payload);
}
