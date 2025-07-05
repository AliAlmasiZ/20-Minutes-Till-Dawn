package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.github.AliAlmasiZ.tillDawn.controllers.PauseMenuController;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;

public class PauseDialog extends Dialog {

    private PauseMenuController controller;
    public PauseDialog() {
        super("", GameAssetManager.getGameAssetManager().pixthulhuuiSkin);

        Table table = getContentTable();
        TextButton resumeGame = new TextButton(Text.RESUME.getText(), getSkin());
        TextButton settings = new TextButton(Text.SETTINGS.getText(), getSkin());
        TextButton cheatcodes = new TextButton(Text.SHOW_CHEATCODES.getText(), getSkin());
        TextButton showAbilities = new TextButton(Text.SHOW_ABILITIES.getText(), getSkin());
        TextButton exit = new TextButton(Text.EXIT.getText(), getSkin());


    }
}
