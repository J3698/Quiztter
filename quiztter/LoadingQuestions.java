package quiztter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.Game;
import gfm.gamestate.FadeTransition;
import gfm.gamestate.GameState;
import gfm.gamestate.Transition;
import gfm.util.ColorCross;
import gfm.util.ErrorUtil;
import gfm.util.StringDraw;

public class LoadingQuestions extends GameState {
   private Color[] myBGColors;
   private int myCurrBGColor;
   private ColorCross myBG;
   private boolean myIsWorkStarted;
   private volatile boolean myIsDone;
   private volatile boolean myWasSuccesful;

   public LoadingQuestions(Game game) {
      super(game);
   }
   public LoadingQuestions(Game game, String stateName) {
      super(game, stateName);
   }

   @Override
   public void init() {
      myBGColors = new Color[] { Main.scheme[1], Main.scheme[3] };
      myCurrBGColor = 1;
      myBG = new ColorCross( myBGColors[0], myBGColors[1], 100);
      myIsWorkStarted = false;
      myIsDone = false;
      myWasSuccesful = true; // changes to false if error
   }

   @Override
   public void draw(Graphics pen) {
      int width = getGame().width();
      int height = getGame().height();

      // handle background color
      Color currColor = myBG.getCurrentColor();
      pen.setColor(currColor);
      myBG.next();
      if ( myBG.isFinished() ) {
         myCurrBGColor++;
         int index = myCurrBGColor % 2;
         myBG = new ColorCross(currColor, myBGColors[ index ], 100);
      }
      pen.fillRect(0, 0, width, height);

      pen.setColor(Main.scheme[5]);
      pen.setFont(new Font("Ariel", 1, 20));
      StringDraw.drawStringCenter(pen, "Loading Questions...", width / 2, height / 2 - 20);
      Play play = (Play) getGame().getGameState("play");
      int progress = play.getQuestionManager().getProgress();
      StringDraw.drawStringCenter(pen, progress + "%", width / 2, height / 2 + 10);
   }

   @Override
   public void update() {
      if ( myIsWorkStarted == false ) {
         myIsWorkStarted = true;
         doWork();
      } else if ( myWasSuccesful == false ) {
         ErrorUtil.error("Something Happened While Creating Questions...");
         Transition toIntro = new FadeTransition(getGame(), getGameMode(), "intro", 100, Main.scheme[1]);
         getGame().setGameState(toIntro);
      } else if ( myIsDone ) {
         Transition toPlay = new FadeTransition(getGame(), getGameMode(), "play", 100, Main.scheme[1]);
         getGame().setGameState(toPlay);
      }
   }

   public void doWork() {
      new Thread(){
         @Override
         public void run() {
            Play play = (Play) getGame().getGameState("play");
            try {
               play.getQuestionManager().generateGame();
               myIsDone = true;
            } catch (Exception e) {
               e.printStackTrace();
               ErrorUtil.errorToFile(e);
               myWasSuccesful = false;
            }
         }
      }.start();

   }

   @Override
   public void initUI() { }

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
