package com.settlers.rpg.scenes;

import com.settlers.rpg.utils.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SceneManagerTest {

    private Scene mockSceneA;
    private Scene mockSceneB;
    private SceneManager sceneManager;

    private ByteArrayOutputStream logOut;
    private Handler testHandler;

    @BeforeEach
    void setUp() {
        // Create simple mock Scenes (no mocking frameworks needed)
        mockSceneA = new Scene() {
            @Override public void render() {}
            @Override public void handleInput(String input) {}
            @Override public void update() {}
            @Override public String getName() { return "SceneA"; }
        };

        mockSceneB = new Scene() {
            @Override public void render() {}
            @Override public void handleInput(String input) {}
            @Override public void update() {}
            @Override public String getName() { return "SceneB"; }
        };

        // Start SceneManager with SceneA
        sceneManager = new SceneManager(mockSceneA);

        // Capture log output from Log.SCENE
        logOut = new ByteArrayOutputStream();
        testHandler = new StreamHandler(logOut, new SimpleFormatter());
        Log.SCENE.addHandler(testHandler);
        Log.SCENE.setUseParentHandlers(false);
    }

    @AfterEach
    void tearDown() {
        Log.SCENE.removeHandler(testHandler);
    }

    private String getLogOutput() {
        testHandler.flush();
        return logOut.toString();
    }

    @Test
    @DisplayName("Initial scene should match the one provided to the constructor")
    void testInitialScene() {
        assertEquals(mockSceneA, sceneManager.getCurrentScene());
    }

    @Test
    @DisplayName("Setting a new valid scene should update current scene and log transition")
    void testSetCurrentScene_ValidTransition() {
        sceneManager.setCurrentScene(mockSceneB);

        assertEquals(mockSceneB, sceneManager.getCurrentScene());

        String logs = getLogOutput();
        assertTrue(logs.contains("Transition: SceneA to SceneB"), "Should log valid scene transition");
    }

    @Test
    @DisplayName("Setting null scene should not change current scene and should log warning")
    void testSetCurrentScene_NullScene() {
        sceneManager.setCurrentScene(null);

        assertEquals(mockSceneA, sceneManager.getCurrentScene(), "Scene should remain unchanged");

        String logs = getLogOutput();
        assertTrue(logs.contains("Attempted to load a null scene"), "Should log warning for null scene");
    }
}