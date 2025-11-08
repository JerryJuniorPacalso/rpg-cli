package com.settlers.rpg.game;

import com.settlers.rpg.scenes.MainMenuScene;
import com.settlers.rpg.scenes.SceneManager;
import com.settlers.rpg.utils.Log;

public class Game {
    public void start(){
        Log.SYSTEM.info("Start Game");
        SceneManager sceneManager = new SceneManager(new MainMenuScene());
        GameLoop gameLoop = new GameLoop(sceneManager);
        gameLoop.run();
        Log.SYSTEM.info("End Game");
    }
}
