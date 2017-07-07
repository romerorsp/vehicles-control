package br.com.javapi.beertime.vehicles.websocket.command;

public enum CommandTypes {
    
    NEW_FIELD, DRAW_VEHICLE, REMOVE_VEHICLE, CHANGE_VEHICLE_STATE;

    public static final String PREFIX_NAME = "Command";
    
    public String getName() {
        return PREFIX_NAME.concat(this.toString());
    }
}
