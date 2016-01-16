package gfm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JOptionPane;

public class ErrorUtil {
   public static void errorAndExit(String message) {
      error(message);
      System.exit(0);
   }
   public static void error(String message) {
      JOptionPane.showMessageDialog(null, message);
   }

   public static void errorToFile(Exception e) {
      errorToFileWithMessage(e, "");
   }

   public static void errorToFileWithFeedback(Exception e) {
      String message = JOptionPane.showInputDialog("Please Describe The Bug: ");
      if ( message != null ) {
         errorToFileWithMessage(e, message);
      } else {
         errorToFile(e);
      }
   }

   public static void errorToFileWithMessage(Exception e, String message) {
      // get available error-file name
      int errorNumb = 0;
      File file = new File("error_" + errorNumb + ".txt");
      while ( file.exists() ) {
         errorNumb++;
         file = new File("error_" + errorNumb + ".txt");
      }

      PrintStream newPrint = null;
      try {
         // set System err to file
         FileOutputStream out = new FileOutputStream(file);
         newPrint = new PrintStream(out);
         // print to file
         newPrint.println(message + "\n");
         e.printStackTrace(newPrint);
      } catch(IOException ioe) {
         ioe.printStackTrace();
      } finally {
         if ( newPrint != null) {
            newPrint.close();
         }
      }
   }
}