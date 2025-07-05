package io.github.AliAlmasiZ.tillDawn.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap;
import io.github.AliAlmasiZ.tillDawn.controllers.utils.ControlsManager;
import io.github.AliAlmasiZ.tillDawn.models.enums.GameAction;

public class InputSettingsDialog extends Dialog {

    private static final float FONT_SCALE = 0.8f;
    public InputSettingsDialog(Skin skin) {
        super("", skin);


        padTop(8f);
        padBottom(8f);
        padLeft(8f);
        padRight(8f);

        Table table = getContentTable();

        table.defaults().pad(4f).minWidth(120f).minHeight(24f);


        for (ObjectMap.Entry<GameAction, Integer> mapping : ControlsManager.getKeyMappings()) {
            if(GameAction.SHOOT.equals(mapping.key))
                continue;
            Label actionLabel = new Label(mapping.key.toString(), skin);
            actionLabel.setFontScale(FONT_SCALE);
            table.add(actionLabel).left();

            TextButton btn = new TextButton(Input.Keys.toString(mapping.value), skin);
            btn.getLabel().setFontScale(FONT_SCALE);
            table.add(btn).height(70).right().row();

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    InputProcessor originalProcessor = Gdx.input.getInputProcessor();
                    btn.setText(Text.ENTER_KEY.getText());
                    Gdx.input.setInputProcessor(new InputAdapter() {
                        @Override
                        public boolean keyDown(int keycode) {
                            if(!ControlsManager.setKeyForAction(mapping.key, keycode))
                                return false;
                            btn.setText(Input.Keys.toString(ControlsManager.getKeyForAction(mapping.key)));
                            Gdx.input.setInputProcessor(originalProcessor);
                            return true;
                        }
                    });


                }
            });
        }

        TextButton back = new TextButton(Text.GO_BACK.getText(), skin);
        back.getLabel().setFontScale(FONT_SCALE);
        table.add(back).colspan(2).center();
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        getTitleLabel().setFontScale(FONT_SCALE);

    }
}
