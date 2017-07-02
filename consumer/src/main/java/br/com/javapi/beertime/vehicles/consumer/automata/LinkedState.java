package br.com.javapi.beertime.vehicles.consumer.automata;

import java.util.Map;

import br.com.javapi.beertime.vehicles.bean.Transition;
import br.com.javapi.beertime.vehicles.common.bean.State;
import lombok.Data;

@Data
class LinkedState implements State {
    
    private static final long serialVersionUID = 7301118732827399693L;

    private String name;

    private Map<Transition, State> transitions;

    public LinkedState(String name) {
        this.name = name;
    }

    @Override
    public void setTransitions(Map<Transition, State> transitions) {
        this.transitions = transitions;
    }

    @Override
    public String toString() {
        return "LinkedState [name=" + name + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LinkedState other = (LinkedState) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
}