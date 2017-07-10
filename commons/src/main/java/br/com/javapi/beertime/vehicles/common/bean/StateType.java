package br.com.javapi.beertime.vehicles.common.bean;

import java.util.Arrays;

public enum StateType {
    INITIAL(Transitions.PAUSE),
    CREATED(Transitions.CREATE),
    MOVING_LEFT(Transitions.MOVE_LEFT),
    MOVING_UP(Transitions.MOVE_UP),
    MOVING_RIGHT(Transitions.MOVE_RIGHT),
    MOVING_DOWN(Transitions.MOVE_DOWN),
    END(Transitions.FINISH);

    private Transition transition;

    private StateType(final Transition transition) {
        this.transition = transition;
    }
    
    public static StateType fromString(final String name) {
        return
            Arrays.stream(StateType.values())
                  .filter(type -> type.toString().equals(name))
                  .findAny()
                  .orElse(INITIAL);
    }

    public Transition transition() {
        return transition;
    }
    
    public static StateType fromTransition(final Transition transition) {
        return
        Arrays.stream(StateType.values())
              .filter(state -> state.transition.equals(transition))
              .findAny()
              .orElse(INITIAL);
    }
}