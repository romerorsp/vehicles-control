package br.com.javapi.beertime.vehicles.common.bean;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 3428590369814336826L;

    private State state;
}
