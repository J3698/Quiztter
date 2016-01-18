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
      generateQuestions(myQuestionsAGame - myQuestions.size());
   }

   public void generateQuestions(int toGenerate) {
      for ( int i = 0 ; i < toGenerate; i++ ) {
         Question newQuestion = generateQuestion();
         myQuestions.add(newQuestion);
      }
   }

   public Question generateQuestion() {
      Question newQuestion = null;
      while ( newQuestion == null ) {
         try {
            newQuestion = GTwitter.randomQuestion();
            newQuestion.randomize();
         } catch (TwitterException e) {
            e.printStackTrace();
            if ( e.exceededRateLimitation() ) {
               ErrorUtil.errorAndExit("Twitter API Limit reached - try again later.");
            }
         } catch (NullPointerException e) {
            newQuestion = null;
         } catch (IndexOutOfBoundsException e) {
            newQuestion = null;
         }
      }

      return newQuestion;
   }

   public int getProgress() {
      double scaledProgress = 100 * myQuestions.size();
      myProgress = (int) (scaledProgress / myQuestionsAGame);
      return myProgress;
   }

   public void setQuestionsAGame(int questionsAGame) { myQuestionsAGame = questionsAGame; }
   public int getQuestionsAGame() { return myQuestionsAGame; }
   public LinkedList<Question> getQuestions() { return myQuestions; }
}