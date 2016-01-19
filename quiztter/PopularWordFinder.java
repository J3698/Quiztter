package quiztter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class PopularWordFinder {
   private Twitter myTwitter;

   public PopularWordFinder(Twitter twitter) {
      myTwitter = twitter;
   }

   private ArrayList<String> makeSortedListOfWordsFromTweets(String twitterHandle) throws TwitterException {
      ArrayList<Status> statuses = new ArrayList<Status>();
      ArrayList<String> sortedTerms = new ArrayList<String>();

      Paging page = new Paging(1, 200);
      int p = 1;
      while ( p <= 4 ) {
         page.setPage(p);
         statuses.addAll(myTwitter.getUserTimeline(twitterHandle, page));
         p++;
      }

      //Makes a list of words from user timeline
      for (Status status : statuses) {
         String[]array = status.getText().split(" ");
         for (String word : array) {
            sortedTerms.add(removePunctuation(word).toLowerCase());
         }
      }

      // Remove common English words, which are stored in commonWords.txt
      sortedTerms = removeCommonEnglishWords(sortedTerms, twitterHandle);
      sortAndRemoveEmpties(sortedTerms);
      return sortedTerms;
   }

   private String removePunctuation(String s) {
      // things to delete
      if ( s.length() != 0 && s.substring(0, 1).equals("@") ) { return ""; }
      if ( s.length()!= 0 && s.substring(0, 1).equals("#") ) { return ""; }
      if ( s.contains("www.") ) { return ""; }
      if ( s.contains("http") ) { return ""; }
      if ( s.length() <= 2) { return ""; }

      String toReturn = "";
      String punct = "`~!@#$%^&*()_+-={}|[]\\:\";'<>?,./";

      for ( int i = 0; i < s.length(); i++ ) {
         if ( !punct.contains(s.substring(i, i + 1)) ) {
            toReturn += s.substring(i, i + 1);
         }
      }
      return toReturn;
   }

   private ArrayList<String> removeCommonEnglishWords (ArrayList<String> list, String twitterHandle) {
      BufferedReader inFile = null;

      try {
         InputStream in = getClass().getResourceAsStream("/quiztter/commonWords.txt");
         inFile = new BufferedReader(new InputStreamReader(in));

         String toRemove;
         while ( (toRemove = inFile.readLine()) != null ) {
            while ( list.contains(toRemove.toLowerCase()) ) {
               list.remove(toRemove.toLowerCase());
            }
         }

         while ( list.contains(twitterHandle.toLowerCase()) ) {
            list.remove(twitterHandle.toLowerCase());
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if ( inFile != null ) {
            try {
               inFile.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }

      return list;
   }

   private void sortAndRemoveEmpties(ArrayList<String> sortedTerms) {
      while ( sortedTerms.contains("") ) {
         sortedTerms.remove("");
      }
      Collections.sort(sortedTerms);
   }

   public ArrayList<WordAndInt> mostPopularWords(String twitterHandle) throws TwitterException {
      ArrayList<String> sortedTerms = makeSortedListOfWordsFromTweets(twitterHandle);
      ArrayList<WordAndInt> words = new ArrayList<WordAndInt>();

      String word = null;
      for ( int i = 0; i < sortedTerms.size(); i++ ) {
         word = sortedTerms.get(i);
         boolean added = false;

         WordAndInt pair = null;
         for ( int j = 0; j < words.size(); j++ ) {
            pair = words.get(j);
            if ( word.equals(pair.getWord()) ) {
               pair.add();
               added = true;
               break;
            }
         }
         if ( !added ) {
            words.add(new WordAndInt(word, 1));
         }
      }
      Collections.sort(words);
      return words;
   }
}
