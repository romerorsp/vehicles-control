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
}
