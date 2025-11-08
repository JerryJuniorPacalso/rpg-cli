package com.settlers.rpg.scenes;

import com.settlers.rpg.utils.ConsolePrinter;
import com.settlers.rpg.utils.Log;

public class MainMenuScene implements Scene{

    private int input = 0;
    private final String name;

    public MainMenuScene(){
        name = "MainMenuScene";
    }

    @Override
    public void render() {
        Log.SCENE.info("Start main menu scene");
        ConsolePrinter.title("Welcome to the Game!");
        ConsolePrinter.info("1. New Game");
        ConsolePrinter.info("2. Exit");
        ConsolePrinter.prompt("Choose an option");
        Log.SCENE.info("End main menu scene");
    }

    @Override
    public Scene handleInput(String input) {
        Log.SCENE.info("Handling input for main menu scene, input="+input);
        int inputAsInt;
        try{
            inputAsInt = Integer.parseInt(input);
        } catch (NumberFormatException nfe){
            Log.SCENE.warning("Unrecognized player option");
            inputAsInt = 0;
        }
        setInput(inputAsInt);
        return update();
    }

    @Override
    public Scene update() {
        switch (input){
            case 1:
                Log.SCENE.info("Player has chosen new game");
                break;
            case 2:
                Log.SCENE.info("Player has chosen to exit the game");
                break;
            default:
                Log.SCENE.info("Unrecognized input");
        }
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setInput(int value){
        this.input = value;
    }
}
