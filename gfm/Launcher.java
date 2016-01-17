package gfm;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Launcher {
   private static final String digits = "0123456789";

   private String myAvailableVerionURL = null;
   private String myVersionURL = null;
   private String myCurrentVersion = null;
   private String myLatestVersion = null;
   private String myDownloadURL = null;

   private Game myGame;

   public Launcher(Game game) {
      myGame = game;
   }

   public void initVersioning(String versionURL, String newVersionURL, String downloadURL) throws UnknownHostException {
      myVersionURL = versionURL;
      myAvailableVerionURL = newVersionURL;
      myDownloadURL = downloadURL;
      getLatestVersion();
      getCurrentVersion();
   }

   public boolean isLatestVersion() {
      // get arrays of decimal places for versions
      if ( myLatestVersion == null || myCurrentVersion == null ) {
         return true;
      }

      String rawLatestVers = myLatestVersion;
      String rawCurrentVers = myCurrentVersion;

      // quit if latest version returns non digits
      for ( int index = 0; index < rawLatestVers.length(); index++ ) {
         boolean containsNonDigit = digits.indexOf(rawLatestVers.charAt(index)) == -1;
         if ( containsNonDigit && rawLatestVers.charAt(index) != '.' ) {
            message("Latest version number failed to be read:" + rawLatestVers);
            return true;
         }
      }

      // prep version numbers
      String[] currentVers = rawCurrentVers.split("\\.");
      String[] latestVers = rawLatestVers.split("\\.");

      // keep track of current decimal place
      int index = 0;
      int currentVersInt;
      int latestVersInt;

      // compare until we run out of decimal places
      while ( index != currentVers.length && index != latestVers.length ) {

         // inform of formatting errors
         boolean latestNumFailed = latestVers[index].equals("");
         boolean currentNumFailed = currentVers[index].equals("");
         if ( latestNumFailed && currentNumFailed ) {
            message("Version numbers failed to be read");
            break;
         } else if ( latestNumFailed ) {
            message("Latest version number failed to be read: " + rawLatestVers);
            break;
         } else if ( currentNumFailed ) {
            message("Current version number failed to be read: " + rawCurrentVers);
            break;
         }
         // get next nubmers to compare
         currentVersInt = Integer.parseInt(currentVers[index]);
         latestVersInt = Integer.parseInt(latestVers[index]);
         // compare numbers
         if ( currentVersInt > latestVersInt ) {
            message("No new updates.");
            return true;
         } else if ( currentVersInt < latestVersInt ) {
            return false;
         }
         // move to next index
         index++;
      }

      // handle if indices have been exhausted
      if ( index == currentVers.length && index == latestVers.length ) {
         message("No new updates.");
         return true;
      } else if ( index == currentVers.length ) {
         return false;
      } else if ( index == latestVers.length ) {
         message("No new updates.");
         return true;
      }

      // because we need a return statement
      return true;
   }

   public void message(String message) {
      JOptionPane.showMessageDialog(myGame.getGamePanel(), message);
   }

   public String getLatestVersion() throws UnknownHostException {
      if ( myAvailableVerionURL == null ) {
         throw new NullPointerException("URL to get next version number from is null.");
      }

      // declare URL, reader and version number
      URL latestVersionURL;
      BufferedReader fromWeb = null;
      String latestVersion = "";

      try {
         // get latest version
         latestVersionURL = new URL(myAvailableVerionURL);
         fromWeb = new BufferedReader(
               new InputStreamReader(latestVersionURL.openStream()));
         latestVersion = fromWeb.readLine();
      } catch (MalformedURLException e) {
         e.printStackTrace();
         return null;
      } catch (IOException e) {
         e.printStackTrace();
         return null;
      } finally {
         try {
            // close reader
            if ( fromWeb != null ) { fromWeb.close(); }
         } catch (Exception e) {
            // print errors
            e.printStackTrace();
         }
      }
      // return latest version number
      myLatestVersion = latestVersion;
      return myLatestVersion;
   }

   public String getCurrentVersion() {
      if ( myCurrentVersion != null ) {
         return myCurrentVersion;
      } else {
         InputStreamReader stream  = new InputStreamReader(getClass().getResourceAsStream(myVersionURL));
         BufferedReader reader = new BufferedReader(stream);

         try {
            myCurrentVersion = reader.readLine();
         } catch (IOException e) {
            e.printStackTrace();
         }

         return myCurrentVersion;
      }
   }

   public boolean downloadLatestVersion() {
      // set variables for creating file name
      String fileName = "Quiztter" + ("" + myLatestVersion).replace(".", "_") + ".jar";
      File file = new File(fileName);
      int number = 0;
      // get available file name
      while ( file.exists() && !file.isDirectory() ) {
         number++;
         fileName = "TwoP" + ("" + myLatestVersion).replace(".", "_") + "(" + number + ")" + ".jar";
         file = new File(fileName);
      }

      // declare variables for downloading file
      URL downloadSite;
      InputStream in = null;
      ByteArrayOutputStream out = null;
      FileOutputStream fileWriter = null;
      byte[] buffer = new byte[1024 * 16];
      int n = 0;

      // download file
      try {
         downloadSite = new URL(myDownloadURL);
         // establish input and ouput streams
         in = new BufferedInputStream(downloadSite.openStream());
         out = new ByteArrayOutputStream();
         // read downlaoad
         while ( (n = in.read(buffer)) != -1 ) {
            out.write(buffer, 0, n);
         }
         // move downlaod to new file
         byte[] response = out.toByteArray();
         fileWriter = new FileOutputStream(fileName);
         fileWriter.write(response);
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         // close input and output
         try {
            if ( out != null ) {
               out.close();
            }
            if ( in != null ) {
               in.close();
            }
            if ( fileWriter != null ) {
               fileWriter.close();
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      return true;
   }

   public String getDownloadURL() { return myDownloadURL; }
}