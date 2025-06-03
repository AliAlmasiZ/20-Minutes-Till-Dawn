package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.AliAlmasiZ.tillDawn.models.Player;

public class GameView {
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    public GameView() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        shapeRenderer = new ShapeRenderer();
    }

    public void render(Player player) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(player.getPosition().x, player.getPosition().y, 20);


    }
}
