package gfm.example;

/**
 * Example
 * @version 1.0
 * @author
 */

import gfm.Game;

public class Example {
   // draw space, different from space taken up on screen
   public static int gameWidth = 640;
   public static int gameHeight = 480;

   public static void main(String[] args) {
      Game game = new Game("-Example:Breakout-", gameWidth, gameHeight);
      game.setGameState("play");
      game.addGameState(new Editor(game));
      game.addGameState(new Play(game));
      game.setFullScreen();
      game.start();
   }
}