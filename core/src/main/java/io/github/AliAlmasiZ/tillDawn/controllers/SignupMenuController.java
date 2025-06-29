package io.github.AliAlmasiZ.tillDawn.controllers;

import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.User;
import io.github.AliAlmasiZ.tillDawn.models.Result;

import javax.management.InstanceAlreadyExistsException;

public class SignupMenuController {
    /*public Result signup(String username, String password, String securityAnswer) {
//        try {
//            DataBaseManager.connect();
//            DataBaseManager.initializeFromSchema();
            /*PlayerDAO playerDAO = new PlayerDAO(DataBaseManager.getConnection());
            User p = playerDAO.loadPlayer(username);
            if (p != null) {
                return new Result(false, "User Already exists!");
            }
            User user = new User(username, password, securityAnswer);
            AppData.getAppData().setActiveUser(user);
            playerDAO.savePlayer(user);
            return new Result(true, "User with username \"" + username + "\" saved successfully!");




        //TODO: for DEBUG : remove below line and uncomment above lines(after fix save and load users)
        {
            Result result = new Result(true, "signup error");
            return result;
        }

//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//            return new Result(false, "something wrong happened!");
//        }
    }
    */

    public Result signup(String username, String password, String securityAnswer) {

        if(AppData.getAppData().getUserByUsername(username) != null) {
            return new Result(false, "user already exists!");
        }
        try {
            User user = new User(username, password, securityAnswer);
            AppData.getAppData().addUser(user);
            AppData.getAppData().setActiveUser(user);
            return new Result(true, "user created successfully!");
        } catch (InstanceAlreadyExistsException e) {
            return new Result(false, e.getMessage());
        }
    }

    public Result forgetPass() {
        return null;
    }
}
