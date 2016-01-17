package quiztter;

import java.util.LinkedList;

import gfm.util.ErrorUtil;
import twitter4j.TwitterException;

public class QuestionManager {
   private int myQuestionsAGame;
   private LinkedList<Question> myQuestions;
   private volatile int myProgress;

   public QuestionManager(int questionsAGame) {
      myQuestionsAGame = questionsAGame;
      myQuestions = new LinkedList<Question>();
      myProgress = 0;
   }

   public Question popQuestion() {
      return myQuestions.size() == 0 ? null : myQuestions.remove();
   }

   public void generateGame() {
      generateQuestions(myQuestionsAGame);
   }

   public void generateQuestions(int toGenerate) {
      for ( int i = 0 ; i < toGenerate; i++ ) {
         Question newQuestion = null;
         try {
            newQuestion = generateQuestion();
         } catch (NullPointerException e) {
            i--;
            continue;
         } catch (IndexOutOfBoundsException e) {
            i--;
            continue;
         }

         myQuestions.add(newQuestion);
         myProgress = (int) (((double) (100 * (i + 1))) / toGenerate);
      }
   }

   public Question generateQuestion() {
      Question newQuestion = null;
      while ( newQuestion == null ) {
         try {
            newQuestion = GTwitter.randomQuestion();
         } catch (TwitterException e) {
            e.printStackTrace();
            if ( e.exceededRateLimitation() ) {
               ErrorUtil.errorAndExit("Twitter API Limit reached - try again later.");
            }
         }
      }

      newQuestion.randomize();
      return newQuestion;
   }

   public void resetGame() {
      myQuestions = new LinkedList<Question>();
      myProgress = 0;
   }


   public int getProgress() { return myProgress; }
   public void setQuestionsAGame(int questionsAGame) { myQuestionsAGame = questionsAGame; }
   public int getQuestionsAGame() { return myQuestionsAGame; }
   public LinkedList<Question> getQuestions() { return myQuestions; }
}