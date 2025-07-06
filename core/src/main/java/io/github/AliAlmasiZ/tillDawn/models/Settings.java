package io.github.AliAlmasiZ.tillDawn.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import io.github.AliAlmasiZ.tillDawn.models.enums.HeroType;
import io.github.AliAlmasiZ.tillDawn.models.enums.MusicTrack;
import io.github.AliAlmasiZ.tillDawn.models.enums.WeaponType;

public class Settings {
    private static Settings instance;
    private final String PREFS_NAME = "GameSettings";
    private final Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);





    public float sfxVolume;
    public float musicVolume;
    public MusicTrack musicTrack;
    public boolean autoReload;
    public boolean blackWhite;
    public Time gameTime;
    public WeaponType weaponType;
    public HeroType heroType;




    private Settings() {
        sfxVolume = prefs.getFloat(Fields.SFX_VOLUME.name(), 1f);
        musicVolume = prefs.getFloat(Fields.MUSIC_VOLUME.name(), 1f);
        autoReload = prefs.getBoolean(Fields.AUTO_RELOAD.name(), false);
        blackWhite = prefs.getBoolean(Fields.BLACK_N_WHITE.name(), false);
        musicTrack = MusicTrack.valueOf(prefs.getString(Fields.MUSIC_TRACK.name(), MusicTrack.StrangerThings.name()));
        gameTime = Time.valueOf(prefs.getString(Fields.TIME.name(), Time.FIVE.name()));
        heroType = HeroType.valueOf(prefs.getString(Fields.HERO.name(), HeroType.SHANA.name()));
        weaponType = WeaponType.valueOf(prefs.getString(Fields.WEOPON.name(), WeaponType.REVOLVER.name()));

        savePrefs();
    }

    public void savePrefs() {
        prefs.putFloat(Fields.MUSIC_VOLUME.name(), musicVolume);
        prefs.putFloat(Fields.SFX_VOLUME.name(), sfxVolume);
        prefs.putBoolean(Fields.BLACK_N_WHITE.name(), blackWhite);
        prefs.putBoolean(Fields.AUTO_RELOAD.name(), autoReload);
        prefs.putString(Fields.MUSIC_TRACK.name(), musicTrack.name());
        prefs.putString(Fields.TIME.name(), gameTime.name());
        prefs.putString(Fields.HERO.name(), heroType.name());
        prefs.putString(Fields.WEOPON.name(), weaponType.name());

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
        BLACK_N_WHITE,
        MUSIC_TRACK,
        TIME,
        HERO,
        WEOPON
        ;
    }

    public enum Time {
        TWO(2),
        FIVE(5),
        TEN(10),
        TWENTY(20);

        public int minutes;

        Time(int minutes) {
            this.minutes = minutes;
        }
    }


}

