package com.settlers.rpg.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConsolePrinterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream customOut = new PrintStream(outContent);

    @BeforeEach
    void setUpStreams() {
        ConsolePrinter.setOutput(customOut);
    }

    @AfterEach
    void restoreStreams() {
        ConsolePrinter.resetOutput();
    }

    @Test
    @DisplayName("title() prints uppercase text with surrounding equals")
    void testTitle() {
        ConsolePrinter.title("Main Menu");
        String output = outContent.toString().trim();

        assertTrue(output.contains("==== MAIN MENU ===="),
                "Title should be uppercase and wrapped with ===");
    }

    @Test
    @DisplayName("info() prints message")
    void testInfo() {
        ConsolePrinter.info("Game started");
        String output = outContent.toString().trim();

        assertEquals("Game started", output,
                "Info message should be displayed");
    }

    @Test
    @DisplayName("error() prints to with [!] prefix")
    void testError() {
        ConsolePrinter.error("Something went wrong");
        String output = outContent.toString().trim();

        assertEquals("[!] Something went wrong", output,
                "Error message should print with [!] prefix");
    }

    @Test
    @DisplayName("prompt() prints text without newline")
    void testPrompt() {
        ConsolePrinter.prompt("Enter name:");
        String output = outContent.toString();

        assertTrue(output.startsWith("Enter name: "),
                "Prompt should print the given text");
        assertFalse(output.endsWith("\n"),
                "Prompt should not add a newline");
    }

    @Test
    @DisplayName("Multiple calls append correctly")
    void testMultipleCalls() {
        ConsolePrinter.info("First line");
        ConsolePrinter.info("Second line");

        String[] lines = outContent.toString().trim().split("\\r?\\n");

        assertEquals(2, lines.length, "Two lines should be printed");
        assertEquals("First line", lines[0]);
        assertEquals("Second line", lines[1]);
    }


    @Test
    @DisplayName("success() should print message with green color code")
    void testSuccessPrintsGreenMessage() {
        String message = "Operation successful!";
        ConsolePrinter.success(message);

        String output = outContent.toString();

        assertTrue(output.contains(message), "Output should contain the message text");
        assertTrue(output.contains("\u001B[32m"), "Output should contain green ANSI code");
        assertTrue(output.contains("\u001B[0m"), "Output should reset ANSI color");
    }

    @Test
    @DisplayName("warn() should print message with yellow color code")
    void testWarnPrintsYellowMessage() {
        String message = "Low HP warning!";
        ConsolePrinter.warn(message);

        String output = outContent.toString();

        assertTrue(output.contains(message), "Output should contain the message text");
        assertTrue(output.contains("\u001B[33m"), "Output should contain yellow ANSI code");
        assertTrue(output.contains("\u001B[0m"), "Output should reset ANSI color");
    }
}