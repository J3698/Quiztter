/*
package quiztter;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import gfm.gui.BasicButton;
import gfm.util.StringDraw;
import gfm.util.Vec2;*/
/*
public class MainMenuButton extends BasicButton {
   private ImageIcon myBird;

   public MainMenuButton(ActionListener listener, String text, Vec2 pos, Vec2 size) {
      super(listener, text, null, null, null, pos, size);
      myBird = new ImageIcon("./quiztter/twitter-bird.png");
   }

   @Override
   public void draw(Graphics pen) {
      int x = (int) (getPosition().getX());
      int y = (int) (getPosition().getY());
      int width = (int) getSize().getX();
      int height = (int) getSize().getY();

      int imgWidth =  width / 2 - 5;
      int imgHeight = height;
      pen.drawImage(myBird.getImage(), x, y, imgWidth, imgWidth, null);
      pen.setFont(new Font("SansSerif", 0, 25));
      pen.setColor(Quiztter.scheme[5]);
      int textX = x + width * 2 / 7 + ((x + width) - (x +(width * 1) / 7)) / 2;
      StringDraw.drawStringCenter(pen, getText(), textX, y + height / 2);
   }
   @Override
   public void drawHovered(Graphics pen) {
      getPosition().subVector(new Vec2(5, 5));
      getSize().addVector(new Vec2(10, 10));
      draw(pen);
      getPosition().addVector(new Vec2(5, 5));
      getSize().subVector(new Vec2(10, 10));
   }
}*/