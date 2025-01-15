package com.jkpr.chinesecheckers.server.UI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jkpr.chinesecheckers.server.ChoiceBase;

/**
 * The {@code ServerWindow} class is a graphical interface used to configure server game options.
 * It allows the user to select a game type and the number of players through dropdown menus.
 * This class extends {@link ApplicationAdapter} from libGDX to create and manage a UI window.
 */
public class ServerWindow extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private TextButton submit;
    private SelectBox<String> typeSelect;
    private SelectBox<String> numberSelect;
    private SelectBox<String> botSelect;
    private GameOptions options;

    /**
     * Constructs a {@code ServerWindow} object with a reference to the {@code GameOptions} object,
     * which is used to store the selected game configuration.
     *
     * @param options A {@code GameOptions} object that stores the game type and player count selected by the user.
     */
    public ServerWindow(GameOptions options) {
        this.options = options;
    }

    /**
     * Initializes the stage, loads the UI skin, and sets up the window with dropdown menus and a submit button.
     * The user selects a game type and player count, which is used to configure the game on the server side.
     */
    @Override
    public void create() {
        // Create the stage and load the skin
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Window window = new Window("", skin);

        // Set window options
        window.setSize(500, 500);
        window.setPosition(Gdx.graphics.getWidth() / 2f - window.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - window.getHeight() / 2f);

        // Add components to the window
        ChoiceBase base = new ChoiceBase();
        String[] typeOptions = base.getKeys();

        typeSelect = new SelectBox<>(skin);
        numberSelect = new SelectBox<>(skin);
        botSelect = new SelectBox<>(skin);
        submit = new TextButton("submit", skin);

        // Set dimensions for UI components
        typeSelect.setHeight(30);
        typeSelect.setWidth(200);
        botSelect.setHeight(30);
        botSelect.setWidth(200);
        numberSelect.setHeight(30);
        numberSelect.setWidth(200);

        // Initialize select box items
        typeSelect.setItems(typeOptions);
        numberSelect.setItems(base.getArray(typeSelect.getSelected()));
        setBotSelect();

        // Set positions for components
        typeSelect.setPosition(10, 400);
        numberSelect.setPosition(10, 300);
        botSelect.setPosition(10, 200);

        submit.setPosition(50, 100);

        // Add UI components to the window
        window.addActor(typeSelect);
        window.addActor(numberSelect);
        window.addActor(botSelect);
        window.addActor(submit);
        stage.addActor(window);

        // Set listeners for user selection
        typeSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = typeSelect.getSelected();
                numberSelect.setItems(base.getArray(selected));
            }
        });

        numberSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setBotSelect();
            }
        });

        submit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(typeSelect.getSelected() + " " + numberSelect.getSelected());

                options.setData(typeSelect.getSelected(),
                        numberSelect.getSelected(),
                        botSelect.getSelected());
                Gdx.app.exit();
            }
        });
    }
    private void setBotSelect()
    {
        int number=Integer.parseInt(numberSelect.getSelected());
        String[] bots=new String[number];
        for(int i=0;i<number;i++)
        {
            bots[i]=String.valueOf(i);
        }
        botSelect.setItems(bots);
    }
    /**
     * Renders the stage and its components, clearing the screen before rendering.
     */
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * Disposes of the stage and skin resources when the application is closed.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
