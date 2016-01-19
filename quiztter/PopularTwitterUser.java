package quiztter;

import java.io.Serializable;

public class PopularTwitterUser implements Serializable {
   private static final long serialVersionUID = -928186037921334528L;

   private String myHandle;
   private String myName;
   private int myNumFollowers;
   private String[] myPopularWords;

   public PopularTwitterUser(String handle) {
      myHandle = handle;
      myName = null;
      myNumFollowers = -1;
      myPopularWords = null;
   }

   public String getHandle() { return myHandle; }
   public void setHandle(String handle) { myHandle = handle; }
   public String getName() { return myName; }
   public void setName(String name) { myName = name; }
   public int getMyNumFollowers() { return myNumFollowers; }
   public void setMyNumFollowers(int numFollowers) { myNumFollowers = numFollowers; }
   public String[] getPopularWords() { return myPopularWords; }
   public void setPopularWords(String[] popularWords) { myPopularWords = popularWords; }
}