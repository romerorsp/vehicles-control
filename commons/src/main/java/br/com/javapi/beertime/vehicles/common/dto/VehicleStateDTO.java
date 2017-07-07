package br.com.javapi.beertime.vehicles.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.javapi.beertime.vehicles.common.bean.Transition;
import br.com.javapi.beertime.vehicles.common.bean.Transitions;
import br.com.javapi.beertime.vehicles.common.serialize.TransitionDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class VehicleStateDTO implements Serializable {

    private static final long serialVersionUID = -1506525502026851856L;

    private String fieldId;
    
    private String vehicleId;
    
    private int posX;
    
    private int posY;
    
    @JsonDeserialize(using=TransitionDeserializer.class)
    private Transition transition = Transitions.CREATE;

    public VehicleStateDTO() {}
    
    public VehicleStateDTO(final String fieldId, final String vehicleId, final int posX, final int posY) {
        this.fieldId = fieldId;
        this.vehicleId = vehicleId;
        this.posX = posX;
        this.posY = posY;
        
    }

    public VehicleStateDTO(String fieldId, String vehicleId, int posX, int posY, Transition transition) {
        this(fieldId, vehicleId, posX, posY);
        this.transition =  transition;
    }
}