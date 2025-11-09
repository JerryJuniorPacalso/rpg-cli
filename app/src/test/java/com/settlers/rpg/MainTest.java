package com.settlers.rpg;

import com.settlers.rpg.game.Game;
import com.settlers.rpg.utils.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.ByteArrayOutputStream;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockConstruction;

public class MainTest {

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
    }

    private String getLogOutput() {
        testHandler.flush();
        return logOut.toString();
    }

    @Test
    @DisplayName("Main.main() should initialize logging and start the game cleanly")
    void testMainRunsSuccessfully() {
        try (MockedConstruction<Game> mocked = mockConstruction(Game.class, (mock, context) -> {
            // When game.start() is called, just log something and exit
            doAnswer(invocation -> {
                Log.SYSTEM.info("Mock Game started");
                return null;
            }).when(mock).start();
        })) {

            Main.main(new String[]{});

            String logs = getLogOutput();

            assertTrue(logs.contains("Game starting"), "Should log game starting");
            assertTrue(logs.contains("Mock Game started"), "Should invoke Game.start()");
            assertTrue(logs.contains("Game exited normally"), "Should log normal exit");
        }
    }

    @Test
    @DisplayName("Main.main() should log severe message on exception")
    void testMainLogsException() {
        try (MockedConstruction<Game> mocked = mockConstruction(Game.class, (mock, context) -> {
            doThrow(new RuntimeException("Simulated failure")).when(mock).start();
        })) {

            Main.main(new String[]{});

            String logs = getLogOutput();
            assertTrue(logs.contains("Unhandled Exception: Simulated failure"), "Should log exception");
            assertTrue(logs.contains("Game exited normally"), "Should log normal exit after failure");
        }
    }
}