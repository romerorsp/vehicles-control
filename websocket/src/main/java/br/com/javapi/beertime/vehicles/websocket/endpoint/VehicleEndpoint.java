package br.com.javapi.beertime.vehicles.websocket.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import br.com.javapi.beertime.vehicles.bean.Transition;
import br.com.javapi.beertime.vehicles.common.bean.Message;

@ServerEndpoint("/vehicles/{fieldId}/{vehicleId}")
public class VehicleEndpoint {

    public final List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onOpen(@PathParam("fieldId") final String fieldId, final @PathParam("vehicleId") String vehicleId, final Session session) {
        //TODO: Send the vehicle connection to the queue/topic
    }

    @OnMessage
    public void onMessage(Session session, Message<Transition> message) {
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
}
