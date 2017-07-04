package br.com.javapi.beertime.vehicles.common.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class Field implements Serializable {
    
    private static final long serialVersionUID = 3889661368060744791L;

    private List<Vehicle> vehicles;
    
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
}
