package com.settlers.rpg.utils;

import com.settlers.rpg.helper.LogConfigTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class LogConfigTest {
    private Logger rootLogger;

    @BeforeEach
    void setupEach() {
        rootLogger = Logger.getLogger("");
        // Reset logger state before each test
        for (Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }
        LogConfigTestHelper.resetInitializedFlag();
    }

    @Test
    @DisplayName("setup() should configure root logger with FileHandler and ConsoleHandler")
    void testSetupConfiguresLogger() {
        LogConfig.setup();

        Handler[] handlers = rootLogger.getHandlers();

        // Check there are exactly 2 handlers
        assertEquals(2, handlers.length, "Root logger should have 2 handlers");

        boolean hasFileHandler = false;
        boolean hasConsoleHandler = false;

        for (Handler handler : handlers) {
            if (handler instanceof FileHandler) hasFileHandler = true;
            if (handler instanceof ConsoleHandler) hasConsoleHandler = true;
        }

        assertTrue(hasFileHandler, "FileHandler should be present");
        assertTrue(hasConsoleHandler, "ConsoleHandler should be present");
        assertEquals(Level.INFO, rootLogger.getLevel(), "Root logger level should be INFO");

        // Check log directory and file exist
        File logDir = new File("logs");
        File logFile = new File("logs/game.log");

        assertTrue(logDir.exists(), "logs directory should exist");
        assertTrue(logFile.exists() || logFile.isFile(), "game.log file should exist or be created on first log");
    }

    @Test
    @DisplayName("setup() should be idempotent (no duplicate handlers after multiple calls)")
    void testSetupIsIdempotent() {
        LogConfig.setup();
        Handler[] firstCall = rootLogger.getHandlers();

        LogConfig.setup();
        Handler[] secondCall = rootLogger.getHandlers();

        assertEquals(firstCall.length, secondCall.length, "Handler count should remain the same after multiple setups");
    }

    @AfterEach
    void tearDown() {
        // Clean up log handlers
        for (Handler handler : rootLogger.getHandlers()) {
            handler.close();
            rootLogger.removeHandler(handler);
        }
    }
}