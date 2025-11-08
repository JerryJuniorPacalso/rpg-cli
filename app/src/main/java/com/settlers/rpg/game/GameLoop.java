package com.settlers.rpg.game;

import com.settlers.rpg.scenes.SceneManager;
import com.settlers.rpg.utils.Log;

public class GameLoop {

    private final SceneManager sceneManager;

    public GameLoop(SceneManager sceneManager){
        this.sceneManager = sceneManager;
    }

    public void run() {
        Log.SYSTEM.info("Start Game Loop");
        //TODO: Add game loop here
        Log.SYSTEM.info("End Game Loop");
    }
}
