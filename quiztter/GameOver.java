package quiztter;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JOptionPane;

import gfm.Game;
import gfm.gamestate.GameState;
import gfm.util.StringDraw;

public class GameOver extends GameState {
   private boolean myDidWin;
   private boolean myAskedForReplay;
   private int myTick;

   public GameOver(Game game) {
      super(game);
   }

   public GameOver(Game game, String gameMode) {
      super(game, gameMode);
   }

   @Override
   public void draw(Graphics pen) {
      // TODO Auto-generated method stub
      pen.setColor(Main.scheme[1]);
      pen.setFont(new Font("Ariel", 1, 30));

      String message = null;
      if ( myDidWin ) {
         message = "You Won!!";
      } else {
         message = "Lol. You lost.";
      }

      int x = getGame().width();
      int y = getGame().height();
      StringDraw.drawStringCenter(pen, message, x / 2, y / 2);
   }

   @Override
   public void update() {
      myTick++;
      if ( myTick != 100 ) { return; }

      if ( !myAskedForReplay ) {
         int replay = JOptionPane.showConfirmDialog(null, "Play again?");
         if ( replay == JOptionPane.YES_OPTION ) {
            restartGame();
         } else {
            System.exit(0);
         }
      }
   }

   public void restartGame() {
      ((Play) getGame().getGameState("play")).restart();
      getGame().getGameState("load").init();
      getGame().setGameState("load");
      myTick = 0;
      myAskedForReplay = false;
   }

   @Override
   public void initUI() {
      // TODO Auto-generated method stub

   }

   @Override
   public void init() {
      myTick = 0;
      myDidWin = false;
      myAskedForReplay = false;
   }


   public boolean getDidWin() { return myDidWin; }
   public void setDidWin(boolean didWin) { myDidWin = didWin; }

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
