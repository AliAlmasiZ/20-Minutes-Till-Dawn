package io.github.AliAlmasiZ.tillDawn.views.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.AliAlmasiZ.tillDawn.Main;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;
import io.github.AliAlmasiZ.tillDawn.views.GameView;
import io.github.AliAlmasiZ.tillDawn.views.PregameMenuView;

public class PregameMenuScreen extends ScreenAdapter {
    private final Main main;
    private final PregameMenuView view;

    public PregameMenuScreen(Main main) {
        this.main = main;
        this.view = new PregameMenuView(GameAssetManager.getGameAssetManager().pixthulhuuiSkin);
        view.start.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen screen = new GameScreen(main);
                main.setScreen(screen);
                AppData.getAppData().activeUser.setLastGame(screen);
                dispose();

            }
        });
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
    public void show() {
        view.show();
    }
}
