package io.github.AliAlmasiZ.tillDawn.views.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.AliAlmasiZ.tillDawn.Main;
import io.github.AliAlmasiZ.tillDawn.controllers.GameController;
import io.github.AliAlmasiZ.tillDawn.controllers.utils.ControlsManager;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.GameData;
import io.github.AliAlmasiZ.tillDawn.models.Entities.Bullet;
import io.github.AliAlmasiZ.tillDawn.models.Entities.Enemy;
import io.github.AliAlmasiZ.tillDawn.models.Entities.Tree;
import io.github.AliAlmasiZ.tillDawn.models.Entities.XPOrb;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;
import io.github.AliAlmasiZ.tillDawn.models.Player;
import io.github.AliAlmasiZ.tillDawn.models.Settings;
import io.github.AliAlmasiZ.tillDawn.models.User;
import io.github.AliAlmasiZ.tillDawn.models.enums.AbilityType;
import io.github.AliAlmasiZ.tillDawn.models.enums.EnemyType;
import io.github.AliAlmasiZ.tillDawn.models.enums.GameAction;
import io.github.AliAlmasiZ.tillDawn.models.enums.WeaponType;
import io.github.AliAlmasiZ.tillDawn.views.DeathAnimation;
import io.github.AliAlmasiZ.tillDawn.views.HitAnimation;
import io.github.AliAlmasiZ.tillDawn.views.Text;
import io.github.AliAlmasiZ.tillDawn.views.dialogs.GameOverDialog;
import io.github.AliAlmasiZ.tillDawn.views.dialogs.PauseDialog;
import io.github.AliAlmasiZ.tillDawn.views.dialogs.SelectAbilityDialog;

import java.util.HashSet;


public class GameScreen extends ScreenAdapter {
    private final Main main;
    private final GameController controller;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    public Viewport viewport;
    public Player player;

    private Texture treeTexture;
    private Animation<TextureRegion> tentacleMonsterAnim;
    private Animation<TextureRegion> eyebatAnim;
    private Animation<TextureRegion> elderBossAnim;



//    private Texture playerTexture;
//    private Animation<TextureRegion> enemyAnim;
    public Texture playerBulletTexture;
    private Texture enemyBulletTexture;
    private Texture xpOrbTexture;
    private Texture backgroundTexture;
    private Texture lightingOverlayTexture;

    Array<Tree> trees;
    private Array<Enemy> enemies;
    public Array<Bullet> playerBullets;
    private Array<Bullet> enemyBullets;
    private Array<XPOrb> xpOrbs;
    private Array<DeathAnimation> deathAnimations;
    private Array<HitAnimation> hitAnimations;

    private HashSet<Long> populatedTreeCells;
    private static final int TREE_CELL_SIZE = 1600;

    // Spawning timers
//    private long lastEnemySpawnTime;
    private long lastTentacleSpawnTime;
    private float tentacleMonsterSpawnRate = 3000;
    private static final float TENTACLE_MONSTER_SPAWN_DELAY = 2000;

    private long lastEyebatSpawnTime;
    private float eyebatSpawnRate = 10000;
    private boolean canSpawnEyebats = false;

    private boolean elderBossHasSpawned = false;


//    private float enemySpawnInterval = 2000;

    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    public boolean isPaused = false;
    public boolean gameOver = false;
    public boolean isAmmoCheatActive = false;
    private int score = 0, kill = 0;
    public float gameTimer = 0;
    public final float MAX_GAME_TIME = Settings.getInstance().gameTime.minutes * 60;

    private Sound shootSound;
    private Sound enemyHitSound;
    private Sound playerHitSound;
    private Sound xpPickupSound;
    private Sound levelUpSound;

    // UI Stage and elements
    public Stage uiStage;
    private Table uiTableRoot;
    private Label scoreLabel;
    private Label killLabel;
    private Label timeLabel;
    private Label healthLabel;
    private Label levelLabel;
    private Label weaponLabel;
    private Label ammoLabel;

