package com.settlers.rpg.game;

import com.settlers.rpg.utils.Log;

public class Game {
    public void start(){
        Log.SYSTEM.info("Start Game");
        GameLoop gameLoop = new GameLoop();
        gameLoop.run();
        Log.SYSTEM.info("End Game");
    }
}
