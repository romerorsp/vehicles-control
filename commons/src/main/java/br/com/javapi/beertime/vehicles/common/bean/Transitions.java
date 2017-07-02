package br.com.javapi.beertime.vehicles.common.bean;

import br.com.javapi.beertime.vehicles.bean.Transition;

public enum Transitions implements Transition {
    CREATE,
    MOVE_RIGHT,
    MOVE_LEFT,
    MOVE_UP,
    MOVE_DOWN,
    PAUSE,
    FINISH;
}
