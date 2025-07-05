package io.github.AliAlmasiZ.tillDawn.models.DataBase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.AliAlmasiZ.tillDawn.models.Player;
import io.github.AliAlmasiZ.tillDawn.models.User;

public class JsonSaver {
    private static JsonSaver instance;
    private final Json json = new Json();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    private JsonSaver() {}
    public static JsonSaver getInstance() {
        if (instance == null)
            instance = new JsonSaver();
        return instance;
    }

    public void savePlayer(Player player, User user) {
        String jsonText = gson.toJson(player);
        FileHandle file = Gdx.files.local("data/player" + user.getId() + ".json");
        file.writeString(jsonText, false);
    }

    public void saveUser(User user) {
        String jsonText = gson.toJson(user);
        FileHandle file = Gdx.files.local("data/user" + user.getId() + ".json");
        file.writeString(jsonText, false);
    }

    public Player loadPlayer(User user) {
        FileHandle file = Gdx.files.local("data/player" + user.getId() + ".json");
        if(file.exists()) {
            return gson.fromJson(file.readString(), Player.class);
        } else {
            return new Player();
        }
    }



}
