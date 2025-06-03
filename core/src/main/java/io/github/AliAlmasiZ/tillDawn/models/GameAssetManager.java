package io.github.AliAlmasiZ.tillDawn.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class GameAssetManager {
    public final AssetManager manager = new AssetManager();
    private static GameAssetManager gameAssetManager;

    //Assets
    //Skins
    public final Skin flatEarthSkin;
    public final Skin pixthulhuuiSkin;

    //Animations
    public Animation<Texture> characterIdleAnim;
    public Animation<Texture> characterRunAnim;


    //Paths
    private final String flatEarthSkinPath = "Flat_Earth_UI_Skin/flatearthui/flat-earth-ui.json";
    private final String pixthulhuuiSkinPath = "Pixthulhu_UI_Skin/pixthulhuui/pixthulhu-ui.json";
    //Character 1
    private final String character1Idle0 = "Images/Sprite/Idle_0.png";
    private final String character1Idle1 = "Images/Sprite/Idle_1 #8354.png";
    private final String character1Idle2 = "Images/Sprite/Idle_2 #8813.png";
    private final String character1Idle3 = "Images/Sprite/Idle_3.png";
    private final String character1Idle4 = "Images/Sprite/Idle_4.png";
    private final String character1Idle5 = "Images/Sprite/Idle_5.png";
    private final String character1Run0 = "Images/Sprite/Run_0 #8756.png";
    private final String character1Run1 = "Images/Sprite/Run_1 #8772.png";
    private final String character1Run2 = "Images/Sprite/Run_2.png";
    private final String character1Run3 = "Images/Sprite/Run_3.png";







    //Textures








    private GameAssetManager() {
        // Load UI skins
        manager.load(flatEarthSkinPath, Skin.class);
        manager.load(pixthulhuuiSkinPath, Skin.class);

        // Load character textures
        manager.load(character1Idle0, Texture.class);
        manager.load(character1Idle1, Texture.class);
        manager.load(character1Idle2, Texture.class);
        manager.load(character1Idle3, Texture.class);
        manager.load(character1Idle4, Texture.class);
        manager.load(character1Idle5, Texture.class);
        manager.load(character1Run0, Texture.class);
        manager.load(character1Run1, Texture.class);
        manager.load(character1Run2, Texture.class);
        manager.load(character1Run3, Texture.class);

        manager.finishLoading(); // In real game use loading screen

        // Initialize skins
        flatEarthSkin = manager.get(flatEarthSkinPath, Skin.class);
        pixthulhuuiSkin = manager.get(pixthulhuuiSkinPath, Skin.class);


        // Create animations
        Array<Texture> idleTextures = new Array<>(new Texture[]{
            manager.get(character1Idle0, Texture.class),
            manager.get(character1Idle1, Texture.class),
            manager.get(character1Idle2, Texture.class),
            manager.get(character1Idle3, Texture.class),
            manager.get(character1Idle4, Texture.class),
            manager.get(character1Idle5, Texture.class)
        });

        Array<Texture> runTextures = new Array<>(new Texture[]{
            manager.get(character1Run0, Texture.class),
            manager.get(character1Run1, Texture.class),
            manager.get(character1Run2, Texture.class),
            manager.get(character1Run3, Texture.class)
        });

        characterIdleAnim = new Animation<>(0.1f, idleTextures, Animation.PlayMode.LOOP);
        characterRunAnim = new Animation<>(0.1f, runTextures, Animation.PlayMode.LOOP);
    }

    public void dispose() {
        manager.dispose();
    }

    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null)
            gameAssetManager = new GameAssetManager();
        return gameAssetManager;
    }

}
