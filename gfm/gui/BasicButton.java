package gfm.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import gfm.Game;
import gfm.util.StringDraw;
import gfm.util.Vec2;

public class BasicButton implements Button {
   private ActionListener myListener;
   private boolean myMouseHovering;
   private Vec2 myPosition;
   private Vec2 mySize;
   private String myText;
   private Color myBodyColor;
   private Color myTextColor;
   private Font myFont;
   private Game myGame;

   public BasicButton(ActionListener listener, String text, Color bodyColor, Color textColor, Font font,
         Vec2 position, Vec2 size) {
      myListener = listener;
      myText = text;
      myFont = font;
      myBodyColor = bodyColor;
      myTextColor = textColor;
      myPosition = position;
      mySize = size;
      myMouseHovering = false;
   }

   @Override
   public void draw(Graphics pen) {
      pen.setColor(myBodyColor);
      int x = (int) myPosition.getX();
      int y = (int) myPosition.getY();
      int width = (int) mySize.getX();
      int height = (int) mySize.getY();
      pen.fillRect(x, y, width, height);
      pen.setColor(myTextColor);
      pen.setFont(myFont);
      StringDraw.drawStringCenter(pen, myText, x + width / 2, y + height / 2);
   }

   @Override
   public void drawHovered(Graphics pen) {
      //shrink
      myPosition.addVector(new Vec2(1, 1));
      mySize.subVector(new Vec2(2, 2));
      //draw normal
      draw(pen);
      //draw veil
      pen.setColor(new Color(0, 0, 0, 100));
      int x = (int) myPosition.getX();
      int y = (int) myPosition.getY();
      int width = (int) mySize.getX();
      int height = (int) mySize.getY();
      pen.fillRect(x, y, width, height);
      //undo shrink
      myPosition.subVector(new Vec2(1, 1));
      mySize.addVector(new Vec2(2, 2));
   }

   @Override
   public void doAction() {
      myListener.actionPerformed(null);
   }

   @Override
   public boolean collidesPoint(double x, double y) {
      boolean inXBounds = (x >= myPosition.getX() && x <= myPosition.getX() + mySize.getX());
      boolean inYBounds = (y >= myPosition.getY() && y <= myPosition.getY() + mySize.getY());
      return (inXBounds && inYBounds);
   }

   public ActionListener getAction() { return myListener; }
   public void setAction(ActionListener listener) { myListener = listener; }

   @Override
   public Vec2 getPosition() { return myPosition; }
   @Override
   public void setPosition(Vec2 position) { myPosition = position; }

   @Override
   public Vec2 getSize() { return mySize; }
   @Override
   public void setSize(Vec2 size) { mySize = size; }

   public String getText() { return myText; }
   public void setText(String text) { myText = text; }

   public Color getBodyColor() { return myBodyColor; }
   public void setBodyColor(Color bodyColor) { myBodyColor = bodyColor; }

   public Color getTextColor() { return myTextColor; }
   public void setTextColor(Color textColor) { myTextColor = textColor; }

   @Override
   public boolean getMouseHovering() { return myMouseHovering; }
   @Override
   public void setMouseHovering(boolean isHovering) { myMouseHovering = isHovering; }

   public Font getFont() { return myFont; }
   public void setFont(Font font) { myFont = font; }


   @Override
   public Game getGame() { return myGame; }
   @Override
   public void setGame(Game game) { myGame = game; }
}