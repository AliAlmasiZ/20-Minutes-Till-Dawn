package io.github.AliAlmasiZ.tillDawn.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.AliAlmasiZ.tillDawn.Main;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.Result;
import io.github.AliAlmasiZ.tillDawn.models.User;
import io.github.AliAlmasiZ.tillDawn.views.ProfileMenuView;
import io.github.AliAlmasiZ.tillDawn.views.screens.SignUpMenuScreen;

import javax.management.InstanceAlreadyExistsException;

public class ProfileController {

    private ProfileMenuView view;

    public ProfileController(ProfileMenuView view) {
        this.view = view;
    }

    public void setAvatar(String path) {
        AppData.getAppData().getActiveUser().setAvatar(path);
        view.avatar.setDrawable(new TextureRegionDrawable(AppData.getAppData().getActiveUser().getAvatarTex()));
    }

    public Result changeUsername() {
        String username = view.usernameField.getText();
        if(AppData.getAppData().activeUser.getUsername().equals(username))
            return new Result(false, "");
        if(AppData.getAppData().getUserByUsername(username) != null)
            return new Result(false, "username already exists");
        User activeUser = AppData.getAppData().getActiveUser();
        AppData.getAppData().deleteUser(activeUser);
        activeUser.setUsername(username);
        try {
            AppData.getAppData().addUser(activeUser);
        } catch (InstanceAlreadyExistsException e) {
            Gdx.app.error("Username", "change failed " + e.getMessage());
        }
        return new Result(true, "username changed");
    }

    public Result changePassword() {
        String password = view.passwordField.getText();
        Result result = (new SignupMenuController()).checkPassword(password);
        if (!result.isSuccessful())
            return result;
        User activeUser = AppData.getAppData().getActiveUser();
        AppData.getAppData().deleteUser(activeUser);
        activeUser.setPassword(password);
        try {
            AppData.getAppData().addUser(activeUser);
        } catch (InstanceAlreadyExistsException e) {
            Gdx.app.error("Password", "cant change " + e.getMessage());
        }
        return new Result(true, "password changed");
    }

    public Result deleteAccount() {
        User activeUser = AppData.getAppData().getActiveUser();
        AppData.getAppData().deleteUser(activeUser);
        activeUser.delete();
        Main.getInstance().setScreen(new SignUpMenuScreen(Main.getInstance()));
        AppData.getAppData().setActiveUser(null);
        return new Result(true, "user deleted");
    }
}
