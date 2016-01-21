package quiztter;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.swing.JOptionPane;

import gfm.Game;
import gfm.gamestate.FadeTransition;
import gfm.gamestate.GameState;
import gfm.gamestate.Transition;
import gfm.util.StringDraw;

public class PinInput extends GameState {
   private static final String digits = "0123456789";
   private static final int maxPinLength = 12;

   private String myPin;
   private int myTick;

   public PinInput(Game game) {
      super(game);
   }
   public PinInput(Game game, String gameMode) {
      super(game, gameMode);
   }

   @Override
   public void init() {
      myPin = "";
      myTick = 0;
   }
   @Override
   public void initUI() {
      //      getGUIManager.addButton(new )
   }

   @Override
   public void draw(Graphics pen) {
      pen.setColor(Main.scheme[0]);
      int width = getWidth();
      int height = getHeight();
      pen.fillRect(0, 0, width, height);

      pen.setColor(Main.scheme[2]);
      pen.setFont(new Font("SansSerif", Font.BOLD, 50));
      StringDraw.drawStringCenter(pen, "enter pin:", width / 2, height / 3);
      pen.setFont(new Font("SansSerif", Font.BOLD, 60));
      Rectangle2D bounds = StringDraw.drawStringCenter(pen, myPin, width / 2, height / 2);

      if ( myTick % 40 > 20 ) {
         pen.setColor(Main.scheme[2]);
         pen.drawString("|", (int) (width / 2 + bounds.getWidth() / 2), height / 2 + 17);
      }

      getGUIManager().draw(pen);
   }
   @Override
   public void update() {
      myTick++;
   }

   private class GoListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         if ( GTwitter.authenticate(myPin) ) {
            Transition toMain = new FadeTransition(getGame(), getGameMode(), "load", 100, Main.scheme[0]);
            getGame().setGameState(toMain);
         } else {
            JOptionPane.showMessageDialog(null, "Authentication Failed.");
            GTwitter.resetAuth();
            Transition toLoading = new FadeTransition(getGame(), getGameMode(), "intro", 100, Main.scheme[0]);
            getGame().setGameState(toLoading);
         }
         myPin = "";
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
      if ( digits.contains("" + event.getKeyChar()) && myPin.length() < maxPinLength ) {
         myPin += event.getKeyChar();

      } else if ( ("" + event.getKeyChar()).equals("") && myPin.length() != 0 ) {
         myPin = myPin.substring(0, myPin.length() - 1);

      } else if ( event.getKeyChar() == '\n' ) {
         new GoListener().actionPerformed(null);

      } else if ( (event.getKeyChar() == '') && event.isControlDown() ) {
         Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard();

         if ( board.isDataFlavorAvailable(DataFlavor.stringFlavor) &&
               myPin.length() < maxPinLength ) {
            try {
               myPin += (String) board.getData(DataFlavor.stringFlavor);

               myPin = removeNonDigits(myPin);

               if ( myPin.length() > maxPinLength ) {
                  myPin = myPin.substring(0, maxPinLength);
               }

            } catch(UnsupportedFlavorException e) {
               e.printStackTrace();
            } catch(IOException e) {
               e.printStackTrace();
            }
         }
      }
   }

   private String removeNonDigits(String toProcess) {
      String temp = "";
      for ( int i = 0; i < toProcess.length(); i++) {
         String sChar = toProcess.substring(i, i + 1);
         temp += digits.contains(sChar) ? sChar : "";
      }
      return temp;
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