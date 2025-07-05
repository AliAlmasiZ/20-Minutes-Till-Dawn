package io.github.AliAlmasiZ.tillDawn.models.Entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;

public class Tree {
    public Vector2 position;
    private transient Sprite sprite;
    public transient Rectangle bounds;
    public static final int TREE_DAMAGE = 5;

    public Tree() {
        this.sprite = new Sprite(GameAssetManager.getGameAssetManager().treeTex);
        this.sprite.setOriginCenter();
        this.bounds = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

    }
    public Tree(Texture texture, Vector2 startPosition) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(startPosition);
        this.sprite.setPosition(position.x, position.y);
        this.sprite.setOriginCenter();

        this.bounds = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void update() {
        sprite.setPosition(position.x, position.y);
        bounds.setPosition(sprite.getX(), sprite.getY());
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() {
        return sprite.getHeight();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
