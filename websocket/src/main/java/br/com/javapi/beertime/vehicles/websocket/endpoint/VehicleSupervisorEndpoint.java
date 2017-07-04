package br.com.javapi.beertime.vehicles.websocket.endpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.javapi.beertime.vehicles.common.bean.Field;
import br.com.javapi.beertime.vehicles.websocket.command.Command;
import br.com.javapi.beertime.vehicles.websocket.command.CommandTypes;
import br.com.javapi.beertime.vehicles.websocket.command.Commands;
import br.com.javapi.beertime.vehicles.websocket.encoder.FieldEncoderDecoder;

@Component
@ServerEndpoint(value = "/socket/supervisor/{id}", configurator=CustomSpringConfigurator.class, encoders=FieldEncoderDecoder.class, decoders=FieldEncoderDecoder.class)
public class VehicleSupervisorEndpoint implements SupervisorWebSocket {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleSupervisorEndpoint.class); 
    
    public final Map<String, Session> sessions = new HashMap<>();

    @Autowired
    private Commands commands;

    @OnOpen
    public void onOpen(@PathParam("id") final String id, final Session session) {
        this.sessions.put(id, session);
    }

    @OnMessage
    public void onMessage(Session session, Field payload) {
        //TODO: handle message received from session
    }

    @OnClose
    public void onClose(Session session) {
        //TODO: handle closed session
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        //TODO: Handle errors...
    }

    @Override
    public void notifyNewField(Field field) {
        Command<Field> command = commands.getCommand(CommandTypes.NEW_FIELD, field);
        sessions.forEach((id, session) -> {
            try {
                session.getBasicRemote().sendObject(command);
            } catch (IOException | EncodeException e) {
                LOGGER.error("Severe Error while sending field [{}] to the session [{}]", field, id, e);
            }
        });
    }
}
