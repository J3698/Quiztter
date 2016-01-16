package quiztter;

import java.util.LinkedList;
import java.util.Random;

public class Question {
   private String myQuestion;
   private String myChoiceCorrect;
   private String myChoiceA;
   private String myChoiceB;
   private String myChoiceC;
   private String myChoiceD;

   public Question(String question, String choiceCorrect,
         String choiceA, String choiceB, String choiceC, String choiceD) {
      myQuestion = question;
      myChoiceCorrect = choiceCorrect;
      myChoiceA = choiceA;
      myChoiceB = choiceC;
      myChoiceC = choiceB;
      myChoiceD = choiceD;
   }

   public void randomize() {
      String correct = getChoice(new String(myChoiceCorrect));
      LinkedList<String> choices = new LinkedList<String>();
      Random rand = new Random();

      choices.add(myChoiceA);
      choices.add(myChoiceB);
      choices.add(myChoiceC);
      choices.add(myChoiceD);

      myChoiceA = choices.remove(rand.nextInt(4));
      myChoiceB = choices.remove(rand.nextInt(3));
      myChoiceC = choices.remove(rand.nextInt(2));
      myChoiceD = choices.remove(0);

      if ( myChoiceA.equals(correct) ) {
         myChoiceCorrect = "A";
      } else if ( myChoiceB.equals(correct) ) {
         myChoiceCorrect = "B";
      } else if ( myChoiceC.equals(correct) ) {
         myChoiceCorrect = "C";
      } else {
         myChoiceCorrect = "D";
      }
   }

   public boolean isCorrect(String choiceToTest) {
      return (choiceToTest.equals(myChoiceCorrect) ? true : false);
   }

   public String getChoice(String option) {
      if ( option.equalsIgnoreCase("A") ) {
         return myChoiceA;
      } else if ( option.equalsIgnoreCase("B") ) {
         return myChoiceB;
      } else if ( option.equalsIgnoreCase("C") ) {
         return myChoiceC;
      } else if ( option.equalsIgnoreCase("D") ) {
         return myChoiceD;
      }

      throw new IllegalArgumentException("Choice must be A, B, C, or D");
   }

   public String getQuestion() { return myQuestion; }
   public String getCorrect() { return myChoiceCorrect; }
   public String getChoiceA() { return myChoiceA; }
   public String getChoiceB() { return myChoiceB; }
   public String getChoiceC() { return myChoiceC; }
   public String getChoiceD() { return myChoiceD; }
}