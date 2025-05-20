package io.github.AliAlmasiZ.tillDawn.models.DataBase;

import io.github.AliAlmasiZ.tillDawn.models.Player;

import java.util.ArrayList;
import java.util.List;

public class AppData {
    private static AppData appData;

    private List<Player> allPlayers;

    private AppData() {
        allPlayers = new ArrayList<>();
    }

    public static AppData getAppData() {
        if(appData == null)
            appData = new AppData();
        return appData;
    }




}
