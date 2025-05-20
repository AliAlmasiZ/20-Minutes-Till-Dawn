package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuView {
    public Stage stage;
    public TextButton loginButton, signupButton;
    public Label mainMenuLabel;


    public MainMenuView(Skin skin) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        loginButton = new TextButton("Login", skin);
        signupButton = new TextButton("Signup", skin);
        mainMenuLabel = new Label("Main Menu", skin);
//        table.setDebug(true);
        table.add(mainMenuLabel).pad(10);
        table.row();
        table.add(loginButton).pad(10);
        table.row();
        table.add(signupButton).pad(10);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
