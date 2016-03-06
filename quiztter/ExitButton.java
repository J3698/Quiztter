package quiztter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import gfm.gui.BasicButton;
import gfm.util.Vec2;

public class ExitButton extends BasicButton {

   public ExitButton(Vec2 position, Vec2 size) {
      super(null, null, null, null, null, position, size);
      setAction(new MyListener());
   }

   @Override
   public void draw(Graphics pen) {
      int x = (int) getPos().getX();
      int y = (int) getPos().getY();
      int width = (int) getSize().getX();
      int height = (int) getSize().getY();
      pen.setColor(Main.scheme[0].darker());
      pen.fillRect(x, y, width, height);
      pen.setColor(Color.red);
      Graphics2D pen2D = (Graphics2D) pen;
      pen2D.setStroke(new BasicStroke(2f));
      Rectangle oldClip = pen2D.getClipBounds();
      pen2D.setClip(x, y, width, height);
      pen2D.drawLine(x + 1, y + 1, x + width - 1, y + height - 1);
      pen2D.drawLine(x + width - 1, y + 1, x + 1, y + height - 1);
      pen2D.setClip(oldClip);
   }


   public class MyListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         String message = "Credits:\n" + "Creator: Antioch Sanders\n" +
               "Teacher: Mr. Rudwick\n" + "Twitter: Twitter\n" +
               "Music: Elizabeth Sherrock ->\n" +
               "        https://soundcloud.com/lizzyd710\n" +
               "Sound: www.freesfx.co.uk\n" +
               "API: twitter4j.org\n" + "Support: You :)\n" + 
               "Exit?";
         int return = JOptionPane.showConfirmDialog(null, message);
         
         if ( return == JOptionPane.YES_OPTION) {   
            System.exit(0);
         }
      }
   }
}
