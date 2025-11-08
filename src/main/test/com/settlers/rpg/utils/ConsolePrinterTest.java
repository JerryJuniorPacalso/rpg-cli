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
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        outContent.reset();
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
}