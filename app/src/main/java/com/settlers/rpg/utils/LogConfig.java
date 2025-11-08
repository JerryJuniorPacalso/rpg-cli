package com.settlers.rpg.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogConfig {
    private static boolean initialized = false;

    public static void setup(){
        if(initialized){
            return;
        }
        initialized = true;
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);

        for(Handler handler: rootLogger.getHandlers()){
            rootLogger.removeHandler(handler);
        }

        try{
            Files.createDirectories(Paths.get("logs"));

            FileHandler fileHandler = new FileHandler("logs/game.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.WARNING);

            rootLogger.addHandler(fileHandler);
            rootLogger.addHandler(consoleHandler);

        } catch (IOException e) {
            System.err.println("Failed to setup logging, " + e.getMessage());
        }
    }
}