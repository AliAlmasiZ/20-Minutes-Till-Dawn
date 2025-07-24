package io.github.AliAlmasiZ.tillDawn.views.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.AliAlmasiZ.tillDawn.Main;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.PlayerDAO;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;
import io.github.AliAlmasiZ.tillDawn.models.User;
import io.github.AliAlmasiZ.tillDawn.views.GameView;
import io.github.AliAlmasiZ.tillDawn.views.ScoreBoardMenuView;
import io.github.AliAlmasiZ.tillDawn.views.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreboardScreen extends ScreenAdapter {

    private final Main main;
    private final Stage stage;
    private final Skin skin;
    private final List<User> allUsers;
    private Table scoreboardTable;


    private final Color top1Color = Color.GOLD;
    private final Color top2Color = Color.GRAY;
    private final Color top3Color = new Color(0.8f, 0.5f, 0.2f, 1f); // Bronze
    private final Color currentUserColor = Color.CYAN;
    private final Color defaultColor = Color.WHITE;

    public ScoreboardScreen(Main main) {
        this.main = main;
        this.stage = new Stage(new ScreenViewport());
        this.skin = GameAssetManager.getGameAssetManager().pixthulhuuiSkin;
        this.allUsers = AppData.getAppData().getAllUsers();


        setupUI();
        sortByScore();
    }

    private void setupUI() {
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.pad(20);
        stage.addActor(rootTable);


        Label titleLabel = new Label(Text.SCOREBOARD.getText(), skin, "title");
        rootTable.add(titleLabel).colspan(4).padBottom(20).row();


        Table sortButtonsTable = new Table();
        TextButton sortByScoreBtn = new TextButton(Text.SCORE.getText(), skin);
        TextButton sortByUsernameBtn = new TextButton(Text.USERNAME.getText(), skin);
        TextButton sortByKillsBtn = new TextButton(Text.KILL_COUNT.getText(), skin);
        TextButton sortByTimeBtn = new TextButton(Text.SURVIVAL_TIME.getText(), skin);

        sortButtonsTable.add(sortByScoreBtn).pad(5);
        sortButtonsTable.add(sortByUsernameBtn).pad(5);
        sortButtonsTable.add(sortByKillsBtn).pad(5);
        sortButtonsTable.add(sortByTimeBtn).pad(5);
        rootTable.add(sortButtonsTable).colspan(4).padBottom(10).row();


        Table headerTable = new Table();
        headerTable.add(new Label("Rank", skin)).width(100);
        headerTable.add(new Label(Text.USERNAME.getText(), skin)).expandX().fillX();
        headerTable.add(new Label(Text.SCORE.getText(), skin)).width(100);
        headerTable.add(new Label(Text.KILL_COUNT.getText(), skin)).width(150);
        headerTable.add(new Label(Text.SURVIVAL_TIME.getText(), skin)).width(200);
        rootTable.add(headerTable).expandX().fillX().padBottom(10).row();


        scoreboardTable = new Table();
        ScrollPane scrollPane = new ScrollPane(scoreboardTable, skin);
        scrollPane.setFadeScrollBars(false);
        rootTable.add(scrollPane).expand().fill().colspan(4).row();


        TextButton backButton = new TextButton(Text.GO_BACK.getText(), skin);
        rootTable.add(backButton).colspan(4).padTop(20).width(300).height(50);


        sortByScoreBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sortByScore();
            }
        });
        sortByUsernameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sortByUsername();
            }
        });
        sortByKillsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sortByKills();
            }
        });
        sortByTimeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sortBySurvivalTime();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new MainMenuScreen(main));
            }
        });
    }

    private void rebuildScoreboardTable() {
        scoreboardTable.clear();
        int rank = 1;
        User currentUser = AppData.getAppData().getActiveUser();

        for (User user : allUsers) {
            if (rank > 10) break;


            Color rowColor = defaultColor;
            if (user.getId() == currentUser.getId()) {
                rowColor = currentUserColor;
            } else if (rank == 1) {
                rowColor = top1Color;
            } else if (rank == 2) {
                rowColor = top2Color;
            } else if (rank == 3) {
                rowColor = top3Color;
            }

            Label.LabelStyle style = new Label.LabelStyle(skin.getFont("font"), rowColor);


            Label rankLabel = new Label(String.valueOf(rank), style);
            Label usernameLabel = new Label(user.getUsername(), style);
            Label scoreLabel = new Label(String.valueOf(user.getScore()), style);
            Label killsLabel = new Label(String.valueOf(user.getKill()), style);
            Label timeLabel = new Label(String.format("%02d:%02d", (int) (user.getLongestSurvivalTime() / 60), (int) (user.getLongestSurvivalTime() % 60)), style);


            scoreboardTable.add(rankLabel).width(100);
            scoreboardTable.add(usernameLabel).expandX().fillX();
            scoreboardTable.add(scoreLabel).width(150);
            scoreboardTable.add(killsLabel).width(100);
            scoreboardTable.add(timeLabel).width(200);
            scoreboardTable.row().padTop(5).padBottom(5);

            rank++;
        }
    }

    private void sortByScore() {
        allUsers.sort(Comparator.comparingInt(User::getScore).reversed());
        rebuildScoreboardTable();
    }

    private void sortByUsername() {
        allUsers.sort(Comparator.comparing(User::getUsername, String.CASE_INSENSITIVE_ORDER));
        rebuildScoreboardTable();
    }

    private void sortByKills() {
        allUsers.sort(Comparator.comparingInt(User::getKill).reversed());
        rebuildScoreboardTable();
    }

    private void sortBySurvivalTime() {
        allUsers.sort(Comparator.comparingDouble(User::getLongestSurvivalTime).reversed());
        rebuildScoreboardTable();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
