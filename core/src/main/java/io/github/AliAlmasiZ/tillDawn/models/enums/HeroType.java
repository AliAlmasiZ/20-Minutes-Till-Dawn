package io.github.AliAlmasiZ.tillDawn.models.enums;

public enum HeroType {
    SHANA(4, 4),
    DIAMOND(7, 1),
    SCARLET(3, 5),
    LILITH(5, 3),
    DASHER(2, 10)
    ;



    public int hp;
    public int speed;

    HeroType(int hp, int speed) {
        this.hp = hp;
        this.speed = speed;
    }


    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
