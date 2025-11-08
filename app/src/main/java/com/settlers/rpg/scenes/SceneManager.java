package com.settlers.rpg.scenes;

import com.settlers.rpg.utils.Log;

public class SceneManager {
    private Scene currentScene;

    public SceneManager(Scene scene){
        this.currentScene = scene;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene newScene) {
        if(newScene == null){
            Log.SCENE.warning("Attempted to load a null scene");
            return;
        }
        Log.SCENE.info("Transition: " + currentScene.getName() + " to " + newScene.getName());
        this.currentScene = newScene;
    }
}
