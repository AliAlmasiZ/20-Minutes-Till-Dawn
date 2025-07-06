package io.github.AliAlmasiZ.tillDawn.views.dialogs;


import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.AliAlmasiZ.tillDawn.models.GameAssetManager;
import io.github.AliAlmasiZ.tillDawn.models.enums.AbilityType;
import io.github.AliAlmasiZ.tillDawn.views.Text;
import io.github.AliAlmasiZ.tillDawn.views.screens.GameScreen;

public class SelectAbilityDialog extends Dialog {
    GameScreen screen;
    public SelectAbilityDialog(GameScreen screen) {
        super("", GameAssetManager.getGameAssetManager().pixthulhuuiSkin);
        this.screen = screen;

        SelectBox<AbilityType> abilityTypeSelectBox = new SelectBox<>(getSkin());
        abilityTypeSelectBox.setItems(AbilityType.values());
        TextButton submit = new TextButton(Text.SUBMIT.getText(), getSkin());
        Label label = new Label(Text.SELECT_ABILITY.getText(), getSkin());

        getContentTable().add(label).pad(20).row();
        getContentTable().add(abilityTypeSelectBox).pad(20).row();
        getContentTable().add(submit).pad(20);

        submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.addAbility(abilityTypeSelectBox.getSelected());
                hide();
            }
        });
    }

    @Override
    public Dialog show(Stage stage) {
        screen.isPaused = true;
        return super.show(stage);
    }

    @Override
    public void hide() {
        screen.isPaused = false;
        super.hide();
    }
}
