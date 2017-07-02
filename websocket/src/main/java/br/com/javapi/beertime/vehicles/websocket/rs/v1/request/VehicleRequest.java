package br.com.javapi.beertime.vehicles.websocket.rs.v1.request;

import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@XmlRootElement
@EqualsAndHashCode(callSuper=false)
public class VehicleRequest {
    
    @NotNull(message="A fieldId is required to assiciate the vehicle to.")
    @NotEmpty(message="A fieldId is required to assiciate the vehicle to.")
    @PathParam("fieldId")
    private String fieldId;
}
