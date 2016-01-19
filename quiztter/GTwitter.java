package quiztter;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;

import gfm.util.ErrorUtil;
import twitter4j.Paging;
import twitter4j.Place;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

// Mostly Public Static Class (Sorry)
// For interacting with Twitter

public class GTwitter {
   private static boolean myIsEnabled = true;

   public static Twitter twitter = null;

   public static AccessToken accessToken = null;
   public static RequestToken requestToken = null;
   public static String pin = null;

   public static boolean loggedIn = false;

   private static int numPopUsers = 25;
   private static ArrayList<String> popHandles;
   private static ArrayList<PopularTwitterUser> popUsers;

   static {
      readInHandles();
      readInUsers();
   }

   private void readInHandles() {
      //      in = getClass().getResourceAsStream(versioningFile);
      //      inputReader = new InputStreamReader(in);
      //      reader = new BufferedReader(inputReader);
      // prep to read in popular handles
      popHandles = new ArrayList<String>(numPopUsers);
      String popHandlesFile = "/quiztter/popHandles.txt";
      InputStreamReader in = null;
      BufferedReader file = null;

      try {
         // open file
         in = new InputStreamReader(
               ClassLoader.getSystemResourceAsStream(popHandlesFile));
         file = new BufferedReader(in);
         // read in handles
         String next;
         while ( (next = file.readLine()) != null ) {
            popHandles.add(next);
         }
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         // clean up
         try {
            if ( in != null ) {
               in.close();
            }
            if ( in != null ) {
               file.close();
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   public static void readInUsers() {
      // prep to deserialize popular users
      popUsers = new ArrayList<PopularTwitterUser>(numPopUsers);
      String popUsersFile = "/quiztter/popUsers.ser";
      FileInputStream file = null;
      ObjectInputStream in = null;

      try {
         file = new FileInputStream(popUsersFile);
         in = new ObjectInputStream(file);

         Object obj;
         while ( (obj = in.readObject()) != null ) {
            PopularTwitterUser user = (PopularTwitterUser) obj;
            if ( popHandles.contains(user.getHandle()) ) {
               popUsers.add(user);
            }
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      } finally {
         // clean up
         try {
            if ( in != null ) {
               in.close();
            }
            if ( in != null ) {
               file.close();
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }






   public static void init() {
      if ( !myIsEnabled ) { return; }

      twitter = new TwitterFactory().getInstance();
      twitter.setOAuthConsumer(consumerKey, consumerSecret);
      try {
         requestToken = twitter.getOAuthRequestToken();
      } catch(TwitterException e) {
         if ( e.getExceptionCode().equals("3cc69290-161493db 3cc69290-161493b1") ) {
            ErrorUtil.error("No Internet: Disabling Online Components");
            disable();
         } else {
            ErrorUtil.errorToFile(e);
            ErrorUtil.errorAndExit("Twitter Initialization Failed");
         }
      }
   }


   public static void resetAuth() {
      if ( !myIsEnabled ) { return; }

      twitter = new TwitterFactory().getInstance();
      twitter.setOAuthConsumer(consumerKey, consumerSecret);
      try {
         requestToken = twitter.getOAuthRequestToken();
      } catch (TwitterException e) {
         ErrorUtil.errorToFile(e);
         ErrorUtil.errorAndExit("Something Broke...");
      }
   }


   public static boolean pointUserToAuthURL() {
      if ( !myIsEnabled ) { return true; }

      String authURL = requestToken.getAuthorizationURL();

      try {
         if ( Desktop.isDesktopSupported() ) {
            Desktop.getDesktop().browse(new URI(authURL));
         } else {
            JOptionPane.showMessageDialog(null, "Please Follow The Next Link To Authorize Quiztter");
            JOptionPane.showMessageDialog(null, authURL);
         }
      } catch (IOException e) {
         ErrorUtil.errorToFile(e);
      } catch (URISyntaxException e) {
         ErrorUtil.errorToFile(e);
      }

      return false;
   }

   public static boolean authenticate(String pin) {
      if ( !myIsEnabled ) { return true; }

      if ( loggedIn )  {
         throw new IllegalStateException("Already logged in.");
      }

      try {
         if ( pin.length() == 0) {
            accessToken = twitter.getOAuthAccessToken();
         } else {
            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
         }

         if ( accessToken != null) {
            loggedIn = true;
            return true;
         } else {
            return false;
         }
      } catch (TwitterException e) {
         if ( 401 == e.getStatusCode() ) {
            return false;
         } else {
            ErrorUtil.errorToFileWithFeedback(e);
         }
      } catch (IllegalStateException e) {
         if ( e.getMessage().equals("No token available.") ) {
            return false;
         } else {
            ErrorUtil.errorToFileWithFeedback(e);
         }
      }

      return false;
   }

   private static PopularTwitterUser randomUser() {
      Random rand = new Random();
      return popUsers.get(rand.nextInt(popHandles.size()));
   }

   private ArrayList<Place> getStatuses(String handle, int toGet) throws TwitterException {
      if ( toGet < 0 ) {
         throw new IllegalArgumentException("Can't get negative amount of statuses.");
      }

      ArrayList<Status> statuses = new ArrayList<Status>();

      Paging page = new Paging(1, 50);
      int pagesToGet = toGet / 50;
      if ( toGet % 50 != 0 ) {
         pagesToGet++;
      }

      for ( int p = 1; p <= pagesToGet; p++ ) {
         page.setPage(p);
         statuses.addAll(twitter.getUserTimeline(handle, page));
      }


      return null;
   }

   public static Question randomQuestion() throws TwitterException {
      // if no internet
      if ( !myIsEnabled ) {
         try {
            Thread.sleep(100);
         } catch (Exception e) {
            e.printStackTrace();
         }
         return new Question("Dummy Q", "A", "RightAns", "Wrong1", "Wrong2", "Wrong3");
      }

      // if internet
      int pick = new Random().nextInt(2);
      Question question = null;
      if ( pick == 0 ) {
         question = commonWordOfUserQuestion();
      } else {
         question = compareFollowersOfUsersQuestion();
      }

      return question;
   }


   private static Question compareFollowersOfUsersQuestion() throws TwitterException {
      LinkedList<String> users = new LinkedList<String>();
      LinkedList<String> usersCopy = new LinkedList<String>();
      String[] mostPopUsers = new String[4];

      for ( int i = 0; i < 4; i++ ) {
         String nextUser = randomHandle();
         while ( users.contains(nextUser) ) {
            nextUser = randomHandle();
         }
         users.add(nextUser);
         usersCopy.add(nextUser);
      }

      for ( int i = 0; i < 4; i++ ) {
         String max = users.get(0);
         for ( String user : users ) {
            int king = twitter.showUser(max).getFollowersCount();
            int curr = twitter.showUser(user).getFollowersCount();
            if ( curr > king ) {
               max = user;
            }
         }

         mostPopUsers[ i ] = max;
         users.remove(max);
      }

      String questionText =
            "Does " + usersCopy.get(0) + ", " + usersCopy.get(1) +
            ", " + usersCopy.get(2) + ", or " + usersCopy.get(3) +
            " have more followers?";

      String ans0 = mostPopUsers[ 0 ];
      String ans1 = mostPopUsers[ 1 ];
      String ans2 = mostPopUsers[ 2 ];
      String ans3 = mostPopUsers[ 3 ];

      return new Question(questionText, "A", ans0, ans1, ans2, ans3);
   }


   private static Question commonWordOfUserQuestion() throws TwitterException {
      PopularTwitterUser user = randomUser();
      String name = user.getName(twitter);

      String questionText = "Which word does " + name + " use most often?";
      String[] popWords = user.getPopularWords(twitter);

      return new Question(
            questionText, "A", popWords[0], popWords[1], popWords[2], popWords[3]);
   }


   public static final String consumerKey = "jaCh0s73zKRjebHlXRq6rCK85";
   public static final String consumerSecret = "e1xbrwOQZgkjGwPu4zxHxBaGfHKWLnhaaWqon30c6C8gJPOZFH";


   public static void disable() {
      myIsEnabled = false;
   }

   public static void enable() {
      myIsEnabled = true;
   }

   public static boolean isEnabled() {
      return myIsEnabled;
   }
}