    public PauseDialog pauseDialog;
    private GameOverDialog gameOverDialog;
    public SelectAbilityDialog selectAbilityDialog;

    private Label autoAimStatusLabel;

    public boolean autoAimEnabled = false;

    private ProgressBar xpBar;
    private Label xpLabel;
    private Texture progressBarBgTex, progressBarKnobTex;

    private Pixmap cursorPixmap;
    private Cursor customCursor;


    private static final float GAME_WORLD_WIDTH = 1920;
    private static final float GAME_WORLD_HEIGHT = 1080;


    public GameScreen(GameData data) {
        this(Main.getInstance());

    }

    public GameScreen(Main main) {
        this.main = main;
        batch = main.batch;
        this.controller = new GameController(this);

        trees = new Array<>();
        enemies = new Array<>();
        playerBullets = new Array<>();
        enemyBullets = new Array<>();
        xpOrbs = new Array<>();
        deathAnimations = new Array<>();
        hitAnimations = new Array<>();
        shapeRenderer = new ShapeRenderer();

        populatedTreeCells = new HashSet<>();

        player = AppData.getAppData().activeUser.getPlayer();
        player.setMaxHealth();
        player.setBaseSpeed();
        player.equipWeapon(Settings.getInstance().weaponType);


    }

    @Override
    public void show() {


        ControlsManager.refreshControls();

//        viewport = new FitViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera);
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.setToOrtho(false, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        Gdx.input.setCursorCatched(false);



        treeTexture = GameAssetManager.getGameAssetManager().treeTex;
        tentacleMonsterAnim = GameAssetManager.getGameAssetManager().brainMonsterAnim;
        eyebatAnim = GameAssetManager.getGameAssetManager().eyebatMonsterAnim;
        elderBossAnim = GameAssetManager.getGameAssetManager().elderBossAnim;
        playerBulletTexture = GameAssetManager.getGameAssetManager().bulletTex;
        enemyBulletTexture = GameAssetManager.getGameAssetManager().bulletTex;
        xpOrbTexture = GameAssetManager.getGameAssetManager().xpOrbTex;
        backgroundTexture = GameAssetManager.getGameAssetManager().backgroundTileTex;
        lightingOverlayTexture = createLightingOverlay();

//        enemyTexture = new Texture(GameAssetManager.getGameAssetManager().getCharacter1Idle0())




        if (player != null) {
            float pWidth = player.getWidth();
            float pHeight = player.getHeight();
            player.position.set(
                GAME_WORLD_WIDTH / 2f - pWidth / 2f,
                GAME_WORLD_HEIGHT / 2f - pHeight / 2f
            );
            player.update(0);
        }

        spawnTreesAroundPlayer();


        FreeTypeFontGenerator generator = null;
        try {
            font = new BitmapFont(Gdx.files.internal(GameAssetManager.getGameAssetManager().CHEVY_RAY_EXPRESS));

        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Could not load bitmap font 'fonts/yourfont.fnt'. Make sure the .fnt and .png files are in assets/fonts/", e);
            font = new BitmapFont();
            font.setColor(Color.RED);
        } finally {
            if(generator != null)
                generator.dispose();
        }

        // --- UI STAGE SETUP ---
        uiStage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(uiStage);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);


        Pixmap bgPixmap = new Pixmap(100, 15, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(Color.DARK_GRAY);
        bgPixmap.fill();
        progressBarBgTex = new Texture(bgPixmap);
        bgPixmap.dispose();

        Pixmap knobPixmap = new Pixmap(1 , 15, Pixmap.Format.RGBA8888);
        knobPixmap.setColor(Color.CYAN);
        knobPixmap.fill();
        progressBarKnobTex = new Texture(knobPixmap);
        knobPixmap.dispose();

        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = new TextureRegionDrawable(new TextureRegion(progressBarBgTex));
        progressBarStyle.knobBefore = new TextureRegionDrawable(new TextureRegion(progressBarKnobTex));



        scoreLabel = new Label(Text.SCORE + ": 0", labelStyle);
        killLabel = new Label(Text.KILL_COUNT + ": 0", labelStyle);
        timeLabel = new Label(Text.TIME.getText() + ": 00:00", labelStyle);
        healthLabel = new Label(Text.HEALTH.getText() + ": 100/100", labelStyle);
        levelLabel = new Label(Text.LEVEL.getText() + ": 1", labelStyle);
        xpBar = new ProgressBar(0, 100, 1, false, progressBarStyle);
        xpLabel = new Label("0/100", labelStyle);
        weaponLabel = new Label(Text.WEAPON + ": REVOLVER", labelStyle);
        ammoLabel = new Label(Text.AMMO + ": 6/6", labelStyle);
        autoAimStatusLabel = new Label(Text.AUTO_AIM + ": OFF", labelStyle);



        pauseDialog = new PauseDialog(this);
        gameOverDialog = new GameOverDialog(this);
        selectAbilityDialog = new SelectAbilityDialog(this);



        uiTableRoot = new Table();
        uiTableRoot.setFillParent(true);
        uiTableRoot.top();
        uiStage.addActor(uiTableRoot);

        Table statsTable = new Table();
        statsTable.top().left().pad(10);
        statsTable.add(scoreLabel).left().row();
        statsTable.add(killLabel).left().row();
        statsTable.add(timeLabel).left().row();
        statsTable.add(healthLabel).left().row();
        statsTable.add(levelLabel).left().row();

        Table xpTable = new Table();
        xpTable.add(xpBar).width(150).height(15);
        xpTable.add(xpLabel).padLeft(5);
        statsTable.add(xpTable).left().row();


        statsTable.add(weaponLabel).left().padTop(5).row();
        statsTable.add(ammoLabel).left().row();
        statsTable.add(autoAimStatusLabel).left().padTop(5).row();



        uiTableRoot.add(statsTable).expandX().top().left(); // Stats table at top-left
        uiTableRoot.row(); // New row


        try {
            cursorPixmap = new Pixmap(Gdx.files.internal("Images/Sprite/T_CursorSprite.png"));
            int xHotSpot = cursorPixmap.getWidth() / 2;
            int yHotSpot = cursorPixmap.getHeight() / 2;

            if (cursorPixmap.getWidth() > 0 && cursorPixmap.getHeight() > 0) {
                customCursor = Gdx.graphics.newCursor(cursorPixmap, xHotSpot, yHotSpot);
                Gdx.graphics.setCursor(customCursor);
            } else {
                Gdx.app.error("GameScreen", "Cursor pixmap is empty or could not be loaded.");
            }
        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Could not load custom cursor: ", e);
        }

        if (cursorPixmap != null) {
            cursorPixmap.dispose();
            cursorPixmap = null;
        }

        lastTentacleSpawnTime = TimeUtils.millis() - (long)tentacleMonsterSpawnRate + (long)TENTACLE_MONSTER_SPAWN_DELAY; // Start spawning soon after delay
        lastEyebatSpawnTime = TimeUtils.millis();
        canSpawnEyebats = false;
        elderBossHasSpawned = false;

        if (isPaused) {
            pauseDialog.show(uiStage);
        }
    }


    private Texture createLightingOverlay() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        float MAX_DARKNESS = 0.5f;
        int size = (int) (screenHeight * 0.9);
//        int size = 512; // Size of the texture (power of 2 is good)
        float radius = size / 2.5f; // Radius of the bright circle
        float feather = size / 2f - radius; // Width of the soft edge

        Pixmap pixmap = new Pixmap(screenWidth, screenHeight, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None); // Important for drawing with alpha

        for (int y = 0; y < screenHeight; y++) {
            for (int x = 0; x < screenWidth; x++) {
                float distance = Vector2.dst(x, y, screenWidth / 2f, screenHeight / 2f);
                if (distance < radius) {
                    // Inside the main light circle: fully transparent
                    pixmap.setColor(0, 0, 0, 0);
                } else if (distance < radius + feather) {
                    // In the soft edge (feather) area: fade to dark
                    float alpha = (distance - radius) / feather;
                    pixmap.setColor(0, 0, 0, alpha * MAX_DARKNESS); // 0.85f controls max darkness
                } else {
                    // Outside the light circle: dark
                    pixmap.setColor(0, 0, 0, MAX_DARKNESS);
                }
                pixmap.drawPixel(x, y);
            }
        }

        Texture overlayTexture = new Texture(pixmap);
        pixmap.dispose(); // Dispose pixmap after creating texture
        return overlayTexture;
    }/*
    private Texture createLightingOverlay() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        Pixmap pixmap = new Pixmap(screenWidth, screenHeight, Pixmap.Format.RGBA8888);

        pixmap.setColor(0f,0f,0f,0.8f);
        pixmap.fill();

        int radius = 256;
        pixmap.setColor(0f,0f,0f,0f);
        pixmap.fillCircle(screenWidth / 2, screenHeight / 2, radius);

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;




    }*/


