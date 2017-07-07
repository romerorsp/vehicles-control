package br.com.javapi.beertime.vehicles.common.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class Field implements Serializable {
    
    private static final long serialVersionUID = 3889661368060744791L;

    private String name;
    
    private int width;
    
    private int height;

    private String id;

    public Field() {}

    public Field(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.id = name.toLowerCase().replaceAll(" ", "-");
    }
    
    public void setName(String name) {
        this.name = name;
        this.id = name.toLowerCase().replaceAll(" ", "-");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Field other = (Field) obj;
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
