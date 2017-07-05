package br.com.javapi.beertime.vehicles.websocket.command;

public enum CommandTypes {
    
    NEW_FIELD,
    NEW_VEHICLE;

    public static final String PREFIX_NAME = "Command";
    
    public String getName() {
        return PREFIX_NAME.concat(this.toString());
    }
}
