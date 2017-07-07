package br.com.javapi.beertime.vehicles.common.bean;

import java.io.Serializable;
import java.util.Map;

public interface State extends Serializable {

    void setTransitions(Map<Transition, State> transitions);
    
    State nextFor(Transition transition);
    
    StateType getType();

    Transition get();
}