    private void spawnTreesAroundPlayer() {
        if (player == null || treeTexture == null) return;

        // Determine the player's current grid cell coordinates
        int cellX = (int) (player.position.x / TREE_CELL_SIZE);
        int cellY = (int) (player.position.y / TREE_CELL_SIZE);

        // Check a 3x3 grid of cells around the player
        for (int y = cellY - 1; y <= cellY + 1; y++) {
            for (int x = cellX - 1; x <= cellX + 1; x++) {
                // Create a unique ID for the cell
                long cellId = (long) x << 32 | (y & 0xffffffffL);

                // If this cell has not been populated yet, spawn trees in it
                if (!populatedTreeCells.contains(cellId)) {
                    // Spawn a random number of trees in this cell
                    int treesToSpawn = MathUtils.random(5, 15); // e.g., 5 to 15 trees per cell
                    for (int i = 0; i < treesToSpawn; i++) {
                        float treeX = (x * TREE_CELL_SIZE) + MathUtils.random(0, TREE_CELL_SIZE - treeTexture.getWidth());
                        float treeY = (y * TREE_CELL_SIZE) + MathUtils.random(0, TREE_CELL_SIZE - treeTexture.getHeight());
                        trees.add(new Tree(treeTexture, new Vector2(treeX, treeY)));
                    }
                    // Mark this cell as populated
                    populatedTreeCells.add(cellId);
                    Gdx.app.log("Tree Spawning", "Populated tree cell: (" + x + ", " + y + ")");
                }
            }
        }
    }

