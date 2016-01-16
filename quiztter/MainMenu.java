/*
package quiztter;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JOptionPane;

import gfm.GamePanel;
import gfm.gamestate.FadeTransition;
import gfm.gamestate.GameState;
import gfm.gamestate.Transition;
import gfm.gui.Button;
import gfm.util.Vec2;

public class MainMenu extends GameState {
   public MainMenu (GamePanel gamePanel) {
      super(gamePanel);
   }
   public MainMenu (GamePanel gamePanel, String gameMode) {
      super(gamePanel, gameMode);
   }

   @Override
   public void init() {
   }
   @Override
   public void initUI() {
      int width = getGamePanel().getGameWidth();
      int height = getGamePanel().getGameHeight();

      Button play = new MainMenuButton(
            new PlayListener(), "play", new Vec2(width / 2 - 90, height / 2 - 100), new Vec2(140, 50));
      Button credits = new MainMenuButton(
            new CreditsListener(), "credits", new Vec2(width / 2 - 90, height / 2 + 30), new Vec2(140, 50));

      getGUIManager().addButton(play);
      getGUIManager().addButton(credits);
   }

   @Override
   public void draw(Graphics pen) {
      int width = getGamePanel().getGameWidth();
      int height = getGamePanel().getGameHeight();

      pen.setColor(Quiztter.scheme[0]);
      pen.fillRect(0, 0, width, height);

      getGUIManager().draw(pen);
   }
   @Override
   public void update() {
   }

   private class PlayListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         Transition toPlay = new FadeTransition(getGamePanel(), getGameMode(), "play", 100, Quiztter.scheme[0]);
         getGamePanel().getGameStateManager().setGameState(toPlay);
      }
   }

   private class CreditsListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         JOptionPane.showMessageDialog(null, "        Credits\n" + "Creator: Antioch Sanders\n" +
               "Teacher: Mr. Rudwick\n" + "Twitter: Twitter\n" +
               "Sound: www.freesfx.co.uk\n" + "API: twitter4j.org\n" +
               "Support: You :)");
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
 */