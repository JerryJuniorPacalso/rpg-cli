package com.settlers.rpg;

import com.settlers.rpg.game.Game;

public class Main{
    static void main(String[] args){
       System.out.println("class=Main, method=main, message=Initialize and start game");
       Game game = new Game();
       game.start();
    }
}