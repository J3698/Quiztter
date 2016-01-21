package quiztter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import gfm.Game;
import gfm.util.ColorCross;
import gfm.util.StringDraw;

public class QuestionRect {
   private Game myGame;
   private String myText;
   private Color myTextColor;
   private Font myFont;

   public QuestionRect(Game game, Color textColor, Font font) {
      myGame = game;
      myText = "";
      myTextColor = textColor;
      myFont = font;
   }

   public void draw(Graphics pen) {
      int width = myGame.getWidth();
      int height = myGame.getHeight();

      pen.setColor(ColorCross.alpha(Color.white, 180));
      // draw translucent box
      pen.fillRect(10, 10, width - 20, height / 2 - 50);
      // draw finish at bottom
      int intens = 100;
      for ( int i  = 10; i < width / 2; i++ ) {
         int alpha = (2 * intens * i) / width;
         pen.setColor(ColorCross.alpha(Color.white, alpha));
         pen.drawLine(i, height / 2 - 42, i, height / 2 - 52);
      }
      for ( int i  = width / 2; i < width  - 10; i++ ) {
         int alpha = intens - (((2 * intens * (i - width / 2)) / width));
         pen.setColor(ColorCross.alpha(Color.white, alpha));
         pen.drawLine(i, height / 2 - 42, i, height / 2 - 52);
      }

      for ( int i  = 10; i < width / 2; i++ ) {
         int alpha = (2 * intens * i) / width;
         pen.setColor(ColorCross.alpha(Color.white, alpha));
         pen.drawLine(i, 20, i, 10);
      }
      for ( int i  = width / 2; i < width  - 10; i++ ) {
         int alpha = intens - (((2 * intens * (i - width / 2)) / width));
         pen.setColor(ColorCross.alpha(Color.white, alpha));
         pen.drawLine(i, 20, i, 10);
      }

      pen.setColor(myTextColor);
      pen.setFont(myFont);
      Rectangle2D textBounds = StringDraw.stringBounds(pen, myText);
      if ( textBounds.getWidth() >= 0.9 * width ) {
         String[] text = splitText(myText);
         StringDraw.drawStringCenter(pen, text[0], (10 + width - 20) / 2, (10 + height / 2 - 70) / 2);
         StringDraw.drawStringCenter(pen, text[1], (10 + width - 20) / 2, (10 + height / 2 - 30) / 2);
      } else {
         StringDraw.drawStringCenter(pen, myText, (10 + width - 20) / 2, (10 + height / 2 - 50) / 2);
      }
   }

   public String[] splitText(String toSplit) {
      int half = toSplit.length() / 2;
      String secondHalf = toSplit.substring(half);
      int spaceIndex = (toSplit.length() % 2 == 0 ? half : half + 1) + secondHalf.indexOf(' ');
      return new String[] { toSplit.substring(0, spaceIndex), toSplit.substring(spaceIndex)};
   }

   public String getText() { return myText; }
   public void setText(String text) { myText = text; }

   public Color getTextColor() { return myTextColor; }
   public void setTextColor(Color color) { myTextColor = color; }

   public Font getFont() { return myFont; }
   public void setFont(Font font) { myFont = font; }
}