    public Enemy findNearestEnemy() {
        if (player == null || enemies.isEmpty()) {
            return null;
        }
        Enemy nearestEnemy = null;
        float minDistanceSq = Float.MAX_VALUE;

        Vector2 playerCenter = new Vector2(
            player.position.x + player.getWidth() / 2f,
            player.position.y + player.getHeight() / 2f
        );

        for (Enemy enemy : enemies) {
            if (enemy == null) continue;
            Vector2 enemyCenter = new Vector2(
                enemy.position.x + enemy.getWidth() / 2f,
                enemy.position.y + enemy.getHeight() / 2f
            );
            float distanceSq = playerCenter.dst2(enemyCenter);
            if (distanceSq < minDistanceSq) {
                minDistanceSq = distanceSq;
                nearestEnemy = enemy;
            }
        }
        return nearestEnemy;
    }







    public void spawnEnemyBullet(Vector2 startPos, float angleRad, int damage) {
        enemyBullets.add(new Bullet(enemyBulletTexture, startPos, angleRad, damage, false));
    }

    private void manageEnemySpawning() {
        // Tentacle Monster Spawning (HP: 25, Spawn Rate: every 3s spawns i/30 monsters)
        if (TimeUtils.millis() - lastTentacleSpawnTime > tentacleMonsterSpawnRate) {
            for(int i = 0; i < gameTimer / 30; i++)
                spawnNewEnemy(EnemyType.TENTACLE_MONSTER);
            lastTentacleSpawnTime = TimeUtils.millis();
        }

        // Eyebat Spawning Logic (HP: 50, Spawn after T/4, then every 10s spawns (4i-T+30) / 30)
        if (!canSpawnEyebats && gameTimer >= MAX_GAME_TIME / 4.0f) {
            canSpawnEyebats = true;
            Gdx.app.log("GameScreen", "Eyebat spawning enabled.");
            lastEyebatSpawnTime = TimeUtils.millis();
        }
        if (canSpawnEyebats && TimeUtils.millis() - lastEyebatSpawnTime > eyebatSpawnRate) {
            for(int i = 0; i < MathUtils.floor((4 * gameTimer - MAX_GAME_TIME + 30) / 30); i++)
                spawnNewEne my(EnemyType.EYEBAT);
            lastEyebatSpawnTime = TimeUtils.millis();
        }

        // Elder Boss Spawning (HP: 400, Spawns after T/2)
        if (!elderBossHasSpawned && gameTimer >= MAX_GAME_TIME / 2.0f) {
            spawnNewEnemy(EnemyType.ELDER_BOSS);
            elderBossHasSpawned = true;
            Gdx.app.log("GameScreen", "ELDER BOSS HAS SPAWNED!");
        }
    }

