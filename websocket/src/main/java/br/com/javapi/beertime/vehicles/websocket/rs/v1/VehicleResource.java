package br.com.javapi.beertime.vehicles.websocket.rs.v1;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.javapi.beertime.vehicles.websocket.rs.v1.request.FieldRequest;
import br.com.javapi.beertime.vehicles.websocket.rs.v1.request.VehicleRequest;
import br.com.javapi.beertime.vehicles.websocket.service.FieldService;

@Path("v1")
@Component
public class VehicleResource {
    
    @Autowired
    private FieldService service;
    
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("field")
    public Response createField(@Valid final FieldRequest request) {
        //TODO: Add the new field to the (hazelcast) cache ....
        return Response.ok().build();
    }
    
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("forfield/{fieldId}")
    public Response createVehicle(@Valid final VehicleRequest request) {
        //TODO: Create the new vehicle in the parameterized field....
        return Response.status(Status.CREATED).build();
    }
    
    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("fields")
    public Response getExistingFields() {
        //TODO: Return the (hazelcast) cached fields and their respective vehicles so that the client can create a interface to show everything...
        return Response.ok(service.getFieldList()).build();
    }
    
    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("automata")
    public Response getStateMachine() {
        //TODO: Return the initialstate from the automata to inform client about the possible state transitions...
        return Response.ok().build();
    }
}