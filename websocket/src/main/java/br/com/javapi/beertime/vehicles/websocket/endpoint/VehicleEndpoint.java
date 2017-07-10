package br.com.javapi.beertime.vehicles.websocket.endpoint;

import java.io.IOException;
import java.util.Optional;

import javax.annotation.Resource;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
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
import br.com.javapi.beertime.vehicles.common.bean.Transitions;
import br.com.javapi.beertime.vehicles.common.dto.VehicleStateDTO;
import br.com.javapi.beertime.vehicles.websocket.command.Command;
import br.com.javapi.beertime.vehicles.websocket.command.CommandTypes;
import br.com.javapi.beertime.vehicles.websocket.command.Commands;
import br.com.javapi.beertime.vehicles.websocket.encoder.VehicleStateEncoderDecoder;
import br.com.javapi.beertime.vehicles.websocket.service.FieldService;

@Component
@ServerEndpoint(value = "/socket/{fieldId}/{vehicleId}/{posX}/{posY}", configurator=CustomSpringConfigurator.class, encoders=VehicleStateEncoderDecoder.class, decoders=VehicleStateEncoderDecoder.class)
public class VehicleEndpoint implements VehiclesWebSocket {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleSupervisorEndpoint.class); 
    
    public Optional<Session> lastSession = Optional.empty();

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
            if(field.get().getWidth() < posX || field.get().getHeight() < posY) {
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
            this.lastSession = Optional.of(session);
        }
    }

    @OnMessage
    public void onMessage(final Session session, final VehicleStateDTO state) {
        channel.send(MessageBuilder.withPayload(state)
                                   .setHeader(Constants.MESSAGE_TYPE_HEADER_NAME, Constants.VEHICLE_MESSAGE_TYPE_VALUE)
                                   .build());
    }

    @OnClose
    public void onClose(@PathParam("fieldId") final String fieldId,
                        @PathParam("vehicleId") final String vehicleId,
                        @PathParam("posX")final int posX,
                        @PathParam("posY")final int posY,
                        final Session session) {
        channel.send(MessageBuilder.withPayload(new VehicleStateDTO(fieldId, vehicleId, posX, posY, Transitions.FINISH))
                                   .setHeader(Constants.MESSAGE_TYPE_HEADER_NAME, Constants.VEHICLE_MESSAGE_TYPE_VALUE)
                                   .build());
    }

    @Override
    public void notifyVehicleState(VehicleStateDTO state) {
        Command<VehicleStateDTO> command = commands.getCommand(CommandTypes.DRAW_VEHICLE, state);
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

    @Override
    public void notifyVehicleRemoved(VehicleStateDTO state) {
        Command<VehicleStateDTO> command = commands.getCommand(CommandTypes.REMOVE_VEHICLE, state);
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