    private void spawnNewEnemy(EnemyType type) {
        float spawnX = 0, spawnY = 0;
        int edge = MathUtils.random(3);
        float camX = camera.position.x;
        float camY = camera.position.y;
        float halfViewWidth = viewport.getWorldWidth() / 2f;
        float halfViewHeight = viewport.getWorldHeight() / 2f;
        float spawnOffset = 50f;



        switch (edge) {
            case 0:
                spawnX = MathUtils.random(camX - halfViewWidth, camX + halfViewWidth);
                spawnY = camY + halfViewHeight + spawnOffset;
                break;
            case 1: spawnX = MathUtils.random(camX - halfViewWidth, camX + halfViewWidth);
                spawnY = camY - halfViewHeight - spawnOffset;
                break;
            case 2: spawnX = camX - halfViewWidth - spawnOffset;
                spawnY = MathUtils.random(camY - halfViewHeight, camY + halfViewHeight);
                break;
            case 3: spawnX = camX + halfViewWidth + spawnOffset;
                spawnY = MathUtils.random(camY - halfViewHeight, camY + halfViewHeight);
                break;
        }

        Animation<TextureRegion> animationToUse = GameAssetManager.getGameAssetManager().brainMonsterAnim;

        if(type == EnemyType.EYEBAT) {
            animationToUse = GameAssetManager.getGameAssetManager().eyebatMonsterAnim;
        } else if (type == EnemyType.ELDER_BOSS) {
            animationToUse = GameAssetManager.getGameAssetManager().elderBossAnim;
        } else if (type == EnemyType.TENTACLE_MONSTER) {
            animationToUse = GameAssetManager.getGameAssetManager().brainMonsterAnim;
        }

        Enemy enemy = new Enemy(type, animationToUse, new Vector2(spawnX, spawnY));
        enemies.add(enemy);

    }

    private void spawnXPOrb(Vector2 position) {
        XPOrb orb = new XPOrb(xpOrbTexture, new Vector2(position.x, position.y));
        xpOrbs.add(orb);
    }

