package gfm.gamestate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.Game;
import gfm.util.ColorCross;

public class FadeTransition extends Transition {
   private Color myColor;
   private int mySteps;
   private int myCurrStep;

   public FadeTransition(Game game, String first, String second) {
      this(game, first, second, 100, Color.black);
   }
   public FadeTransition(Game game, String first, String second, int steps, Color color) {
      super(game, first, second);
      myColor = color;
      mySteps = steps;
   }

   @Override
   public void init() {
      myCurrStep = 0;
   }
   @Override
   public void initUI() {}

   @Override
   public void draw(Graphics pen) { }

   @Override
   public void drawOverMacro(Graphics pen) {
      int width = getGame().width();
      int height = getGame().height();
      int alpha;

      if ( myCurrStep <= mySteps / 2 ) {
         getFirstGameState().draw(pen);
         getFirstGameState().drawOverMacro(pen);
         alpha = (255 * myCurrStep * 2) / mySteps;
      } else {
         getSecondGameState().draw(pen);
         getSecondGameState().drawOverMacro(pen);
         alpha = 255 - (255 * (myCurrStep - mySteps / 2) / (mySteps / 2));
      }

      pen.setColor(ColorCross.alpha(myColor, alpha));
      pen.fillRect(0, 0, width, height);
   }

   @Override
   public void update() {
      if ( myCurrStep >= mySteps ) { finish(); }
      myCurrStep++;
   }

   public void setSteps(int steps) { mySteps = steps; }
   public int getSteps() { return mySteps; }
   public void setColor(Color color) { myColor = color; }
   public Color getColor() { return myColor; }

   @Override
   public void keyPressed(KeyEvent event) {
   }
   @Override
   public void keyReleased(KeyEvent event) {
   }
   @Override
   public void keyTyped(KeyEvent event) {
   }

   @Override
   public void mouseClicked(MouseEvent event) {
   }
   @Override
   public void mouseDragged(MouseEvent event) {
   }
   @Override
   public void mouseEntered(MouseEvent event) {
   }
   @Override
   public void mouseExited(MouseEvent event) {
   }
   @Override
   public void mouseMoved(MouseEvent event) {
   }
   @Override
   public void mousePressed(MouseEvent event) {
   }
   @Override
   public void mouseReleased(MouseEvent event) {
   }
   @Override
   public void mouseWheelMoved(MouseWheelEvent event) {
   }
}