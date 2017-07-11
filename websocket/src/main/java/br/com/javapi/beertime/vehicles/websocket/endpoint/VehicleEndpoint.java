package br.com.javapi.beertime.vehicles.websocket.endpoint;

import java.io.IOException;
import java.util.Optional;

import javax.annotation.Resource;
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
import br.com.javapi.beertime.vehicles.websocket.encoder.VehicleStateEncoderDecoder;
import br.com.javapi.beertime.vehicles.websocket.service.FieldService;

@Component
@ServerEndpoint(value = "/socket/{fieldId}/{vehicleId}/{posX}/{posY}", configurator=CustomSpringConfigurator.class, encoders=VehicleStateEncoderDecoder.class, decoders=VehicleStateEncoderDecoder.class)
public class VehicleEndpoint implements VehiclesWebSocket {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleSupervisorEndpoint.class); 
    
    public Optional<Session> lastSession = Optional.empty();

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
        final Optional<Field> field = service.getFieldList().stream().filter(fld -> fieldId.equalsIgnoreCase(fld.getId())).findAny();
        final boolean error = (!field.isPresent()) || (field.get().getWidth() < posX || field.get().getHeight() < posY);
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
}
