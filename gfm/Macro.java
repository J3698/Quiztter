package gfm;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface Macro {
   void draw(Graphics pen);
   void update();

   void keyPressed(KeyEvent event);
   void keyReleased(KeyEvent event);
   void keyTyped(KeyEvent event);

   void mouseClicked(MouseEvent event);
   void mouseDragged(MouseEvent event);
   void mouseEntered(MouseEvent event);
   void mouseExited(MouseEvent event);
   void mouseMoved(MouseEvent event);
   void mousePressed(MouseEvent event);
   void mouseReleased(MouseEvent event);
   void mouseWheelMoved(MouseWheelEvent event);
}