package com.settlers.rpg.scene;

public interface Scene {
    void render();
    void handleInput(String input);
    void update();
}
