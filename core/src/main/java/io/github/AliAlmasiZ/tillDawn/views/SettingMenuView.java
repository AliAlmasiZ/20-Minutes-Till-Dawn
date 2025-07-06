package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.AliAlmasiZ.tillDawn.Main;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.GameData;
import io.github.AliAlmasiZ.tillDawn.models.Settings;
import io.github.AliAlmasiZ.tillDawn.models.enums.MusicTrack;
import io.github.AliAlmasiZ.tillDawn.views.dialogs.InputSettingsDialog;

public class SettingMenuView implements Disposable {
    private final Stage stage;
    private final Table table;
    private final InputSettingsDialog inputDialog;
    public Slider sfxSlider, musicSlider;
    public CheckBox autoReload, blackAndWhite;
    public SelectBox<MusicTrack> musicTrackSelection;
    public TextButton back;


    public SettingMenuView(Skin skin) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.center();

        stage.addActor(table);

        musicTrackSelection = new SelectBox<>(skin);
        musicTrackSelection.setItems(MusicTrack.values());
        musicTrackSelection.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.getInstance().musicTrack = musicTrackSelection.getSelected();
                Settings.getInstance().savePrefs();
                Main.getInstance().playMusic();
            }
        });

        Label sfxLabel = new Label(Text.SFX_VOLUME.getText() ,skin);
        Label musicLabel = new Label(Text.MUSIC_VOLUME.getText(), skin);
        Label musicTrackLabel = new Label(Text.MUSIC_TRACK.getText(), skin);
        TextButton changeInputButtons = new TextButton(Text.CHANGE_INPUTS.getText(), skin);



        sfxSlider = new Slider(0, 100, 1, false, skin);
        musicSlider = new Slider(0, 100, 1, false, skin);
        autoReload = new CheckBox(Text.AUTO_RELOAD.getText(), skin);
        blackAndWhite = new CheckBox(Text.BLACK_AND_WHITE.getText(), skin);
        back = new TextButton(Text.GO_BACK.getText(), skin);
        inputDialog = new InputSettingsDialog(skin);



        sfxSlider.setValue(100 * Settings.getInstance().sfxVolume);
        musicSlider.setValue(100 * Settings.getInstance().musicVolume);
        autoReload.setChecked(Settings.getInstance().autoReload);
        blackAndWhite.setChecked(Settings.getInstance().blackWhite);

        sfxSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.getInstance().sfxVolume = sfxSlider.getValue() / 100;
                Settings.getInstance().savePrefs();
            }
        });
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getInstance().playMusic();
                Settings.getInstance().musicVolume = musicSlider.getValue() / 100;
                Settings.getInstance().savePrefs();
            }
        });
        blackAndWhite.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.getInstance().blackWhite = blackAndWhite.isChecked();
                Settings.getInstance().savePrefs();
            }
        });
        autoReload.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.getInstance().autoReload = autoReload.isChecked();
                Settings.getInstance().savePrefs();
            }
        });
        changeInputButtons.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inputDialog.show(stage);
            }
        });





        table.add(sfxLabel).left().pad(10);
        table.add(sfxSlider).right().growX().pad(10).row();
        table.add(musicLabel).left().pad(10);
        table.add(musicSlider).right().growX().pad(10).row();
        table.add(autoReload).center().pad(20);
        table.add(blackAndWhite).center().pad(20).row();
        table.add(musicTrackLabel).left().pad(20);
        table.add(musicTrackSelection).row();
        table.add(changeInputButtons).center().pad(30);
        table.add(back).center().pad(30).row();






    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
