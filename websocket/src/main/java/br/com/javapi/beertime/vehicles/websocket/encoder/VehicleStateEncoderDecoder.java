package br.com.javapi.beertime.vehicles.websocket.encoder;

import java.io.IOException;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.javapi.beertime.vehicles.common.dto.VehicleStateDTO;
import br.com.javapi.beertime.vehicles.websocket.command.Command;

public class VehicleStateEncoderDecoder implements Encoder.Text<Command<?>>, Decoder.Text<VehicleStateDTO> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Override
    public void destroy() {}

    @Override
    public void init(EndpointConfig config) {}

    @Override
    public String encode(Command<?> command) throws EncodeException {
        try {
            return MAPPER.writeValueAsString(command);
        } catch (JsonProcessingException e) {
            throw new EncodeException(command, "The Command object is not parseable", e);
        }
    }

    @Override
    public VehicleStateDTO decode(String value) throws DecodeException {
        try {
            return MAPPER.readValue(value, VehicleStateDTO.class);
        } catch (IOException e) {
            throw new DecodeException(value, "The String is not parseable to VehicleStateDTO object", e);
        }
    }

    @Override
    public boolean willDecode(String value) {
        try {
            return StringUtils.hasLength(new JSONObject(value).toString());
        } catch (Exception e) {
            return false;
        }
    }
}
