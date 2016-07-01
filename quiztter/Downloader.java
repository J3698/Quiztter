package quiztter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Downloader {
   public static void main(String[] args) {
      String message = "Please select A Destination Folder";
      int input = JOptionPane.showConfirmDialog(null, message);
      if ( input == JOptionPane.YES_OPTION ) {
         final JFileChooser fc = new JFileChooser();
         fc.showSaveDialog(null);
      }
   }
}