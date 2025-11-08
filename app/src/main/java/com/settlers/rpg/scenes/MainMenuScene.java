package com.settlers.rpg.scenes;

import com.settlers.rpg.utils.ConsolePrinter;
import com.settlers.rpg.utils.Log;

public class MainMenuScene implements Scene{

    private int input = 0;

    @Override
    public void render() {
        Log.SYSTEM.info("Start main menu scene");
        ConsolePrinter.title("Welcome to the Game!");
        ConsolePrinter.info("1. New Game");
        ConsolePrinter.info("2. Exit");
        ConsolePrinter.prompt("Choose an option");
        Log.SYSTEM.info("End main menu scene");
    }

    @Override
    public void handleInput(String input) {
        Log.SYSTEM.info("Handling input for main menu scene, input="+input);
        int inputAsInt;
        try{
            inputAsInt = Integer.parseInt(input);
        } catch (NumberFormatException nfe){
            Log.SYSTEM.warning("Unrecognized player option");
            inputAsInt = 0;
        }
        setInput(inputAsInt);
        update();
    }

    @Override
    public void update() {
        switch (input){
            case 1:
                Log.SYSTEM.info("Player has chosen new game");
                break;
            case 2:
                Log.SYSTEM.info("Player has chosen to exit the game");
                break;
            default:
                Log.SYSTEM.info("Unrecognized input");
        }
    }

    public void setInput(int value){
        this.input = value;
    }
}
