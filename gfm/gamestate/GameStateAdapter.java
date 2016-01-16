package gfm.gamestate;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.Game;

public class GameStateAdapter extends GameState {


   public GameStateAdapter(Game game) {
      super(game);
   }

   public GameStateAdapter(Game game, String stateName) {
      super(game, stateName);
   }

   @Override
   public void draw(Graphics pen) { }
   @Override
   public void update() { }
   @Override
   public void initUI() { }
   @Override
   public void init() { }

   @Override
   public void keyPressed(KeyEvent event) { }
   @Override
   public void keyReleased(KeyEvent event) { }
   @Override
   public void keyTyped(KeyEvent event) { }
   @Override
   public void mouseClicked(MouseEvent event) { }
   @Override
   public void mouseDragged(MouseEvent event) { }
   @Override
   public void mouseEntered(MouseEvent event) { }
   @Override
   public void mouseExited(MouseEvent event) { }
   @Override
   public void mouseMoved(MouseEvent event) { }
   @Override
   public void mousePressed(MouseEvent event) { }
   @Override
   public void mouseReleased(MouseEvent event) { }
   @Override
   public void mouseWheelMoved(MouseWheelEvent event) { }
}
