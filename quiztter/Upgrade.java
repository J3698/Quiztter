package quiztter;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Random;

import javax.swing.JOptionPane;

import gfm.Game;
import gfm.gamestate.GameState;
import gfm.util.StringDraw;

public class Upgrade extends GameState {
   private volatile boolean myStarted;
   private volatile boolean myFinished;
   private volatile boolean myWasSuccesful;

   public Upgrade(Game game) {
      super(game);
   }
   public Upgrade(Game game, String stateName) {
      super(game, stateName);
   }

   @Override
   public void draw(Graphics pen) {
      int width = getWidth();
      int height = getHeight();

      pen.setFont(new Font("Ariel", 1, 40));
      pen.setColor(Main.scheme[0]);
      StringDraw.drawStringCenter(pen, "Downloading...", width / 2, height / 5);

      if ( getGame().getLauncher().getBytesDownloaded() != null ) {
         double progress = getGame().getLauncher().getBytesDownloaded() /
               (double) getGame().getLauncher().getUpgradeSize();
         System.out.println(getGame().getLauncher().getBytesDownloaded());
         pen.fillRect(0, height / 3, (int) (progress * width), 30);
      }

      String letters = "01";
      letters += letters.toUpperCase();
      Random chooser = new Random();
      pen.setFont(new Font("Ariel", 1, 15));

      for ( int i = 0; i < 100; i++ ) {
         int x = chooser.nextInt(width);
         int y = chooser.nextInt(height);
         int letter = chooser.nextInt(letters.length());

         pen.drawString("" + letters.charAt(letter), x, y);
      }
   }

   @Override
   public void update() {
      if ( !myStarted ) {
         myStarted = true;
         doWork();
      } else if ( myFinished ) {
         if ( myWasSuccesful ) {
            JOptionPane.showMessageDialog(null, "Update Succes!");
            System.exit(0);
         } else {
            JOptionPane.showMessageDialog(null, "Update Fail.");
            getGame().setGameState("intro");
         }
      }
   }

   public void doWork() {
      new Thread() {
         @Override
         public void run() {
            myWasSuccesful = getGame().getLauncher().downloadLatestVersion();
            myFinished = true;
         }
      }.start();
   }

   @Override
   public void initGUI() {
   }

   @Override
   public void init() {
      myStarted = false;
      myFinished = false;
      myWasSuccesful = false;
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
