package gfm;

import gfm.gamestate.GameState;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
   private GameState myGameState;

   public KeyListener(GameState gameState) {
      myGameState = gameState;
   }

   public void keyPressed(KeyEvent event) {
      myGameState.keyPressed(event);
   }
   public void keyReleased(KeyEvent event) {
      myGameState.keyReleased(event);
   }
   public void keyTyped(KeyEvent event) {
      myGameState.keyTyped(event);
   }
}