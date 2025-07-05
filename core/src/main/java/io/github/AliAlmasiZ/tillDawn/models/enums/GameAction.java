package io.github.AliAlmasiZ.tillDawn.models.enums;

public enum GameAction {
    MOVE_UP,
    MOVE_DOWN,
    MOVE_LEFT,
    MOVE_RIGHT,
    RELOAD,
    SHOOT,
    PAUSE,
    TOGGLE_AUTO_AIM;


    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().toLowerCase().replace("_", " ").substring(1);
    }
}



