package io.github.AliAlmasiZ.tillDawn.controllers;

import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.DataBaseManager;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.PlayerDAO;
import io.github.AliAlmasiZ.tillDawn.models.User;
import io.github.AliAlmasiZ.tillDawn.models.Result;

public class LoginMenuController {
    /*public Result login(String username, String password) {
        PlayerDAO playerDAO = new PlayerDAO(DataBaseManager.getConnection());
        User user = playerDAO.loadPlayer(username);

        if(!user.getPassword().equals(password)) {
            return new Result(false, "Wrong Password");
        }

        AppData.getAppData().setActiveUser(user);
        return new Result(true, "logged in successfully");
    }*/

    public Result login(String username, String password) {
        User user = AppData.getAppData().getUserByUsername(username);
        if(user == null)
            return new Result(false, "there is no user with this username!");
        if(!user.getPassword().equals(password))
            return new Result(false, "password is incorrect!");
        AppData.getAppData().setActiveUser(user);
        return new Result(true, "user logged in successfully!");
    }

    public Result forgetPass() {
        return null;
    }
}
