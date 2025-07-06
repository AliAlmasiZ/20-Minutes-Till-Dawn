package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class DeathAnimation {
    private Animation<TextureRegion> animation;
    private Vector2 position;
    private float stateTime = 0f;

    public DeathAnimation(Animation<TextureRegion> animation, Vector2 position) {
        this.animation = animation;
        // Center the animation on the provided position
        this.position = new Vector2(
            position.x - animation.getKeyFrame(0).getRegionWidth() / 2f,
            position.y - animation.getKeyFrame(0).getRegionHeight() / 2f
        );
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public boolean isFinished() {
        // Check if the animation has finished playing
        return animation.isAnimationFinished(stateTime);
    }

    public void draw(SpriteBatch batch) {
        // Get the current frame. The 'false' parameter means the animation should not loop.
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
        if (currentFrame != null) {
            batch.draw(currentFrame, position.x, position.y);
        }
    }
}
