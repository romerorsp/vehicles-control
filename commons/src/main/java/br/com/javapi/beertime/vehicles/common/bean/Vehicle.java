package br.com.javapi.beertime.vehicles.common.bean;

import java.io.Serializable;

import br.com.javapi.beertime.vehicles.common.dto.VehicleStateDTO;
import lombok.Data;

@Data
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 3428590369814336826L;

    private String id;
    
    private State state;
    
    private int posX;
    
    private int posY;
    
    private String fieldId;

    public Vehicle(final String fieldId, final String id, final State state) {
        this.id = id;
        this.state = state;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fieldId == null) ? 0 : fieldId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vehicle other = (Vehicle) obj;
        if (fieldId == null) {
            if (other.fieldId != null)
                return false;
        } else if (!fieldId.equals(other.fieldId))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public VehicleStateDTO toDTO() {
        return new VehicleStateDTO(this.fieldId, this.id, this.posX, this.posY, this.state.get());
    }
}
