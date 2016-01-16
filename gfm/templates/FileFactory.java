package gfm.templates;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class FileFactory {
   public static boolean newMain(String name) {
      return classFromTemplate(name, "MainTemplate");
   }

   public static boolean newGameState(String name) {
      return classFromTemplate(name, "GSTemplate");
   }

   public static boolean classFromTemplate(String name, String template) {
      File out = new File(name + ".java");
      InputStream in =
            FileFactory.class.getClassLoader().getResourceAsStream(
                  "gfm/templates/" + template + ".txt");

      if ( out.exists() ) {
         JOptionPane.showMessageDialog(null, "File Already Exists");
         return false;
      } else if ( in == null ) {
         JOptionPane.showMessageDialog(null, "Template Not Found");
         return false;
      }

      PrintStream toWrite = null;
      Scanner toRead = null;

      try {
         toWrite = new PrintStream(new FileOutputStream(out));
          toRead = new Scanner(in);

         while ( toRead.hasNextLine() ) {
            String[] line = toRead.nextLine().split(" ");

            for ( int index = 0; index < line.length; index++ ) {
               String word = line[index];

               if ( word.equals("CLASSNAME") ) {
                  toWrite.print(name);
               } else {
                  toWrite.print(word);
               }

               if ( index != line.length - 1  ) {
                  toWrite.print(" ");
               }
            }

            if ( toRead.hasNextLine() ) {
               toWrite.print("\n");
            }
         }
      } catch (IOException exception) {
         exception.printStackTrace();
         return false;
      } finally {
         try {
            if ( toWrite != null ) {
               toWrite.close();
            }
         } catch (Exception exception) {
            exception.printStackTrace();
         }
         try {
            if ( toRead != null ) {
               toRead.close();
            }
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      }

      return true;
   }
}
