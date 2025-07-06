package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.AliAlmasiZ.tillDawn.models.DataBase.AppData;
import io.github.AliAlmasiZ.tillDawn.models.Result;
import io.github.AliAlmasiZ.tillDawn.models.User;
import io.github.AliAlmasiZ.tillDawn.views.dialogs.AvatarchooseDialog;

public class ProfileMenuView{
    private final AvatarchooseDialog dialog;
    private final User user;

    private final float avatarSize = 300;
    private final float avatarRadius = avatarSize / 2;
    private final Vector2 avatarPosition = new Vector2();


    private Stage stage;
    private Table dataTable, table;

    public final TextButton deleteAccountBtn, backBtn;
    public final TextField usernameField, passwordField;
    public final Image avatar;
    public final Label messageLabel;


    public ProfileMenuView(Skin skin) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        user = AppData.getAppData().getActiveUser();


        messageLabel = new Label("", skin);
        messageLabel.setVisible(false);
        messageLabel.setText("");

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        dialog = new AvatarchooseDialog(skin, this);

        avatar = new Image(user.getAvatarTex());
        avatar.setScaling(Scaling.fit);

        avatar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.show(stage);
            }
        });

        Table avatarTable = new Table();
        avatarTable.center();
        table.add(avatarTable).center().size(avatarSize, avatarSize).row();
        avatarTable.add(avatar).center().grow().space(10);

        table.add(messageLabel).row();


        dataTable = new Table();
        Label usernameLabel = new Label(Text.USERNAME + ": ", skin);
        usernameField = new TextField(user.getUsername(), skin);
        Label passwordLabel = new Label(Text.PASSWORD + ": ", skin);
        passwordField = new TextField(user.getPassword(), skin);
        Label scoreLabel = new Label(Text.SCORE.getText() + ": " + user.getScore(), skin);
        dataTable.add(usernameLabel).left().padBottom(10);
        dataTable.add(usernameField).right().padBottom(10).width(300);
        dataTable.row();
        dataTable.add(passwordLabel).left().padBottom(10);
        dataTable.add(passwordField).right().padBottom(10).width(300);
        dataTable.row();
        dataTable.add(scoreLabel).center().padBottom(20).colspan(2);
        dataTable.row();

        deleteAccountBtn = new TextButton("Delete Account", skin);
        backBtn = new TextButton(Text.GO_BACK.getText(), skin);

        // Add buttons to details
        dataTable.add(backBtn).pad(10);
        dataTable.add(deleteAccountBtn).fillX().colspan(2).pad(10).row();





        table.add(dataTable);



        float width = stage.getViewport().getScreenWidth();
        float height = stage.getViewport().getScreenHeight();



        //Avatar
//        Texture avatarTexture = new Texture(Gdx.files.internal("avatars/default_avatar.png"));
//        avatarImage = new Image(new TextureRegionDrawable(avatarTexture));
//        avatarImage.setSize(128, 128);
//        table.add(avatarImage).padRight(30).size(128, 128);







    }


    public void render(float delta) {

        stage.act(delta);
        stage.draw();







    }


    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }




    private void drawAvatar() {






//        shapes.begin(ShapeRenderer.ShapeType.Filled);
//        shapes.setColor(0.2f, 0.2f, 0.25f, 1);
//        shapes.circle(
//            avatarPosition.x + avatarRadius,
//            avatarPosition.y + avatarRadius,
//            avatarRadius
//        );
//        shapes.end();
//
//        Gdx.gl.glEnable(GL20.GL_STENCIL_TEST);
//        Gdx.gl.glClear(GL20.GL_STENCIL_BUFFER_BIT);
//        Gdx.gl.glStencilFunc(GL20.GL_ALWAYS, 1, 0xFF);
//        Gdx.gl.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_REPLACE);
//        Gdx.gl.glStencilMask(0xFF);
//
//        shapes.begin(ShapeRenderer.ShapeType.Filled);
//        shapes.setColor(Color.WHITE);
//        shapes.circle(
//            avatarPosition.x + avatarRadius,
//            avatarPosition.y + avatarRadius,
//            avatarRadius
//        );
//        shapes.end();
//
//        Gdx.gl.glStencilFunc(GL20.GL_EQUAL, 1, 0xFF);
//        Gdx.gl.glStencilMask(0x00);
//
//        batch.begin();
//        batch.draw(user.getAvatarTex(),
//            avatarPosition.x, avatarPosition.y,
//            avatarSize, avatarSize
//            );
//        batch.end();
//
//        Gdx.gl.glDisable(GL20.GL_STENCIL_TEST);
//
//        shapes.begin(ShapeRenderer.ShapeType.Line);
//        shapes.setColor(0.4f, 0.6f, 1f, 1);
//        shapes.circle(
//            avatarPosition.x + avatarRadius,
//            avatarPosition.y + avatarRadius,
//            avatarRadius
//        );
//        shapes.end();
    }


    public void setStatusMessage(Result result) {
        if(result == null) {
            this.messageLabel.setVisible(false);
            this.messageLabel.setText("");
            return;
        }
        this.messageLabel.setText(result.message());
        this.messageLabel.setVisible(true);
//        this.statusMessageLabel.setVisible(!result.isSuccessful());
    }

}
