package quiztter;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import gfm.gui.BasicButton;
import gfm.util.ColorCross;
import gfm.util.StringDraw;
import gfm.util.Vec2;

public class TwitterAuthButton extends BasicButton {
   private ImageIcon myBird;
   private float myOpacity;

   public TwitterAuthButton(ActionListener listener, Vec2 pos, Vec2 size) {
      super(listener, "login", null, null, null, pos, size);
      myBird = new ImageIcon(getClass().getResource("twitter-bird.png"));
      myOpacity = 0f;
   }

   @Override
   public void draw(Graphics pen) {
      int x = (int) (getPosition().getX());
      int y = (int) (getPosition().getY());
      int width = (int) getSize().getX();
      int height = (int) getSize().getY();

      Graphics2D pen2D = (Graphics2D) pen;
      Composite original = pen2D.getComposite();
      AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, myOpacity);
      pen2D.setComposite(ac);
      pen.drawImage(myBird.getImage(), x, y, width, width, null);
      pen2D.setComposite(original);

      if ( myOpacity < .9f ) {
         myOpacity += 0.01f;
      } else {
         myOpacity = 1f;
      }

      pen.setFont(new Font("SansSerif", 2, 15));
      pen.setColor(ColorCross.alpha(Main.scheme[5], (int) (255 * myOpacity)));
      StringDraw.drawStringCenter(pen, "login", x + width / 2, y + height / 2);
   }

   @Override
   public void drawHovered(Graphics pen) {
      getPosition().subVector(new Vec2(5, 5));
      getSize().addVector(new Vec2(10, 10));
      draw(pen);
      getPosition().addVector(new Vec2(5, 5));
      getSize().subVector(new Vec2(10, 10));
   }
}