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

import br.com.javapi.beertime.vehicles.common.bean.Field;
import br.com.javapi.beertime.vehicles.websocket.command.Command;

public class FieldEncoderDecoder implements Encoder.Text<Command<?>>, Decoder.Text<Field> {

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
    public Field decode(String value) throws DecodeException {
        try {
            return MAPPER.readValue(value, Field.class);
        } catch (IOException e) {
            throw new DecodeException(value, "The String is not parseable to Field object", e);
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
