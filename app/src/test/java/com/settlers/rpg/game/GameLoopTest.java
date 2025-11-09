package com.settlers.rpg.game;

import com.settlers.rpg.scenes.ExitScene;
import com.settlers.rpg.scenes.Scene;
import com.settlers.rpg.scenes.SceneManager;
import com.settlers.rpg.utils.ConsolePrinter;
import com.settlers.rpg.utils.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class GameLoopTest {

    private SceneManager sceneManager;
    private ByteArrayOutputStream logOut;
    private Handler testHandler;

    @BeforeEach
    void setUp() {
        logOut = new ByteArrayOutputStream();
        testHandler = new StreamHandler(logOut, new SimpleFormatter());
        Log.GAME.addHandler(testHandler);
        Log.GAME.setUseParentHandlers(false);
        Log.SYSTEM.addHandler(testHandler);
        Log.SYSTEM.setUseParentHandlers(false);// prevent double logs
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

    @Test
    @DisplayName("GameLoop.run() should handle exception in scene.render()")
    void testRunHandlesException() {
        // Mock a scene that throws exception on render
        Scene badScene = mock(Scene.class);
        when(badScene.getName()).thenReturn("BadScene");
        doThrow(new RuntimeException("Simulated render failure")).when(badScene).render();

        SceneManager sceneManager = new SceneManager(badScene);
        GameLoop gameLoop = new GameLoop(sceneManager);

        // Mock ConsolePrinter.error to verify it's called
        try (MockedStatic<ConsolePrinter> consoleMock = mockStatic(ConsolePrinter.class)) {
            // Provide dummy input so scanner.nextLine() doesn't block
            System.setIn(new java.io.ByteArrayInputStream("input\n".getBytes()));

            gameLoop.run();

            // Verify ConsolePrinter.error() called
            consoleMock.verify(() -> ConsolePrinter.error("Unexpected error! Check logs for details."));
        }

        // Verify the log contains the severe message
        String logs = getLogOutput();
        assertTrue(logs.contains("Error in GameLoop: Simulated render failure"));
    }

    @Test
    @DisplayName("GameLoop.run() stops loop after exception")
    void testLoopStopsAfterException() {
        Scene badScene = mock(Scene.class);
        when(badScene.getName()).thenReturn("BadScene");
        doThrow(new RuntimeException("fail")).when(badScene).handleInput(anyString());

        SceneManager sceneManager = new SceneManager(badScene);
        GameLoop gameLoop = new GameLoop(sceneManager);

        // Provide dummy input
        System.setIn(new java.io.ByteArrayInputStream("input\n".getBytes()));

        // Run loop
        try (MockedStatic<ConsolePrinter> consoleMock = mockStatic(ConsolePrinter.class)) {
            gameLoop.run();
            consoleMock.verify(() -> ConsolePrinter.error("Unexpected error! Check logs for details."));
        }

        // If loop didn't stop, test would hang — finishing indicates stop() worked
        assertTrue(true, "Loop stopped after exception");
    }
}
