package br.com.javapi.beertime.vehicles.common.bean;

import java.util.Map;

import lombok.Data;

@Data
public class LinkedState implements State {
    
    private static final long serialVersionUID = 7301118732827399693L;

    private StateType type;

    private Map<Transition, State> transitions;

    public LinkedState(StateType type) {
        this.type = type;
    }

    @Override
    public void setTransitions(Map<Transition, State> transitions) {
        this.transitions = transitions;
    }

    @Override
    public String toString() {
        return "LinkedState [type=" + type + "]";
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
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public State nextFor(Transition transition) {
        return transitions.get(transition);
    }

    @Override
    public Transition get() {
        return type.transition();
    }
}