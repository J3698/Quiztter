package quiztter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

import gfm.Game;
import gfm.gamestate.GameState;
import gfm.sound.Sound;
import gfm.util.ColorCross;
import gfm.util.StringDraw;
import gfm.util.Vec2;

public class Play extends GameState {
   private int myQuestionsAGame;
   private int myQuestionsRight;
   private int myLives;
   private int myMaxLives;
   private QuestionManager myQuestionManager;
   private Question myCurrQuestion;
   private AnswerButton myAnsA, myAnsB, myAnsC, myAnsD;
   private AnswerButton myAnswered;
   private QuestionRect myQuestionRect;
   private boolean myHasColored;
   private boolean myColorStats;
   private Sound mySoundCorrect;
   private Sound mySoundWrong;

   public Play (Game game, int questionsAGame, int lives) {
      super(game);
      myQuestionsAGame = questionsAGame;
      myLives = lives;
      myMaxLives = lives;
      myQuestionManager.setQuestionsAGame(myQuestionsAGame + myLives - 1);
   }
   public Play (Game game, String gameMode, int questionsAGame, int lives) {
      super(game, gameMode);
      myQuestionsAGame = questionsAGame;
      myLives = lives;
      myMaxLives = lives;
      myQuestionManager.setQuestionsAGame(myQuestionsAGame + myLives - 1);
   }




   @Override
   public void init() {
      myQuestionRect = new QuestionRect(getGame(), Color.black, new Font("Ariel", 1, 26));
      myQuestionManager = new QuestionManager(0);
      myCurrQuestion = null;
      myAnswered = null;
      myHasColored = false;
      myColorStats = false;
      myQuestionsRight = 0;
      mySoundCorrect = new Sound("/quiztter/ding.wav", false);
      mySoundWrong = new Sound("/quiztter/buzzer.wav", false);
   }

   // unique to play, for new games
   public void restart() {
      myQuestionRect = new QuestionRect(getGame(), Color.black, new Font("Ariel", 1, 26));
      myQuestionManager.resetGame();
      myCurrQuestion = null;
      myAnswered = null;
      myHasColored = false;
      myColorStats = false;
      myQuestionsRight = 0;
      myLives = myMaxLives;
      getGUIManager().deleteAllButtons();
      getGUIManager().enable();
      initUI();
   }



   @Override
   public void initUI() {
      Color color = ColorCross.alpha(Color.white, 150);
      int width = getGame().width();
      int height = getGame().height();
      Vec2 size = new Vec2(width / 2 - 15, height / 4 + 5);
      myAnsA = new AnswerButton(new ListenerA(), "A", "", color, Color.black,
            new Vec2(10, height / 2 - 30), size.copy());
      myAnsB = new AnswerButton(new ListenerB(), "B", "", color, Color.black,
            new Vec2(width / 2 + 5, height / 2 - 30), size.copy());
      myAnsC = new AnswerButton(new ListenerC(), "C", "", color, Color.black,
            new Vec2(10, height * 3 / 4 - 15), size.copy());
      myAnsD = new AnswerButton(new ListenerD(), "D", "", color, Color.black,
            new Vec2(width / 2 + 5, height * 3 / 4 - 15), size.copy());
      getGUIManager().addButton(myAnsA);
      getGUIManager().addButton(myAnsB);
      getGUIManager().addButton(myAnsC);
      getGUIManager().addButton(myAnsD);
      getGUIManager().setClickSound("/quiztter/low_button");
      getGUIManager().setHoverSound("/quiztter/high_button");
   }






   @Override
   public void draw(Graphics pen) {
      int width = getGame().width();
      int height = getGame().height();
      pen.setColor(Main.scheme[0]);
      pen.fillRect(0, 0, width, height);
      myQuestionRect.draw(pen);
      getGUIManager().draw(pen);

      if ( myColorStats ) {
         if ( myCurrQuestion == null ) {
            return;
         }
         pen.setColor(Color.black);
         pen.setFont(new Font("SansSerif", 1, 40));
         String stats = myQuestionsRight + "/" + myQuestionsAGame;
         stats += " Correct, " + myLives + " Lives Left!!";
         StringDraw.drawStringCenter(pen, stats, width / 2, height / 2 - 50);
         pen.setFont(new Font("SansSerif", 1, 22));

         String qText = myCurrQuestion.getQuestion();
         Rectangle2D textBounds = StringDraw.stringBounds(pen, qText);
         if ( textBounds.getWidth() >= 0.8 * width ) {
            String[] text = splitText(qText);
            StringDraw.drawStringCenter(pen, text[0], width / 2, height / 2 - 20 / 2);
            StringDraw.drawStringCenter(pen, text[1], width / 2, height / 2 + 20 / 2);
         } else {
            StringDraw.drawStringCenter(pen, qText, width / 2, height / 2);
         }

         pen.setFont(new Font("SansSerif", 1, 17));
         String yourAns = myCurrQuestion.getChoice(myAnswered.getChoice());
         String correctAns = myCurrQuestion.getChoice(myCurrQuestion.getCorrect());
         StringDraw.drawStringCenter(pen, "Correct Answer: " + correctAns, width / 2, height / 2 + 65);
         pen.setFont(new Font("SansSerif", 1, 14));
         StringDraw.drawStringCenter(pen, "You Answered: " + yourAns, width / 2, height / 2 + 140);
      }
   }

