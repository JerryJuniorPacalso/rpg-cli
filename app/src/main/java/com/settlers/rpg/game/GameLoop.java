package com.settlers.rpg.game;

import com.settlers.rpg.scenes.ExitScene;
import com.settlers.rpg.scenes.Scene;
import com.settlers.rpg.scenes.SceneManager;
import com.settlers.rpg.utils.ConsolePrinter;
import com.settlers.rpg.utils.Log;

import java.util.Scanner;

public class GameLoop {

    private final SceneManager sceneManager;
    private boolean running = true;
    private final Scanner scanner = new Scanner(System.in);

    public GameLoop(SceneManager sceneManager){
        this.sceneManager = sceneManager;
    }

    public void run() {
        Log.GAME.info("Start Game Loop");
        while(running){
            Scene scene = sceneManager.getCurrentScene();

            try{
                if(scene instanceof ExitScene){
                    stop();
                    continue;
                }

                scene.render();
                String input = scanner.nextLine().trim();

                Scene nextScene = scene.handleInput(input);

                if(nextScene == null){
                    Log.GAME.warning("Scene returned null, stopping game");
                    stop();
                    continue;
                }

                if(nextScene != scene){
                    sceneManager.setCurrentScene(nextScene);
                }

            }catch (Exception e){
                Log.SYSTEM.severe("Error in GameLoop: " + e.getMessage());
                ConsolePrinter.error("Unexpected error! Check logs for details.");
                stop();
            }
        }
        Log.GAME.info("End Game Loop");
    }

    private void stop(){
        running = false;
    }
}
