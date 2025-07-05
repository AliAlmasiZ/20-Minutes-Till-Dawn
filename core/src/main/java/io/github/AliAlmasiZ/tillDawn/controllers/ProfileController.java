package io.github.AliAlmasiZ.tillDawn.controllers;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.views.ProfileMenuView;

public class ProfileController {

    private ProfileMenuView view;

    public ProfileController(ProfileMenuView view) {
        this.view = view;
    }

    public void setAvatar(String path) {
        AppData.getAppData().getActiveUser().setAvatar(path);
        view.avatar.setDrawable(new TextureRegionDrawable(AppData.getAppData().getActiveUser().getAvatarTex()));
    }
}
