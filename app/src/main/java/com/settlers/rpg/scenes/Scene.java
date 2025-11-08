package com.settlers.rpg.scenes;

public interface Scene {
    void render();
    void handleInput(String input);
    void update();
}
