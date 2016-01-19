package quiztter;

import java.io.Serializable;
import java.util.ArrayList;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public class PopularTwitterUser implements Serializable {
   private static final long serialVersionUID = -928186037921334528L;

   private String myHandle;
   private String myName;
   private int myFollowersCount;
   private String[] myPopularWords;

   public PopularTwitterUser(String handle) {
      myHandle = handle;
      myName = null;
      myFollowersCount = -1;
      myPopularWords = null;
   }

   public String getHandle() {
      return myHandle;
   }
   public void setHandle(String handle) {
      myHandle = handle;
   }

   public String getName(Twitter twitter) throws TwitterException {
      if ( myName == null ) {
         myName = twitter.showUser(myHandle).getName();
      }
      return myName;
   }
   public void setName(String name) {
      myName = name;
   }

   public int getFollowersCount(Twitter twitter) throws TwitterException {
      if ( myFollowersCount == -1 ) {
         myFollowersCount = twitter.showUser(myHandle).getFollowersCount();
      }
      return myFollowersCount;
   }
   public void setMyNumFollowers(int followersCount) {
      myFollowersCount = followersCount;
   }

   public String[] getPopularWords(Twitter twitter) throws TwitterException {
      if ( myPopularWords == null ) {
         PopularWordFinder finder = new PopularWordFinder(twitter);
         ArrayList<WordAndInt> popWords = finder.mostPopularWords(myHandle);
         myPopularWords =
               new String[] { popWords.get(0).getWord(), popWords.get(1).getWord(),
                     popWords.get(2).getWord(), popWords.get(3).getWord()};
      }
      return myPopularWords;
   }

   public void setPopularWords(String[] popularWords) {
      myPopularWords = popularWords;
   }
}