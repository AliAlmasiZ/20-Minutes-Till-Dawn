package io.github.AliAlmasiZ.tillDawn.controllers;

import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.DataBaseManager;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.PlayerDAO;
import io.github.AliAlmasiZ.tillDawn.models.Player;
import io.github.AliAlmasiZ.tillDawn.models.Result;

import java.sql.SQLException;

public class SignupMenuController {
    public Result signup(String username, String password, String securityAnswer) {
//        try {
//            DataBaseManager.connect();
//            DataBaseManager.initializeFromSchema();
            PlayerDAO playerDAO = new PlayerDAO(DataBaseManager.getConnection());
            Player p = playerDAO.loadPlayer(username);
            if (p != null) {
                return new Result(false, "User Already exists!");
            }
            Player player = new Player(username, password, securityAnswer);
            AppData.getAppData().setActivePlayer(player);
            playerDAO.savePlayer(player);
            return new Result(true, "User with username \"" + username + "\" saved successfully!");
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//            return new Result(false, "something wrong happened!");
//        }
    }

    public Result forgetPass() {
        return null;
    }
}
