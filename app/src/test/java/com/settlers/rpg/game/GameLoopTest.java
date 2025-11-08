package com.settlers.rpg.game;

import com.settlers.rpg.scenes.ExitScene;
import com.settlers.rpg.scenes.Scene;
import com.settlers.rpg.scenes.SceneManager;
import com.settlers.rpg.utils.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameLoopTest {

    private SceneManager sceneManager;
    private ByteArrayOutputStream logOut;
    private Handler testHandler;

    @BeforeEach
    void setUp() {
        logOut = new ByteArrayOutputStream();
        testHandler = new StreamHandler(logOut, new SimpleFormatter());
        Log.GAME.addHandler(testHandler);
        Log.GAME.setUseParentHandlers(false); // prevent double logs
    }

    @AfterEach
    void tearDown() {
        Log.GAME.removeHandler(testHandler);
    }

    private String getLogOutput() {
        testHandler.flush();
        return logOut.toString();
    }

    @Test
    @DisplayName("GameLoop stops immediately when current scene is ExitScene")
    void testRun_ExitSceneStopsImmediately() {
        Scene exitScene = new ExitScene();
        sceneManager = new SceneManager(exitScene);

        GameLoop gameLoop = new GameLoop(sceneManager);

        // Provide dummy input (won't be used)
        System.setIn(new ByteArrayInputStream("".getBytes()));

        gameLoop.run();

        String logs = getLogOutput();
        assertTrue(logs.contains("Start Game Loop"));
        assertTrue(logs.contains("End Game Loop"));
    }

    @Test
    @DisplayName("GameLoop processes one input and transitions to next scene")
    void testRun_TransitionToNextScene() {
        // Mock initial scene
        Scene exitScene = new ExitScene();

        sceneManager = new SceneManager(exitScene);

        // Provide input to satisfy scanner.nextLine()
        String simulatedInput = "2\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        GameLoop gameLoop = new GameLoop(sceneManager);

        gameLoop.run();

        // Check logs
        String logs = getLogOutput();
        assertTrue(logs.contains("Start Game Loop"));
        assertTrue(logs.contains("End Game Loop"));

        // Check that SceneManager transitioned
        assertEquals("ExitScene", sceneManager.getCurrentScene().getName());
    }

    @Test
    @DisplayName("GameLoop stops on null scene returned")
    void testRun_NullSceneStops() {
        Scene sceneA = new Scene() {
            @Override public void render() {}
            @Override public Scene handleInput(String input) { return null; }

            @Override
            public Scene update() {
                return null;
            }

            @Override public String getName() { return "SceneA"; }
        };

        sceneManager = new SceneManager(sceneA);

        // Provide dummy input
        System.setIn(new ByteArrayInputStream("input\n".getBytes()));

        GameLoop gameLoop = new GameLoop(sceneManager);
        gameLoop.run();

        String logs = getLogOutput();
        assertTrue(logs.contains("Scene returned null, stopping game"));
        assertTrue(logs.contains("End Game Loop"));
    }
}
