package br.com.javapi.beertime.vehicles.common.bean;

public enum Transitions implements Transition {
    CREATE,
    MOVE_RIGHT,
    MOVE_LEFT,
    MOVE_UP,
    MOVE_DOWN,
    PAUSE,
    FINISH;
}
