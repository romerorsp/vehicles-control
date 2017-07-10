package br.com.javapi.beertime.vehicles.websocket.endpoint;

import java.io.IOException;
import java.util.Optional;

import javax.annotation.Resource;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import br.com.javapi.beertime.vehicles.common.bean.Field;
import br.com.javapi.beertime.vehicles.common.dto.VehicleStateDTO;
import br.com.javapi.beertime.vehicles.websocket.command.Command;
import br.com.javapi.beertime.vehicles.websocket.command.CommandTypes;
import br.com.javapi.beertime.vehicles.websocket.command.Commands;
import br.com.javapi.beertime.vehicles.websocket.encoder.FieldEncoderDecoder;
import br.com.javapi.beertime.vehicles.websocket.encoder.VehicleStateEncoderDecoder;

@Component
@ServerEndpoint(value = "/socket/supervisor/{id}", configurator=CustomSpringConfigurator.class, encoders=FieldEncoderDecoder.class, decoders=VehicleStateEncoderDecoder.class)
public class VehicleSupervisorEndpoint implements SupervisorWebSocket {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleSupervisorEndpoint.class); 
    
    @Autowired
    private Commands commands;
    
    @Resource(name = "vehicleStateChannel")
    private MessageChannel channel;

    private Optional<Session> lastSession = Optional.empty();

    @OnOpen
    public void onOpen(@PathParam("id") final String id, final Session session) {
        this.lastSession = Optional.of(session);
    }

    @OnMessage
    public void onMessage(Session session, VehicleStateDTO state) {
        this.notifyChangeState(state);
    }

    @Override
    public void notifyNewField(Field field) {
        Command<Field> command = commands.getCommand(CommandTypes.NEW_FIELD, field);
        lastSession.ifPresent(last -> last.getOpenSessions().forEach(session -> {
            try {
                session.getBasicRemote().sendObject(command);
            } catch (IOException | EncodeException e) {
                LOGGER.error("Severe Error while sending field [{}] to the session [{}]", field, session.getId(), e);
            }
        }));
    }
    
    public void notifyChangeState(VehicleStateDTO state) {
        Command<VehicleStateDTO> command = commands.getCommand(CommandTypes.CHANGE_VEHICLE_STATE, state);
        this.lastSession.ifPresent(session -> session.getOpenSessions()
                                                     .stream()
                                                     .forEach(open -> {
            try {
                open.getBasicRemote().sendObject(command);
            } catch (IOException | EncodeException e) {
                LOGGER.error("Severe Error while sending vehicle state [{}] to the session [{}]", state, open.getId(), e);
            }
        }));        
    }
}
