package com.settlers.rpg;

import com.settlers.rpg.game.Game;

public class Main{
    static void main(String[] args){
       System.out.println("Starting game");
       Game game = new Game();
       game.start();
    }
}