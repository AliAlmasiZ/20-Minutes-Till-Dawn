package io.github.AliAlmasiZ.tillDawn.views.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.AliAlmasiZ.tillDawn.Main;
import io.github.AliAlmasiZ.tillDawn.controllers.PauseMenuController;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;
import io.github.AliAlmasiZ.tillDawn.views.Text;
import io.github.AliAlmasiZ.tillDawn.views.screens.GameScreen;
import io.github.AliAlmasiZ.tillDawn.views.screens.MainMenuScreen;
import io.github.AliAlmasiZ.tillDawn.views.screens.SettingMenuScreen;

public class PauseDialog extends Dialog {

    private PauseMenuController controller;
    public PauseDialog(GameScreen screen) {
        super("", GameAssetManager.getGameAssetManager().pixthulhuuiSkin);

        Table table = getContentTable();
        TextButton resumeGame = new TextButton(Text.RESUME.getText(), getSkin());
        TextButton settings = new TextButton(Text.SETTINGS.getText(), getSkin());
        TextButton cheatcodes = new TextButton(Text.SHOW_CHEATCODES.getText(), getSkin());
        TextButton showAbilities = new TextButton(Text.SHOW_ABILITIES.getText(), getSkin());
        TextButton giveUp = new TextButton(Text.GIVE_UP.getText(), getSkin());
        TextButton save = new TextButton(Text.SAVE_AND_EXIT.getText(), getSkin());

        table.add(resumeGame).center().row();
        table.add(settings).center().row();
        table.add(cheatcodes).center().row();
        table.add(showAbilities).center().row();
        table.add(giveUp).center().row();
        table.add(save);




        resumeGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.isPaused = false;
                hide();
            }
        });
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.hide();
                Main.getInstance().setScreen(new SettingMenuScreen(Main.getInstance(), screen));

            }
        });
        cheatcodes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        showAbilities.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        giveUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                screen.setGameOver();
            }
        });
        save.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AppData.getAppData().activeUser.setLastGame(screen);
                screen.hide();
                Main.getInstance().setScreen(new MainMenuScreen(Main.getInstance()));
                hide();
            }
        });


    }
}
