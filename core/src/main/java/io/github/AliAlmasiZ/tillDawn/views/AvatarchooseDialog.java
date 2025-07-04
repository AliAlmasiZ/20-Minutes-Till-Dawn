package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.Entities.Bullet;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;
import io.github.AliAlmasiZ.tillDawn.models.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AvatarchooseDialog extends Dialog {


    public AvatarchooseDialog(String title, Skin skin) {
        super("", skin);
        for (String avatar : GameAssetManager.getGameAssetManager().avatars) {
            Image image = new Image(new Texture(avatar));
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    AppData.getAppData().activeUser.setAvatar(avatar);
                    hide();
                }
            });
            getContentTable().add(image);
        }
        getContentTable().row();
        TextButton goback = new TextButton(Text.GO_BACK.getText(), skin);
        TextButton chooseAvatar = new TextButton(Text.CHOOSE_PICTURE.getText(), skin);
        getContentTable().add(goback).left();
        getContentTable().add(chooseAvatar).right();

        goback.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        chooseAvatar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JFileChooser chooser = new JFileChooser();
                if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String path = chooser.getSelectedFile().getPath();
                    if(!path.endsWith(".png") && !path.endsWith(".jpg")) return;
                    AppData.getAppData().activeUser.setAvatar(path);
                }
                hide();
            }
        });


    }
}
