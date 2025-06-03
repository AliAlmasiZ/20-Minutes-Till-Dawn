package io.github.AliAlmasiZ.tillDawn.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
    public Vector2 position;
    public float speed = 200f;
    public int health = 100;
    public int maxHealth = 100;
    public int damage = 10;

    private Sprite sprite;
    private Animation<TextureRegion> walkAnimation;

    public boolean isMoving;
    public float stateTime;
    public float rotation;

    public Player() {
        position = new Vector2(0, 0);
        isMoving = false;
        stateTime = 0;
        rotation = 0;
    }

    public void updateStateTime(float delta) {
        stateTime += delta;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        this.isMoving = moving;
    }

    public float getStateTime() {
        return stateTime;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSpeed() {
        return speed;
    }

    public float getRotation() {
        return rotation;
    }
}
