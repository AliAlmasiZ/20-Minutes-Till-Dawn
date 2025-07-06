package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.AliAlmasiZ.tillDawn.models.enums.HeroType;

public class PregameMenuView {
    Stage stage;
    Table table;

    public PregameMenuView(Skin skin) {
        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton start = new TextButton(Text.START_GAME.getText(), skin);
        Label heroLabel = new Label(Text.HERO.getText(), skin);
        SelectBox<HeroType> heroSelectBox= new SelectBox<>(skin);
        heroSelectBox.setItems(HeroType.values());
        Label weaponLabel = new Label(Text.WEAPON.getText(), skin);
        Label gameDurationLabel = new Label(Text.DURATION.getText(), skin);


//        table.add()
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }


}
