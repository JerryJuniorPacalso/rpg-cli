package com.settlers.rpg.game;

import com.settlers.rpg.utils.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {

    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream logOut;
    private Handler testHandler;

    @BeforeEach
    void setUp() {
        logOut = new ByteArrayOutputStream();
        testHandler = new StreamHandler(logOut, new SimpleFormatter());
        Log.SYSTEM.addHandler(testHandler);
        Log.SYSTEM.setUseParentHandlers(false);
    }

    @AfterEach
    void tearDown() {
        Log.SYSTEM.removeHandler(testHandler);
        System.setIn(originalIn);
    }

    private String getLogOutput() {
        testHandler.flush();
        return logOut.toString();
    }

    @Test
    @DisplayName("Game.start() should initialize and terminate cleanly")
    void testGameStart() {
        // Patch System.in so scanner won’t block
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));

        // Create an inline subclass of Game that injects mock scene
        Game game = new Game();

        game.start();

        String logs = getLogOutput();
        assertTrue(logs.contains("Start Game"), "Should log start of game");
        assertTrue(logs.contains("End Game"), "Should log end of game");
    }
}