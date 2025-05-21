package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class SignupMenuView {
    private final Stage stage;
    private final Table table;
    public final Label usernameLabel, passwordLabel;
    public final TextField usernameField, passwordField;
    public final TextButton signupButton, guestButton, loginMenuBtn;


    public SignupMenuView(Skin skin) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        float width = stage.getViewport().getScreenWidth();
        float height = stage.getViewport().getScreenHeight();

        table = new Table();
        table.setFillParent(true);
        table.center();
        table.pad(40);

        stage.addActor(table);

        //Title
        Label title = new Label("Create Account", skin);
        title.setFontScale(1.5f);
        table.add(title).colspan(2).padBottom(20);
        table.row();

        //Username
        usernameLabel = new Label("Username:", skin);
        usernameField = new TextField("", skin);
        usernameField.setMessageText("Enter username");
        table.add(usernameLabel).pad(10).right();
        table.add(usernameField).pad(10).width(300);
        table.row();

        //Password
        passwordLabel = new Label("Password:", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Enter password");
        table.add(passwordLabel).pad(10).right();
        table.add(passwordField).pad(10).width(300);
        table.row();

        //Buttons
        signupButton = new TextButton("Signup", skin);
        guestButton = new TextButton("Play as Guest", skin);
        loginMenuBtn = new TextButton("Go to login menu",skin);
        float labelScale = 0.9f;
        signupButton.getLabel().setFontScale(labelScale);
        guestButton.getLabel().setFontScale(labelScale);
        loginMenuBtn.getLabel().setFontScale(labelScale);
        signupButton.pad(8, 12, 8, 12);
        guestButton.pad(8, 12, 8, 12);
        loginMenuBtn.pad(8, 12, 8, 12);

        Table buttonTable = new Table();
        buttonTable.defaults().pad(10).height(75).width(400);
        buttonTable.add(signupButton);
        buttonTable.row();
        buttonTable.add(guestButton);
        buttonTable.row();
        buttonTable.add(loginMenuBtn);
        table.add(buttonTable).colspan(2).padTop(20).center();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.draw();
    }
}
