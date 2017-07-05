package br.com.javapi.beertime.vehicles.common.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 3428590369814336826L;

    private String id;
    
    private State state;
    
    private int posX;
    
    private int posY;

    public Vehicle(String id, State state) {
        this.id = id;
        this.state = state;
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
