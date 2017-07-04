package br.com.javapi.beertime.vehicles.websocket.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Commands {

    @Autowired
    private ApplicationContext context;

    @SuppressWarnings("unchecked")
    public <T> Command<T> getCommand(CommandTypes type, T payload) {
        Command<T> command = context.getBean(type.getName(), Command.class);
        command.setPayload(payload);
        return command;
    }
}
