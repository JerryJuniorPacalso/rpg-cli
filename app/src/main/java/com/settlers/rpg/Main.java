package com.settlers.rpg;

import com.settlers.rpg.game.Game;
import com.settlers.rpg.utils.Log;
import com.settlers.rpg.utils.LogConfig;

public class Main{
    static void main(String[] args){
        LogConfig.setup();
        Log.SYSTEM.info("Game starting....");
        try{
            Game game = new Game();
            game.start();
        }catch (Exception e){
            Log.SYSTEM.severe("Unhandled Exception: "+e.getMessage());
        }
        Log.SYSTEM.info("Game exited normally");
    }
}