package com.settlers.rpg.scenes;

import com.settlers.rpg.utils.ConsolePrinter;
import com.settlers.rpg.utils.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainMenuSceneTest {

    private MainMenuScene scene;
    private ByteArrayOutputStream logOut;
    private Handler testHandler;

    @BeforeEach
    void setUp() {
        scene = new MainMenuScene();

        // Capture log output
        logOut = new ByteArrayOutputStream();
        testHandler = new StreamHandler(logOut, new SimpleFormatter());
        Log.SYSTEM.addHandler(testHandler);
        Log.SYSTEM.setUseParentHandlers(false); // prevent double printing
    }

    @AfterEach
    void tearDown() {
        Log.SYSTEM.removeHandler(testHandler);
    }

    private String getLogOutput() {
        testHandler.flush();
        return logOut.toString();
    }

    @Test
    @DisplayName("render() should log start and end messages and display menu")
    void testRenderLogsAndMenuPrinted() {
        // Redirect ConsolePrinter output to a test stream
        ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
        PrintStream customOut = new PrintStream(consoleOut);
        ConsolePrinter.setOutput(customOut);

        scene.render();

        String logOutput = getLogOutput();
        String printedOutput = consoleOut.toString();

        // Verify logs
        assertTrue(logOutput.contains("Start main menu scene"));
        assertTrue(logOutput.contains("End main menu scene"));

        // Verify console output
        assertTrue(printedOutput.contains("Welcome to the Game!".toUpperCase()));
        assertTrue(printedOutput.contains("1. New Game"));
        assertTrue(printedOutput.contains("2. Exit"));
        assertTrue(printedOutput.contains("Choose an option"));

        ConsolePrinter.resetOutput();
    }

    @Test
    @DisplayName("handleInput() with '1' should log new game selection")
    void testHandleInput_NewGame() {
        scene.handleInput("1");

        String logs = getLogOutput();
        assertTrue(logs.contains("Handling input for main menu scene, input=1"));
        assertTrue(logs.contains("Player has chosen new game"));
    }

    @Test
    @DisplayName("handleInput() with '2' should log exit selection")
    void testHandleInput_Exit() {
        scene.handleInput("2");

        String logs = getLogOutput();
        assertTrue(logs.contains("Handling input for main menu scene, input=2"));
        assertTrue(logs.contains("Player has chosen to exit the game"));
    }

    @Test
    @DisplayName("handleInput() with invalid string should log warning and unrecognized input")
    void testHandleInput_InvalidInput() {
        scene.handleInput("abc");

        String logs = getLogOutput();
        assertTrue(logs.contains("Unrecognized player option"), "Should warn about invalid input");
        assertTrue(logs.contains("Unrecognized input"), "Should log unrecognized input during update");
    }

    @Test
    @DisplayName("setInput() should update input field correctly")
    void testSetInput() throws Exception {
        scene.setInput(2);
        // Reflectively check internal state (optional)
        var field = MainMenuScene.class.getDeclaredField("input");
        field.setAccessible(true);
        int value = (int) field.get(scene);
        assertEquals(2, value);
    }
}
