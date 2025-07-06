package io.github.AliAlmasiZ.tillDawn.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.JsonSaver;
import io.github.AliAlmasiZ.tillDawn.views.screens.GameScreen;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    static int playersCount = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = ++playersCount;
    private String username;
    private String password;
//    private String securityQuestion;
    private String securityAnswer;
    private int score;
    private int kill;
    private float longestSurvivalTime = 0;
    private String avatarPath;
    @Transient
    private transient Player player;
    @Transient
    private transient GameScreen lastGame;


    private User(){}


    public User(String username, String password, String securityAnswer) {
        this.username = username;
        this.password = password;
//        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.id = ++playersCount;
        score = 0;
        kill = 0;
        this.player = JsonSaver.getInstance().loadPlayer(this);
        this.player.setUserID(id);
        setRandomAvatar();
    }


    private void setRandomAvatar() {
        FileHandle dir = Gdx.files.internal("avatars");
        Array<FileHandle> files = Array.with(dir.list());
        FileHandle pick = files.random();
        Gdx.app.log("random avatar", pick.path());
        this.setAvatar(pick.path());
    }

    public void loadPlayer() {

    }



    public static int getPlayersCount() {
        return playersCount;
    }

    public static void setPlayersCount(int playersCount) {
        User.playersCount = playersCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public float getLongestSurvivalTime() {
        return longestSurvivalTime;
    }

    public void setLongestSurvivalTime(float longestSurvivalTime) {
        this.longestSurvivalTime = longestSurvivalTime;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public Texture getAvatarTex() {
        return new Texture(avatarPath);
    }



    public void setAvatar(String path) {
        avatarPath = path;
    }

    public void delete() {

    }

    public GameScreen getLastGame() {
        return lastGame;
    }

    public void setLastGame(GameScreen lastGame) {
        this.lastGame = lastGame;
    }
}
