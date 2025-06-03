package io.github.AliAlmasiZ.tillDawn.views.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.AliAlmasiZ.tillDawn.Main;
import io.github.AliAlmasiZ.tillDawn.controllers.utils.ControlsManager;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.Entities.Bullet;
import io.github.AliAlmasiZ.tillDawn.models.Entities.Enemy;
import io.github.AliAlmasiZ.tillDawn.models.Entities.XPOrb;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;
import io.github.AliAlmasiZ.tillDawn.models.Player;
import io.github.AliAlmasiZ.tillDawn.models.enums.GameAction;


import java.util.Map;

public class GameScreen extends ScreenAdapter {
    private final Main main;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Player player;
    private Texture playerTexture;
    private Texture enemyTexture;
    private Texture bulletTexture;
    private Texture xpOrbTexture;
    private Texture backgroundTexture;

    private Array<Enemy> enemies;
    private Array<Bullet> bullets;
    private Array<XPOrb> xpOrbs;

    private long lastEnemySpawnTime;
    private float enemySpawnInterval = 2000;

    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    private boolean isPaused = false;
    private boolean gameOver = false;
    private int score = 0;
    private float gameTimer = 0;
    private final float MAX_GAME_TIME = 20 * 60;

    private Music gameMusic;
    private Sound shootSound;
    private Sound enemyHitSound;
    private Sound playerHitSound;
    private Sound xpPickupSound;
    private Sound levelUpSound;

    private static final float GAME_WORLD_WIDTH = 1600;
    private static final float GAME_WORLD_HEIGHT = 900;


    public GameScreen(Main main) {
        this.main = main;
        batch = main.batch;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();

        viewport = new FitViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera);
        camera.setToOrtho(false, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);


        playerTexture = new Texture(GameAssetManager.getGameAssetManager().getCharacter1Idle0());
//        enemyTexture = new Texture(GameAssetManager.getGameAssetManager().getCharacter1Idle0())


        player = AppData.getAppData().activeUser.getPlayer();
        player.getPosition().set(GAME_WORLD_WIDTH / 2f - playerTexture.getWidth() / 2f,
            GAME_WORLD_HEIGHT / 2f - playerTexture.getHeight() / 2f);

        enemies = new Array<>();
        bullets = new Array<>();
        xpOrbs = new Array<>();
        shapeRenderer = new ShapeRenderer();


        lastEnemySpawnTime = TimeUtils.millis();

    }

    private void handleInput(float delta) {
        if(gameOver) return;

        if(Gdx.input.isKeyJustPressed(ControlsManager.getKeyForAction(GameAction.PAUSE)) ||
            Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
        }

        if(isPaused) {
            if(player != null) player.setMoving(false);
            return;
        }

        if(player == null)
            return;

        boolean movedX = false;
        boolean movedY = false;
        float moveSpeed = player.getSpeed() * delta;

        if (Gdx.input.isKeyPressed(ControlsManager.getKeyForAction(GameAction.MOVE_UP))) {
            player.position.y += moveSpeed;
            movedY = true;
        }
        if (Gdx.input.isKeyPressed(ControlsManager.getKeyForAction(GameAction.MOVE_DOWN))) {
            player.position.y -= moveSpeed;
            movedY = true;
        }
        if (Gdx.input.isKeyPressed(ControlsManager.getKeyForAction(GameAction.MOVE_LEFT))) {
            player.position.x -= moveSpeed;
            movedX = true;
        }
        if (Gdx.input.isKeyPressed(ControlsManager.getKeyForAction(GameAction.MOVE_RIGHT))) {
            player.position.x += moveSpeed;
            movedX = true;
        }

        player.isMoving = movedX || movedY;

        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(mousePos);

        float playerCenterX = player.position.x + playerTexture.getWidth() / 2f;
        float playerCenterY = player.position.y + playerTexture.getHeight() / 2f;

        float angleRadians = MathUtils.atan2(mousePos.y - playerCenterY, mousePos.x - playerCenterX);
        float angleDegrees = angleRadians * MathUtils.radiansToDegrees;


    }
}

