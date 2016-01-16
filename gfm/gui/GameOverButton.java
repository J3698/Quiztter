package gfm.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import gfm.util.StringDraw;
import gfm.util.Vec2;

public class GameOverButton extends BasicButton {
   public GameOverButton(ActionListener listener, String text, Vec2 position) {
      super(listener, text, new Color(0, 0, 0, 0), new Color(150, 150, 150), new Font("Ariel", 1, 25),
            position, new Vec2(110, 80));
   }

   @Override
   public void draw(Graphics pen) {
      pen.setFont(new Font("sans", 0, 15));
      pen.setColor(getTextColor());
      int centerX = (int) (getPosition().getX() + getSize().getX() / 2);
      int centerY = (int) (getPosition().getY() + getSize().getY() / 2);
      StringDraw.drawStringCenter(pen, getText(), centerX, centerY);
   }

   @Override
   public void drawHovered(Graphics pen) {
      pen.setFont(new Font("sans", 0, 15));
      pen.setColor(new Color(180, 180, 180));
      int centerX = (int) (getPosition().getX() + getSize().getX() / 2);
      int centerY = (int) (getPosition().getY() + getSize().getY() / 2);
      StringDraw.drawStringCenter(pen, getText(), centerX, centerY);
   }
}
