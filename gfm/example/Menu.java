package gfm.example;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.Game;
import gfm.gamestate.GameState;

public class Menu extends GameState {
   private KeyAdapter myKeyListener;
   private MouseAdapter myMouseListener;

   public Menu (Game game, String gameMode) {
      super(game, gameMode);
      myKeyListener = new KeyListener();
      myMouseListener = new MouseListener();
      initUI();
   }

   @Override
   public void draw(Graphics pen) {
      getGUIManager().draw(pen);
   }
   @Override
   public void update() {
   }
   @Override
   public void initUI() {
   }

   private class KeyListener extends KeyAdapter {
      @Override
      public void keyPressed(KeyEvent event) {
      }
      @Override
      public void keyReleased(KeyEvent event) {
      }
      @Override
      public void keyTyped(KeyEvent event) {
      }
   }

   private class MouseListener extends MouseAdapter {
      @Override
      public void mouseClicked(MouseEvent event) {
         getGUIManager().mousePressed(event);
      }
      @Override
      public void mouseDragged(MouseEvent event) {
      }
      @Override
      public void mouseEntered(MouseEvent event) {
      }
      @Override
      public void mouseExited(MouseEvent event) {
      }
      @Override
      public void mouseMoved(MouseEvent event) {
         getGUIManager().mouseMoved(event);
      }
      @Override
      public void mousePressed(MouseEvent event) {
      }
      @Override
      public void mouseReleased(MouseEvent event) {
      }
      @Override
      public void mouseWheelMoved(MouseWheelEvent event) {
      }
   }

   @Override
   public KeyAdapter getKeyListener() { return myKeyListener; }
   @Override
   public MouseAdapter getMouseListener() { return myMouseListener; }

   @Override
   public void init() {
      // TODO Auto-generated method stub

   }

   @Override
   public void keyPressed(KeyEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void keyReleased(KeyEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void keyTyped(KeyEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseClicked(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseDragged(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseEntered(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseExited(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseMoved(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mousePressed(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseReleased(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseWheelMoved(MouseWheelEvent event) {
      // TODO Auto-generated method stub

   }
}