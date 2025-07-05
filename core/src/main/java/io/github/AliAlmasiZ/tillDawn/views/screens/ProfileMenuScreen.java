package io.github.AliAlmasiZ.tillDawn.views.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import io.github.AliAlmasiZ.tillDawn.Main;
import io.github.AliAlmasiZ.tillDawn.controllers.ProfileController;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;
import io.github.AliAlmasiZ.tillDawn.views.ProfileMenuView;

public class ProfileMenuScreen implements Screen {
    private final Main main;
    private final ProfileMenuView view = new ProfileMenuView(GameAssetManager.getGameAssetManager().pixthulhuuiSkin);
    private final ProfileController controller;

    public ProfileMenuScreen(Main main) {
        this.main = main;
        this.controller = new ProfileController(view);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        view.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        view.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void setAvatar(String path) {
        controller.setAvatar(path);
    }
}