    private void updateGameLogic(float delta) {
        if (isPaused || gameOver) return;


        gameTimer += delta;
        if(gameTimer > MAX_GAME_TIME) {
            setGameOver();
            return;
        }

        spawnTreesAroundPlayer();

        if(player != null) player.update(delta);

        for(int i = playerBullets.size - 1; i >= 0; i--) {
            Bullet bullet = playerBullets.get(i);
            bullet.update(delta);
            if(bullet.isOffScreen(camera, viewport)) {
                playerBullets.removeIndex(i);
            }
        }

        for(int i = enemyBullets.size - 1; i >= 0; i--) {
            Bullet bullet = enemyBullets.get(i);
            bullet.update(delta);
            if(bullet.isOffScreen(camera, viewport)) {
                enemyBullets.removeIndex(i);
            }
        }



        for (Enemy enemy : enemies) {
            if (player != null) {
                Vector2 playerCenter = new Vector2(
                    player.position.x + player.getWidth() / 2f,
                    player.position.y + player.getHeight() / 2f
                );
                enemy.update(delta, playerCenter, this);
            }
        }
        manageEnemySpawning();


        for (int i = xpOrbs.size - 1; i >= 0; i--) {
            xpOrbs.get(i).update(delta);
        }

        for(int i = deathAnimations.size - 1; i >= 0; i--) {
            DeathAnimation anim = deathAnimations.get(i);
            anim.update(delta);
            if(anim.isFinished()) {
                deathAnimations.removeIndex(i);
            }
        }

        for(int i = hitAnimations.size - 1; i >= 0; i--) {
            HitAnimation anim = hitAnimations.get(i);
            anim.update(delta);
            if (anim.isFinished()) {
                hitAnimations.removeIndex(i);
            }
        }


        if (player != null) checkCollisions();
    }

    private void checkCollisions() {
        if(player == null) return;
        Rectangle playerBounds = player.getBounds();

        // Player Bullets vs Enemies
        for (int i = playerBullets.size - 1; i >= 0; i--) {
            Bullet bullet = playerBullets.get(i);
            if(bullet == null || !bullet.isPlayerBullet) continue;
            Rectangle bulletBounds = bullet.getBounds();
            for (int j = enemies.size - 1; j >= 0 ; j--) {
                Enemy enemy = enemies.get(j);
                if(bulletBounds.overlaps(enemy.getBounds())) {
                    playerBullets.removeIndex(i);
                    enemy.takeDamage(bullet.damage);

                    Vector2 knockbackDir = new Vector2(bullet.getVelocity()).nor();
                    float knockbackForce = 500f;
                    enemy.applyKnockback(knockbackDir, knockbackForce);

                    if(enemy.health <= 0) {
                        Vector2 enemyCenterPos = new Vector2(
                            enemy.position.x + enemy.getWidth()/2f,
                            enemy.position.y + enemy.getHeight()/2f);
                        enemies.removeIndex(j);
                        score += 10;
                        kill += 1;
                        spawnXPOrb(enemyCenterPos);

                        Animation<TextureRegion> deathAnim = GameAssetManager.getGameAssetManager().deathAnimation;
                        if (deathAnim != null) {
                            deathAnimations.add(new DeathAnimation(deathAnim, enemyCenterPos));
                        }
                    }
                    break;
                }
            }
        }

        //Enemy Bullets vs Player
        for(int i = enemyBullets.size - 1; i >= 0; i--) {
            Bullet bullet = enemyBullets.get(i);
            if(bullet == null || bullet.isPlayerBullet) return;
            if (playerBounds.overlaps(bullet.getBounds())) {
                if (TimeUtils.millis() - player.lastHitTime > player.invincibilityDuration) {
                    player.takeDamage(bullet.damage);
                    enemyBullets.removeIndex(i);

                    Animation<TextureRegion> hitAnim = GameAssetManager.getGameAssetManager().hitAnimation;
                    if(hitAnim != null) {
                        hitAnimations.add(new HitAnimation(
                                hitAnim,
                                new Vector2(
                                    player.position.x + player.getWidth() / 2,
                                    player.position.y + player.getHeight() / 2)
                            )
                        );
                    }

                    if (player.health <= 0) { setGameOver(); return; }
                }
            }
        }

        for(int i = enemies.size - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            if(playerBounds.overlaps(enemy.getBounds())) {
                if(TimeUtils.millis() - player.lastHitTime > player.invincibilityDuration) {
                    player.takeDamage(enemy.damage);
                    if(player.health <= 0) {
                        setGameOver();
                        return;
                    }
                }
            }
        }

        for (Tree tree : trees) {
            if(playerBounds.overlaps(tree.getBounds())) {
                if(TimeUtils.millis() - player.lastHitTime > player.invincibilityDuration) {
                    player.takeDamage(Tree.TREE_DAMAGE);
                    if(player.health <= 0) {
                        setGameOver();
                        return;
                    }
                }
            }
        }

        for (int i = xpOrbs.size - 1; i >= 0; i--) {
            XPOrb orb = xpOrbs.get(i);
            if(playerBounds.overlaps(orb.getBounds())) {
                player.gainXP(orb.xpValue);
                xpOrbs.removeIndex(i);
                if(player.xp >= player.xpToNextLevel) {
                    player.levelUp();
                    selectAbilityDialog.show(uiStage);
                }
            }
        }
    }


