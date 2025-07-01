package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class LoginMenuView {
    private Stage stage;
    private Table mainTable;
    private Table forgetPassTable;
    public TextField usernameField, passwordField, securityQuestionField;
    public TextButton loginButton, signupBtn, forgetPassBtn, submitBtn;

    public LoginMenuView(Skin skin) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        float height = stage.getViewport().getWorldHeight();
        float width = stage.getViewport().getWorldWidth();

        initiateMainTable(skin);

    }

    private void initiateMainTable(Skin skin) {
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();
        mainTable.pad(40);

        stage.addActor(mainTable);

        //Title
        Label title = new Label("Login", skin);
        title.setFontScale(1.5f);
        mainTable.add(title).colspan(2).padBottom(20);
        mainTable.row();

        //Username
        Label usernameLabel = new Label("Username:", skin);
        usernameField = new TextField("", skin);
        usernameField.setMessageText("Enter username");
        mainTable.add(usernameLabel).pad(10).right();
        mainTable.add(usernameField).pad(10).width(300);
        mainTable.row();

        //Pass
        Label passwordLabel = new Label("Password:", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Enter password");
        mainTable.add(passwordLabel).pad(10).right();
        mainTable.add(passwordField).pad(10).width(300);
        mainTable.row();

        //Buttons
        loginButton = new TextButton("Login", skin);
        signupBtn = new TextButton("Sign Up", skin);
        forgetPassBtn = new TextButton("Forget Password", skin);

//        forgetPassBtn.getLabel().setFontScale(0.75f);


        Table buttonTable = new Table();
        buttonTable.defaults().pad(10).height(75).width(300);
        buttonTable.add(loginButton);
        buttonTable.add(signupBtn);
        buttonTable.row();
        buttonTable.add(forgetPassBtn).colspan(2).width(600);
        mainTable.add(buttonTable).colspan(2).padTop(20).center();
    }

    private void initiateForgetPassTable(Skin skin) {
        forgetPassTable = new Table();
        forgetPassTable.setFillParent(true);
        forgetPassTable.setVisible(false);
        forgetPassTable.center();
        forgetPassTable.pad(40);

        securityQuestionField = new TextField("", skin);
        securityQuestionField.setMessageText("Enter your security answer");

        submitBtn = new TextButton("Submit", skin);

        forgetPassTable.add(securityQuestionField);
        forgetPassTable.row();
        forgetPassTable.add(submitBtn);

        stage.addActor(forgetPassTable);
    }


    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
