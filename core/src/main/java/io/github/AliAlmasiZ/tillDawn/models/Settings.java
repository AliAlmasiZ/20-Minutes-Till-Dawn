package io.github.AliAlmasiZ.tillDawn.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
    private static Settings instance;
    private final String PREFS_NAME = "GameSettings";
    private final Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);





    public float sfxVolume;
    public float musicVolume;
    //TODO: music
    public boolean autoReload;
    public boolean blackWhite;


    private Settings() {
        sfxVolume = prefs.getFloat(Fields.SFX_VOLUME.name(), 1f);
        musicVolume = prefs.getFloat(Fields.MUSIC_VOLUME.name(), 1f);
        autoReload = prefs.getBoolean(Fields.AUTO_RELOAD.name(), false);
        blackWhite = prefs.getBoolean(Fields.BLACK_N_WHITE.name(), false);
        savePrefs();
    }

    public void savePrefs() {
        prefs.putFloat(Fields.MUSIC_VOLUME.name(), musicVolume);
        prefs.putFloat(Fields.SFX_VOLUME.name(), sfxVolume);
        prefs.putBoolean(Fields.BLACK_N_WHITE.name(), blackWhite);
        prefs.putBoolean(Fields.AUTO_RELOAD.name(), autoReload);

        prefs.flush();
    }

    public static Settings getInstance() {
        if(instance == null)
            instance = new Settings();
        return instance;
    }

    public enum Fields {
        SFX_VOLUME,
        MUSIC_VOLUME,
        AUTO_RELOAD,
        BLACK_N_WHITE
        ;
    }


}