    private void updateUILabels() {
        if (player == null ) return;

        scoreLabel.setText(Text.SCORE + ": " + score);
        killLabel.setText(Text.KILL_COUNT + ": " + kill);
        timeLabel.setText(String.format(Text.TIME + ": %02d:%02d", (int)((MAX_GAME_TIME - gameTimer) / 60), (int)((MAX_GAME_TIME - gameTimer) % 60)));
        healthLabel.setText(Text.HEALTH.getText() + ": " + player.health + "/" + player.maxHealth);
        levelLabel.setText(Text.LEVEL + ": " + player.level);

        xpBar.setRange(0, player.xpToNextLevel);
        xpBar.setValue(player.xp);
        xpLabel.setText(player.xp + "/" + player.xpToNextLevel);

        weaponLabel.setText(Text.WEAPON + ": " + player.currentWeapon.name());
        String ammoText = player.isReloading ? Text.RELOADING + "..." : player.currentAmmo + "/" + player.currentMaxAmmo;
        ammoLabel.setText(Text.AMMO + ": " + ammoText);
        autoAimStatusLabel.setText(Text.AUTO_AIM + ": " + (autoAimEnabled ? Text.ON : Text.OFF));




        if (isPaused && !gameOver) {

        } else if (gameOver) {
//            String outcome = (gameTimer >= MAX_GAME_TIME) ? Text.SURVIVAL_TIME.getText() : Text.GAME_OVER.getText();
//            gameOverDialog.setKill(kill);
//            gameOverDialog.setSurvivalTime(gameTimer / 60 + ":" + gameTimer % 60);
//            gameOverDialog.setOutcome(outcome);
//            gameOverDialog.setScore(score);
//            gameOverDialog.show(uiStage);
        } else {
            gameOverDialog.hide();
        }
    }

