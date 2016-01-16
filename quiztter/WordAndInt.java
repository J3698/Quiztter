package quiztter;

public class WordAndInt implements Comparable<WordAndInt> {
   private String myWord;
   private int myCount;

   public WordAndInt(String word, int count) {
      myWord = word;
      myCount = count;
   }

   public void add() { myCount++; }
   public int getInt() { return myCount; }
   public String getWord() { return myWord; }
   @Override
   public int compareTo(WordAndInt other) {
      return other.getInt() - myCount;
   }
   @Override
   public String toString() {
      return myWord + ":" + myCount;
   }
}
