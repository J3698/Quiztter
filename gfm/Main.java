package gfm;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JOptionPane;

/**
 * GFM
 * @version 1.0
 * @author Antioch John Sanders
 */

import gfm.gamestate.GameState;
import gfm.gui.MenuButton;
import gfm.templates.FileFactory;
import gfm.util.Vec2;

public class Main {
   public static int gameWidth = 640;
   public static int gameHeight = 480;
   public static int drawWidth = 770;
   public static int drawHeight = 580;

   public static void main(String[] args) {
      Game game = new Game("GFM", gameWidth, gameHeight);

      game.getGamePanel().getGameStateManager().setGameState("templates");
      game.getGamePanel().getGameStateManager().add(
            "templates", new PlayGround(game, "templates"));

      game.start();
   }
}

class PlayGround extends GameState {

   public PlayGround(Game game) {
      super(game);
   }
   public PlayGround (Game game, String gameMode) {
      super(game, gameMode);
   }

   @Override
   public void draw(Graphics pen) {
      pen.clearRect(0, 0, getWidth(), getHeight());
      getGUIManager().draw(pen);
   }
   @Override
   public void update() {
   }

   @Override
   public void init() {}

   @Override
   public void initUI() {
      // some args (last 2) redundant
      getGUIManager().addButton(
            new MenuButton(
                  new AddGameStateListener(), "New G S", new Vec2(50, 50), new Vec2(90, 90),
                  getWidth(), getHeight()));

      getGUIManager().addButton(
            new MenuButton(
                  new AddMainListener(), "New Main", new Vec2(150, 50), new Vec2(90, 90),
                  getWidth(), getHeight()));
   }

   private class AddGameStateListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         getGUIManager().disable();
         String name = JOptionPane.showInputDialog("Game State Name: ");
         if ( name != null ) {
            FileFactory.newGameState(name);
         }
         getGUIManager().enable();
      }
   }

   private class AddMainListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         getGUIManager().disable();
         String name = JOptionPane.showInputDialog("Name: ");
         if ( name != null ) {
            FileFactory.newMain(name);
         }
         getGUIManager().enable();
      }
   }

   @Override
   public void keyPressed(KeyEvent event) {
   }
   @Override
   public void keyReleased(KeyEvent event) {
   }
   @Override
   public void keyTyped(KeyEvent event) {
   }

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