    @Override
    public void render(float delta) {
        controller.handleInput(delta);
        if(!isPaused && !gameOver) {
            updateGameLogic(delta);
        }

        if(player != null) {
            camera.position.set(
                player.position.x + player.getWidth() / 2f,
                player.position.y + player.getHeight() / 2f,
                0
            );
        }
        camera.update();

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        batch.begin();

        //Background drawing
        if(backgroundTexture != null) {
            float tileWidth = backgroundTexture.getWidth();
            float tileHeight = backgroundTexture.getHeight();

            if(tileWidth > 0 && tileHeight > 0) {
                float viewLeft = camera.position.x - viewport.getWorldWidth() / 2f;
                float viewBottom = camera.position.y - viewport.getWorldHeight() / 2f;
                float viewRight = camera.position.x + viewport.getWorldWidth() / 2f;
                float viewTop = camera.position.y + viewport.getWorldHeight() / 2f;

                float startX = MathUtils.floor(viewLeft / tileWidth) * tileWidth;
                float startY = MathUtils.floor(viewBottom / tileHeight) * tileHeight;

                for (float y = startY; y < viewTop; y += tileHeight) {
                    for (float x = startX; x < viewRight; x += tileWidth) {
                        batch.draw(backgroundTexture, x, y, tileWidth, tileHeight);
                    }
                }
            } else {
                batch.draw(backgroundTexture,
                    camera.position.x - viewport.getWorldWidth() / 2f,
                    camera.position.y - viewport.getWorldHeight() / 2f,
                    viewport.getWorldWidth(),
                    viewport.getWorldHeight());
            }
        }
        //--------------------------

        for (Tree tree : trees) tree.draw(batch);
        for (XPOrb orb : xpOrbs) orb.draw(batch);
        for (Enemy enemy : enemies) enemy.draw(batch);
        if(player != null) player.draw(batch);
        for(Bullet bullet : playerBullets) bullet.draw(batch);
        for (Bullet bullet : enemyBullets) bullet.draw(batch);
        for (DeathAnimation deathAnimation : deathAnimations) deathAnimation.draw(batch);
        for (HitAnimation hitAnimation : hitAnimations) hitAnimation.draw(batch);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (player != null) player.drawHealthBar(shapeRenderer);
        for (Enemy enemy : enemies) enemy.drawHealthBar(shapeRenderer);
        shapeRenderer.end();

        // lightingOverlay

        batch.setProjectionMatrix(uiStage.getCamera().combined);
        batch.begin();
        if (lightingOverlayTexture != null) {
            batch.setColor(Color.WHITE);

            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();



            batch.draw(lightingOverlayTexture, 0, 0, screenWidth, screenHeight);
        }
        batch.end();

        // Render UI
        updateUILabels();
        uiStage.getViewport().apply();
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        if (uiStage != null) {
            uiStage.getViewport().update(width, height, true);
        }
    }

    @Override
    public void resume() {
        ControlsManager.refreshControls();
    }

    @Override
    public void dispose() {
        AppData.getAppData().activeUser.setLastGame(null);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
//        if (customCursor != null) {
//            customCursor.dispose(); // Dispose the custom cursor object
//            customCursor = null;
//        }
//        if(progressBarKnobTex != null) progressBarKnobTex.dispose();
//        if (progressBarBgTex != null) progressBarBgTex.dispose();
//        if(lightingOverlayTexture != null) lightingOverlayTexture.dispose();
//        if(font != null) font.dispose();
//        if(shapeRenderer != null) shapeRenderer.dispose();
//        if(uiStage != null) uiStage.dispose();
//        if(treeTexture != null) treeTexture.dispose();

        trees.clear();
        enemies.clear();
        playerBullets.clear();
        enemyBullets.clear();
        xpOrbs.clear();
        deathAnimations.clear();
        hitAnimations.clear();
    }

    @Override
    public void hide() {
        AppData.getAppData().activeUser.setLastGame(this);
        if(isPaused) {
            pauseDialog.hide();
        }

        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        if (customCursor != null) {
            customCursor.dispose(); // Dispose the custom cursor object
            customCursor = null;
        }
        Gdx.input.setInputProcessor(null);
    }

    public void setGameOver() {
        gameOver = true;
        String outcome = (gameTimer >= MAX_GAME_TIME) ? Text.SURVIVAL_TIME.getText() : Text.GAME_OVER.getText();
        gameOverDialog.setKill(kill);
        gameOverDialog.setSurvivalTime(String.format("%02d:%02d", (int) gameTimer / 60, (int) gameTimer % 60));
        gameOverDialog.setOutcome(outcome);
        gameOverDialog.setScore(score);
        gameOverDialog.show(uiStage);

        User user = AppData.getAppData().activeUser;
        user.setKill(user.getKill() + kill);
        user.setScore(user.getScore() + score);
        user.setLongestSurvivalTime(Math.max(gameTimer , user.getLongestSurvivalTime()));
    }

    public void addAbility(AbilityType type) {
        player.applyAbility(type);
    }
}

