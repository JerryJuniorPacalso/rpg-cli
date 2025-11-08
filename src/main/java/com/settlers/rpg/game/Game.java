package com.settlers.rpg.game;

public class Game {
    public void start(){
        System.out.println("class=Game, method=start, message=Start Game");
        GameLoop gameLoop = new GameLoop();
        gameLoop.run();
        System.out.println("class=Game, method=start, message=End Game");
    }
}
