package br.com.javapi.beertime.vehicles.websocket.endpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.annotation.Resource;
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
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import br.com.javapi.beertime.vehicles.common.Constants;
import br.com.javapi.beertime.vehicles.common.bean.Field;
import br.com.javapi.beertime.vehicles.common.bean.Vehicle;
import br.com.javapi.beertime.vehicles.common.dto.VehicleStateDTO;
import br.com.javapi.beertime.vehicles.websocket.command.Command;
import br.com.javapi.beertime.vehicles.websocket.command.CommandTypes;
import br.com.javapi.beertime.vehicles.websocket.command.Commands;
import br.com.javapi.beertime.vehicles.websocket.encoder.VehicleStateEncoderDecoder;
import br.com.javapi.beertime.vehicles.websocket.service.FieldService;

@Component
@ServerEndpoint(value = "/socket/{fieldId}/{vehicleId}/{posX}/{posY}", configurator=CustomSpringConfigurator.class, encoders=VehicleStateEncoderDecoder.class, decoders=VehicleStateEncoderDecoder.class)
public class VehicleEndpoint implements VehicleWebSocket {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleSupervisorEndpoint.class); 
    
    public final Map<String, Session> sessions = new HashMap<>();

    @Autowired
    private Commands commands;

    @Autowired
    private FieldService service;
    
    @Resource(name = "vehicleStateChannel")
    private MessageChannel channel;

    @OnOpen
    public void onOpen(@PathParam("fieldId") final String fieldId,
                       @PathParam("vehicleId") final String vehicleId,
                       @PathParam("posX")final int posX,
                       @PathParam("posY")final int posY,
                       final Session session) {
        Optional<Field> field = service.getFieldList().stream().filter(fld -> fieldId.equalsIgnoreCase(fld.getId())).findAny();
        boolean error = false;
        if(field.isPresent()) {
            if(field.get().getWidth() < posY || field.get().getHeight() < posX) {
                error  = true;
            }
        } else {
            error = true;
        }
        if(error) {
            try {
                session.close();
            } catch (IOException e) {
                LOGGER.error("Error trying to close bad connection...", e);
            }
        } else {
            channel.send(MessageBuilder.withPayload(new VehicleStateDTO(fieldId, vehicleId, posX, posY))
                                       .setHeader(Constants.MESSAGE_TYPE_HEADER_NAME, Constants.VEHICLE_MESSAGE_TYPE_VALUE)
                                       .build());
            this.sessions.put(vehicleId, session);
        }
    }

    @OnMessage
    public void onMessage(final Session session, final VehicleStateDTO state) {
        channel.send(MessageBuilder.withPayload(state)
                                   .setHeader(Constants.MESSAGE_TYPE_HEADER_NAME, Constants.VEHICLE_MESSAGE_TYPE_VALUE)
                                   .build());
    }

    @OnClose
    public void onClose(Session session) {
        channel.send(MessageBuilder.withPayload(new VehicleStateDTO())
                                   .setHeader(Constants.MESSAGE_TYPE_HEADER_NAME, Constants.VEHICLE_MESSAGE_TYPE_VALUE)
                                   .build());
        sessions.entrySet().stream()
                           .filter(entry -> session.equals(entry.getValue()))
                           .map(Entry::getKey)
                           .findAny()
                           .ifPresent(this.sessions::remove);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        sessions.entrySet().stream()
                           .filter(entry -> session.equals(entry.getValue()))
                           .map(Entry::getKey)
                           .findAny()
                           .ifPresent(this.sessions::remove);
    }

    @Override
    public void notifyNewVehicle(Vehicle vehicle) {
        Command<Vehicle> command = commands.getCommand(CommandTypes.NEW_VEHICLE, vehicle);
        sessions.forEach((id, session) -> {
            try {
                session.getBasicRemote().sendObject(command);
            } catch (IOException | EncodeException e) {
                LOGGER.error("Severe Error while sending vehicle [{}] to the session [{}]", vehicle, id, e);
            }
        });
    }
}
