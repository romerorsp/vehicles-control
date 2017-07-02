package br.com.javapi.beertime.vehicles.common.bean;

import java.io.Serializable;
import java.util.Map;

import br.com.javapi.beertime.vehicles.bean.Transition;

public interface State extends Serializable {

    void setTransitions(Map<Transition, State> transitions);
}
