package gfm.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class StringDraw {
   private static HashMap<String, Font> myFonts = new HashMap<String, Font>();

   public static boolean addFont(String name, Font toAdd) {
      if ( toAdd == null ) { throw new NullPointerException(); }
      Object wasPut = myFonts.put(name, toAdd);
      if ( wasPut == null ) { return false; }
      return true;
   }

   public static Font getFont(String name) {
      return myFonts.get(name);
   }

   public static Rectangle2D stringBounds(Graphics pen, String string) {
      Graphics2D pen2D = (Graphics2D) pen;
      FontMetrics fontMetrics = pen2D.getFontMetrics();
      Rectangle2D stringRect = fontMetrics.getStringBounds(string, pen2D);
      return stringRect;
   }

   public static Rectangle2D drawStringCenter(Graphics pen, String string, int x, int y) {
      Rectangle2D stringRect = stringBounds(pen, string);
      FontMetrics fontMetrics = ((Graphics2D)pen).getFontMetrics();
      x = (int) (x - stringRect.getWidth() / 2);
      y = (int) (y + stringRect.getHeight() / 2 - fontMetrics.getAscent() / 5);
      pen.drawString(string, x, y);
      return stringRect;
   }

   public static String[] availableFonts() {
      GraphicsEnvironment local = GraphicsEnvironment.getLocalGraphicsEnvironment();
      return local.getAvailableFontFamilyNames();
   }

}