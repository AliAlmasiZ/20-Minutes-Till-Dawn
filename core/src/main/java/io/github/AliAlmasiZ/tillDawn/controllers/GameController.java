package io.github.AliAlmasiZ.tillDawn.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.AliAlmasiZ.tillDawn.controllers.utils.ControlsManager;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.Entities.Bullet;
import io.github.AliAlmasiZ.tillDawn.models.Entities.Enemy;
import io.github.AliAlmasiZ.tillDawn.models.Player;
import io.github.AliAlmasiZ.tillDawn.models.Settings;
import io.github.AliAlmasiZ.tillDawn.models.enums.GameAction;
import io.github.AliAlmasiZ.tillDawn.models.enums.WeaponType;
import io.github.AliAlmasiZ.tillDawn.views.screens.GameScreen;

public class GameController {
    private GameScreen screen;
    private Player player;

    public GameController(GameScreen screen) {
        this.screen = screen;
        player = AppData.getAppData().activeUser.getPlayer();
    }

    public void handleInput(float delta) {


        if(Gdx.input.isKeyJustPressed(ControlsManager.getKeyForAction(GameAction.PAUSE)) ||
            Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            screen.isPaused = !screen.isPaused;
            if(screen.isPaused) {
                screen.pauseDialog.show(screen.uiStage);
            }else {
                screen.pauseDialog.hide();
            }
        }
        if(Gdx.input.isKeyJustPressed(ControlsManager.getKeyForAction(GameAction.TOGGLE_AUTO_AIM))) {
            screen.autoAimEnabled = !screen.autoAimEnabled;
            Gdx.app.log("GameScreen", "Auto-aim " + (screen.autoAimEnabled ? "enabled" : "disabled"));
        }

        if (player != null && Gdx.input.isKeyJustPressed(ControlsManager.getKeyForAction(GameAction.RELOAD))) {
            player.startReload();
        }

        if (screen.isPaused || screen.gameOver || player == null) {
            if (player != null) player.isMoving = false;
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

//        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
//        screen.viewport.unproject(mousePos);
//
//        float playerCenterX = player.position.x + playerTexture.getWidth() / 2f;
//        float playerCenterY = player.position.y + playerTexture.getHeight() / 2f;
//
//        float angleRadians = MathUtils.atan2(mousePos.y - playerCenterY, mousePos.x - playerCenterX);
//        float angleDegrees = angleRadians * MathUtils.radiansToDegrees;
//
//        player.setAimAngle(angleDegrees);

        // Aiming Logic
        if (screen.autoAimEnabled) {
            Enemy targetEnemy = screen.findNearestEnemy();
            if (targetEnemy != null) {
                float pCenterX = player.position.x + player.getWidth() / 2f, pCenterY = player.position.y + player.getHeight() / 2f;
                float eCenterX = targetEnemy.position.x + targetEnemy.getWidth() / 2f, eCenterY = targetEnemy.position.y + targetEnemy.getHeight() / 2f;
                player.setAimAngle(MathUtils.atan2(eCenterY - pCenterY, eCenterX - pCenterX) * MathUtils.radiansToDegrees);
                Vector2 enemyScreenPos = screen.viewport.project(new Vector2(eCenterX, eCenterY));
                Gdx.input.setCursorPosition((int) enemyScreenPos.x, Gdx.graphics.getHeight() - (int) enemyScreenPos.y);
            } else {
                manualAim();
            }
        } else {
            manualAim();
        }

//        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
//
//            if (TimeUtils.millis() - player.lastShotTime > player.shootCooldown || true) {
//                spawnBullet();
//                player.lastShotTime = TimeUtils.millis();
//            }
//        }

        if (Gdx.input.isButtonPressed(ControlsManager.getKeyForAction(GameAction.SHOOT))) { // isButtonPressed for continuous fire
            if (player.canShoot() && screen.gameTimer - player.lastShotTime > player.shootCooldown) {
                spawnPlayerBullet();
                player.lastShotTime = screen.gameTimer;
                if (!player.isReloading && !screen.isAmmoCheatActive) player.currentAmmo--;
                if (player.currentAmmo == 0 && !player.isReloading && Settings.getInstance().autoReload) {
                    player.startReload();
                }
            }
        }


        //Cheats
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            player.health = player.maxHealth;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            screen.gameTimer += 60;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            player.levelUp();
            screen.selectAbilityDialog.show(screen.uiStage);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
            if(screen.gameTimer > screen.MAX_GAME_TIME / 2f)
                screen.gameTimer = screen.MAX_GAME_TIME / 2f;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            screen.isAmmoCheatActive = !screen.isAmmoCheatActive;
        }

    }
    private void manualAim() {
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        screen.viewport.unproject(mousePos);
        float pCenterX = player.position.x + player.getWidth() / 2f;
        float pCenterY = player.position.y + player.getHeight() / 2f;
        player.setAimAngle(MathUtils.atan2(mousePos.y - pCenterY, mousePos.x - pCenterX) * MathUtils.radiansToDegrees);
    }

    private void spawnPlayerBullet() {
        if (player == null) return;
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        screen.viewport.unproject(mousePos);

        float playerCenterX = player.position.x + player.getWidth() / 2f;
        float playerCenterY = player.position.y + player.getHeight() / 2f;

        float baseAngleRad = MathUtils.atan2(mousePos.y - playerCenterY, mousePos.x - playerCenterX);

        int effectiveDamage = player.getEffectiveDamage();

        for (int i = 0; i < player.projectilesPerShot; i++) {
            float currentAngleRad = baseAngleRad;
            if (/*player.currentWeapon == WeaponType.SHOTGUN &&*/ player.projectilesPerShot > 1) {
                float spreadRange = 30f;
                float randomSpread = MathUtils.random(-spreadRange / 2f, spreadRange / 2f);
                currentAngleRad = baseAngleRad + (randomSpread * MathUtils.degreesToRadians);
            }
            // For Dual SMGs, if they are meant to fire from slightly different positions or angles,
            // that logic would go here too, potentially alternating. For now, they fire like revolver.

            screen.playerBullets.add(new Bullet(screen.playerBulletTexture,
                new Vector2(playerCenterX, playerCenterY), currentAngleRad, effectiveDamage, true));
        }
    }
}