   public String[] splitText(String toSplit) {
      int half = toSplit.length() / 2;
      String secondHalf = toSplit.substring(half);
      int spaceIndex = (toSplit.length() % 2 == 0 ? half : half + 1) + secondHalf.indexOf(' ');
      return new String[] { toSplit.substring(0, spaceIndex), toSplit.substring(spaceIndex)};
   }



   @Override
   public void drawOverMacro(Graphics pen) { }




   @Override
   public void update() {
      if ( myCurrQuestion == null ) {
         myCurrQuestion = myQuestionManager.popQuestion();
         if ( myCurrQuestion == null ) {
            return;
         }
         myQuestionRect.setText(myCurrQuestion.getQuestion());
         myAnsA.setText(myCurrQuestion.getChoiceA());
         myAnsB.setText(myCurrQuestion.getChoiceB());
         myAnsC.setText(myCurrQuestion.getChoiceC());
         myAnsD.setText(myCurrQuestion.getChoiceD());
      }

      if ( myAnswered != null && myAnswered.doneExpanding() && !myHasColored ) {
         myAnswered.setText("");
         if ( myCurrQuestion.isCorrect(myAnswered.getChoice()) ) {
            myAnswered.colorCross(Color.green, 5);
            myQuestionsRight++;
            mySoundCorrect.reset();
            mySoundCorrect.play();
         } else {
            myAnswered.colorCross(Color.red, 3);
            myLives--;
            mySoundWrong.reset();
            mySoundWrong.play();
         }
         myHasColored = true;
      } else if ( myAnswered != null && myAnswered.doneExpanding()
            && myHasColored && myAnswered.getBG() == null ) {
         myColorStats = true;
         myAnswered.setText("");
         // do other stuff
      }
   }



   public void nextQuestion() {
      myCurrQuestion = myQuestionManager.popQuestion();

      if ( myLives < 1 ) {
         getGame().setGameState("over");
         GameOver over = ((GameOver) getGame().getGameState("over"));
         getGame().setGameState(over.getGameMode());
         over.setDidWin(false);
         return;
      } else if ( myQuestionsRight == myQuestionsAGame ) {
         getGame().setGameState("over");
         GameOver over = ((GameOver) getGame().getGameState("over"));
         getGame().setGameState(over.getGameMode());
         over.setDidWin(true);
         return;
      }

      myQuestionRect.setText(myCurrQuestion.getQuestion());
      myAnsA.setText(myCurrQuestion.getChoiceA());
      myAnsB.setText(myCurrQuestion.getChoiceB());
      myAnsC.setText(myCurrQuestion.getChoiceC());
      myAnsD.setText(myCurrQuestion.getChoiceD());
      getGUIManager().enable();
      myAnswered.reset();
      myAnswered = null;
      myHasColored = false;
      myColorStats = false;
   }




   public QuestionManager getQuestionManager() { return myQuestionManager; }

   private class ListenerA implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         myAnswered = myAnsA;

         int midX = getGame().width() / 2;
         int midY = getGame().height() / 2;
         Vec2 middle = new Vec2(midX, midY);
         myAnsA.startExpand(20, middle, new Color(255, 255, 255, 250));

         getGUIManager().disable();
         getGUIManager().removeButton(myAnsA);
         getGUIManager().addButton(myAnsA);
      }
   }
   private class ListenerB implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         myAnswered = myAnsB;

         int midX = getGame().width() / 2;
         int midY = getGame().height() / 2;
         Vec2 middle = new Vec2(midX, midY);
         myAnsB.startExpand(20, middle, new Color(255, 255, 255, 250));

         getGUIManager().disable();
         getGUIManager().removeButton(myAnsB);
         getGUIManager().addButton(myAnsB);
      }
   }
   private class ListenerC implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         myAnswered = myAnsC;

         int midX = getGame().width() / 2;
         int midY = getGame().height() / 2;
         Vec2 middle = new Vec2(midX, midY);
         myAnsC.startExpand(20, middle, new Color(255, 255, 255, 250));

         getGUIManager().disable();
         getGUIManager().removeButton(myAnsC);
         getGUIManager().addButton(myAnsC);
      }
   }
   private class ListenerD implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         myAnswered = myAnsD;

         int midX = getGame().width() / 2;
         int midY = getGame().height() / 2;
         Vec2 middle = new Vec2(midX, midY);
         myAnsD.startExpand(20, middle, new Color(255, 255, 255, 250));

         getGUIManager().disable();
         getGUIManager().removeButton(myAnsD);
         getGUIManager().addButton(myAnsD);
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

      if ( myColorStats ) {
         nextQuestion();
      }
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