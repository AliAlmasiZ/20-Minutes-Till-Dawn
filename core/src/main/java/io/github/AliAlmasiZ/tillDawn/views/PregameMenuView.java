package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.AliAlmasiZ.tillDawn.models.Settings;
import io.github.AliAlmasiZ.tillDawn.models.enums.HeroType;
import io.github.AliAlmasiZ.tillDawn.models.enums.WeaponType;

public class PregameMenuView {
    Stage stage;
    Table table;
    public TextButton start;

    public PregameMenuView(Skin skin) {
        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        start = new TextButton(Text.START_GAME.getText(), skin);

        Label heroLabel = new Label(Text.HERO.getText(), skin);
        SelectBox<HeroType> heroSelectBox= new SelectBox<>(skin);
        heroSelectBox.setItems(HeroType.values());
        heroSelectBox.setSelected(Settings.getInstance().heroType);
        heroSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.getInstance().heroType = heroSelectBox.getSelected();
                Settings.getInstance().savePrefs();
            }
        });
        Label weaponLabel = new Label(Text.WEAPON.getText(), skin);
        SelectBox<WeaponType> weaponSelectBox = new SelectBox<>(skin);
        weaponSelectBox.setItems(WeaponType.values());
        weaponSelectBox.setSelected(Settings.getInstance().weaponType);
        weaponSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.getInstance().weaponType = weaponSelectBox.getSelected();
                Settings.getInstance().savePrefs();
            }
        });
        Label gameDurationLabel = new Label(Text.DURATION.getText(), skin);
        SelectBox<Settings.Time> timeSelectBox = new SelectBox<>(skin);
        timeSelectBox.setItems(Settings.Time.values());
        timeSelectBox.setSelected(Settings.getInstance().gameTime);
        timeSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.getInstance().gameTime = timeSelectBox.getSelected();
                Settings.getInstance().savePrefs();
            }
        });



        table.add(start).colspan(2).row();
        table.add(heroLabel).pad(20);
        table.add(heroSelectBox).pad(20).row();
        table.add(weaponLabel).pad(20);
        table.add(weaponSelectBox).pad(20).row();
        table.add(gameDurationLabel).pad(20);
        table.add(timeSelectBox).pad(20).row();
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {

    }


}
