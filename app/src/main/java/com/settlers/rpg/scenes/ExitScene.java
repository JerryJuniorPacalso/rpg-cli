package com.settlers.rpg.scenes;

import com.settlers.rpg.utils.ConsolePrinter;

public class ExitScene implements Scene {
    @Override
    public void render() {
        ConsolePrinter.title("Exiting Game");
    }

    @Override
    public Scene handleInput(String input) {
        return update();
    }

    @Override
    public Scene update() {
        return this;
    }

    @Override
    public String getName() {
        return "ExitScene";
    }
}
