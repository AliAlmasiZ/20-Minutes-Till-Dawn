package io.github.AliAlmasiZ.tillDawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.AliAlmasiZ.tillDawn.controllers.MainMenuController;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;
import io.github.AliAlmasiZ.tillDawn.models.Settings;
import io.github.AliAlmasiZ.tillDawn.models.User;
import io.github.AliAlmasiZ.tillDawn.models.enums.MusicTrack;
import io.github.AliAlmasiZ.tillDawn.views.MainMenuView;
import io.github.AliAlmasiZ.tillDawn.views.screens.GameScreen;
import io.github.AliAlmasiZ.tillDawn.views.screens.MainMenuScreen;
import io.github.AliAlmasiZ.tillDawn.views.screens.SignUpMenuScreen;

import java.util.Set;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main instance;
    private ShaderProgram grayscaleShader;
    private FrameBuffer frameBuffer;

    public SpriteBatch batch;
    private static Music music;

    @Override
    public void create() {
        playMusic();
        //TODO: for debug uncomment bellow code block :
        /*{
            AppData.getAppData().setActiveUser(new User("Ali", "pass", "meow"));
        }*/

        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight(), false);

        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shaders/grayscale.vert"),
            Gdx.files.internal("shaders/grayscale.frag")
        );

        if(!grayscaleShader.isCompiled())
            Gdx.app.error("Shader", "Failed to compile grayscale shader: " + grayscaleShader.getLog());

        instance = this;
        batch = new SpriteBatch();
        setScreen(new SignUpMenuScreen(this));
    }


    @Override
    public void render() {
        frameBuffer.begin();
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
        frameBuffer.end();
        if(Settings.getInstance().blackWhite) //TODO
            batch.setShader(grayscaleShader);
        batch.begin();
        batch.draw(frameBuffer.getColorBufferTexture(),
            0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
            0, 0, 1, 1);
        batch.end();

        batch.setShader(null);
    }

    @Override
    public void dispose() {
        screen.dispose();
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    public static Main getInstance() {
        return instance;
    }

    public void playMusic() {
        float pos = 0;
        if(music != null && music.isPlaying()) {
            pos = music.getPosition();
            music.stop();
            music.dispose();
        }
        music = Gdx.audio.newMusic(Gdx.files.internal(Settings.getInstance().musicTrack.getPath()));
        music.setVolume(Settings.getInstance().musicVolume);
        music.setPosition(pos);
        music.play();


    }

}
