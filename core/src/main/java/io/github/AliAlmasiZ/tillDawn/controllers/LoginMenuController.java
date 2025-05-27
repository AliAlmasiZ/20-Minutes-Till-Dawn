package io.github.AliAlmasiZ.tillDawn.controllers;

import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.DataBaseManager;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.PlayerDAO;
import io.github.AliAlmasiZ.tillDawn.models.Player;
import io.github.AliAlmasiZ.tillDawn.models.Result;

public class LoginMenuController {
    public Result login(String username, String password) {
        PlayerDAO playerDAO = new PlayerDAO(DataBaseManager.getConnection());
        Player player = playerDAO.loadPlayer(username);

        if(!player.getPassword().equals(password)) {
            return new Result(false, "Wrong Password");
        }

        AppData.getAppData().setActivePlayer(player);
        return new Result(true, "logged in successfully");
    }
}
