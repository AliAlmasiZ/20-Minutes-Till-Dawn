package io.github.AliAlmasiZ.tillDawn.views.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.AliAlmasiZ.tillDawn.Main;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;
import io.github.AliAlmasiZ.tillDawn.views.Text;
import io.github.AliAlmasiZ.tillDawn.views.screens.GameScreen;
import io.github.AliAlmasiZ.tillDawn.views.screens.MainMenuScreen;

import java.text.Format;


public class GameOverDialog extends Dialog {
    BitmapFont font;
    String survivalTime, outcome;
    int kill, score;
    Screen gameScreen;
    public GameOverDialog(Screen gameScreen) {
        super("", GameAssetManager.getGameAssetManager().pixthulhuuiSkin);
        this.gameScreen = gameScreen;
        font = new BitmapFont(Gdx.files.internal(GameAssetManager.getGameAssetManager().CHEVY_RAY_LANTERN));
    }

    private Table dataTable() {
        Table table = new Table();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.RED);
        Label gameOverMessageLabel = new Label(outcome, labelStyle);
        Label usernameLabel = new Label(Text.USERNAME + ": " + AppData.getAppData().activeUser.getUsername(), labelStyle);
        Label finalScoreLabel = new Label(Text.SCORE +": " + score, labelStyle);
        Label survivalTimeLabel = new Label(Text.SURVIVAL_TIME.getText() + " : " + survivalTime , labelStyle);
        Label killLabel = new Label(Text.KILL_COUNT.getText() + ": " + kill , labelStyle);
        TextButton exit = new TextButton(Text.EXIT.getText(), getSkin());

        table.defaults().center().size(300, 20).pad(10);
        table.add(gameOverMessageLabel).row();
        table.add(usernameLabel).row();
        table.add(finalScoreLabel).row();
        table.add(survivalTimeLabel).row();
        table.add(killLabel).row();
        table.add(exit).height(100);

        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.dispose();
                Main.getInstance().setScreen(new MainMenuScreen(Main.getInstance()));
                hide();
            }
        });

        return table;
    }

    @Override
    public Dialog show(Stage stage) {
        getContentTable().clear();
        getContentTable().add(dataTable());
        return super.show(stage);
    }

    public void setSurvivalTime(String survivalTime) {
        this.survivalTime = survivalTime;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
