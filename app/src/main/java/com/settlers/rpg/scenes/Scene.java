package com.settlers.rpg.scenes;

public interface Scene {
    void render();
    Scene handleInput(String input);
    Scene update();
    String getName();
}
