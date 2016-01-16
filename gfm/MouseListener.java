package gfm;

import gfm.gamestate.GameState;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseListener extends MouseAdapter {
   private GameState myGameState;

   public MouseListener(GameState gameState) {
      myGameState = gameState;
   }

   public void mouseClicked(MouseEvent event) {
      myGameState.mouseClicked(event);
   }
   public void mouseDragged(MouseEvent event) {
      myGameState.mouseDragged(event);
   }
   public void mouseEntered(MouseEvent event) {
      myGameState.mouseEntered(event);
   }
   public void mouseExited(MouseEvent event) {
      myGameState.mouseExited(event);
   }
   public void mouseMoved(MouseEvent event) {
      myGameState.mouseMoved(event);
   }
   public void mousePressed(MouseEvent event) {
      myGameState.mousePressed(event);
   }
   public void mouseReleased(MouseEvent event) {
      myGameState.mouseReleased(event);
   }
   public void mouseWheelMoved(MouseWheelEvent event) {
      myGameState.mouseWheelMoved(event);
   }
}