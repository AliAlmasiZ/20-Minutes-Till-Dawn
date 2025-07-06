package io.github.AliAlmasiZ.tillDawn.views.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.AliAlmasiZ.tillDawn.Main;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;
import io.github.AliAlmasiZ.tillDawn.views.SettingMenuView;

public class SettingMenuScreen extends ScreenAdapter {
    private final Main main;
    private final SettingMenuView view;


    public SettingMenuScreen(Main main, Screen previous) {
        this.main = main;
        this.view = new SettingMenuView(GameAssetManager.getGameAssetManager().pixthulhuuiSkin);

        view.back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(previous);
                dispose();
            }
        });
    }


    @Override
    public void show() {
        view.show();
    }

    @Override
    public void resize(int width, int height) {
        view.resize(width, height);
    }

    @Override
    public void render(float delta) {
        view.render(delta);
    }

    @Override
    public void dispose() {
        view.dispose();
    }
}
