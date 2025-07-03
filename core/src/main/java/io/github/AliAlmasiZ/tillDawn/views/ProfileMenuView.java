package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;

public class ProfileMenuView {
    private final ShapeRenderer shapes;
    


    private Stage stage;
    private Table buttonTable, dataTable;

    public final TextButton changeUsernameBtn, changePasswordBtn, deleteAccountBtn, changeAvatarBtn, backBtn;
//    public final Image avatarImage;
    public final Label messageLabel;


    public ProfileMenuView(Skin skin) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        float width = stage.getViewport().getScreenWidth();
        float height = stage.getViewport().getScreenHeight();

        buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.center();
        buttonTable.pad(40);

        stage.addActor(buttonTable);

        //Title
        Label title = new Label("Profile Menu", skin);
        title.setFontScale(1.5f);
        buttonTable.add(title).colspan(2).padBottom(20);
        buttonTable.row();

        //Avatar
//        Texture avatarTexture = new Texture(Gdx.files.internal("avatars/default_avatar.png"));
//        avatarImage = new Image(new TextureRegionDrawable(avatarTexture));
//        avatarImage.setSize(128, 128);
//        table.add(avatarImage).padRight(30).size(128, 128);




        dataTable = new Table();
        Label nameLabel = new Label("Username: " + AppData.getAppData().getActiveUser().getUsername(), skin);
        Label scoreLabel = new Label("Score: " + AppData.getAppData().getActiveUser().getScore(), skin);
        dataTable.add(nameLabel).left().padBottom(10);
//        details.row();
        dataTable.add(scoreLabel).left().padBottom(20);
        dataTable.row();

        changeUsernameBtn = new TextButton("Change Username", skin);
        changePasswordBtn = new TextButton("Change Password", skin);
        deleteAccountBtn = new TextButton("Delete Account", skin);
        changeAvatarBtn = new TextButton("Change Avatar", skin);
        backBtn = new TextButton(Text.GO_BACK.getText(), skin)

        // Add buttons to details
        dataTable.add(changeUsernameBtn).fillX().pad(5);
        dataTable.row();
        dataTable.add(changePasswordBtn).fillX().pad(5);
        dataTable.row();
        dataTable.add(changeAvatarBtn).fillX().pad(5);
        dataTable.row();
        dataTable.add(deleteAccountBtn).fillX().pad(5);

        buttonTable.add(dataTable);
        buttonTable.row();

        messageLabel = new Label("", skin);
        messageLabel.setVisible(false);
        messageLabel.setText("");
        buttonTable.add(messageLabel).colspan(2).fillX().padTop(20).height(30);


    }


    public void render(float delta) {

        stage.act(delta);
        stage.draw();
    }


    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    private void drawAvatar() {

    }
}
