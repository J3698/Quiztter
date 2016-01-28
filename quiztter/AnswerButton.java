package quiztter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import gfm.gui.BasicButton;
import gfm.util.ColorCross;
import gfm.util.StringDraw;
import gfm.util.Vec2;

public class AnswerButton extends BasicButton {
   private String myChoice;
   private int myExpandSteps;
   private int myCurrExpandStep;
   private Vec2 myExpandMotion;
   private Vec2 myExpandGrowth;
   private ColorCross myBG;
   private Vec2 myOrigSize;
   private Vec2 myOrigPos;
   private Color myOrigColor;


   public AnswerButton(ActionListener listener, String choice, String text, Color bodyColor,
         Color textColor, Vec2 position, Vec2 size) {
      super(listener, text, bodyColor, textColor, new Font("Ariel", 1, 30), position, size);
      myChoice = choice;
      myExpandSteps = 0;
      myCurrExpandStep = 0;
      myBG = null;
      myOrigSize = getSize().copy();
      myOrigPos = getPosition().copy();
      myOrigColor = new Color(getBodyColor().getRed(), getBodyColor().getGreen(),
            getBodyColor().getBlue(), getBodyColor().getAlpha());
   }

   @Override
   public void draw(Graphics pen) {
      if ( !doneExpanding() ) {
         expand();
      }

      if ( myBG != null ) {
         setBodyColor(myBG.getCurrentColor());
         myBG.next();
         if ( myBG.isFinished() ) {
            myBG = null;
         }
      }
      pen.setColor(getBodyColor());

      int x = (int) getPosition().getX();
      int y = (int) getPosition().getY();
      int width = (int) getSize().getX();
      int height = (int) getSize().getY();
      pen.fillRect(x, y, width, height);
      pen.setFont(new Font("Ariel", 1, 10));
      pen.drawString(myChoice, x, y);
      pen.setColor(getTextColor());
      pen.setFont(getFont());
      StringDraw.drawStringCenter(pen, getText(), x + width / 2, y + height / 2);
   }

   @Override
   public void drawHovered(Graphics pen) {
      Vec2 offset = getSize().copy();
      offset.multiply(0.05);
      getPosition().subVector(offset);
      Vec2 zoom = getSize().copy();
      zoom.multiply(0.1);
      getSize().addVector(zoom);
      draw(pen);
      getPosition().addVector(offset);
      getSize().subVector(zoom);
   }

   public void startExpand(int expandSteps, Vec2 targetSize, Color targetColor) {
      myExpandSteps = expandSteps;
      myCurrExpandStep = 0;

      int width = getGUIManager().getGame().getWidth();
      int height = getGUIManager().getGame().getHeight();

      // according to hoverzoom - need to disable gui afterstartexpand
      getSize().multiply(1.1);

      myExpandGrowth = new Vec2((int)(0.95 * width), (int)(0.95 * height));
      myExpandGrowth.subVector(getSize());
      myExpandGrowth.divide(myExpandSteps);

      myExpandMotion = new Vec2(width / 2.0, height / 2.0);
      Vec2 toSub = getSize().copy();
      toSub.divide(2.0);
      toSub.addVector(getPosition());
      myExpandMotion.subVector(toSub);
      myExpandMotion.divide(myExpandSteps);

      colorCross(targetColor, expandSteps);
   }

   public void expand() {
      if ( doneExpanding() ) {
         return;
      }
      myCurrExpandStep++;

      getPosition().addVector(myExpandMotion);
      getSize().addVector(myExpandGrowth);
      Vec2 correct = myExpandGrowth.copy();
      correct.divide(2.0);
      getPosition().subVector(correct);
   }

   public boolean doneExpanding() {
      return (myExpandSteps <= myCurrExpandStep);
   }

   public void colorCross(Color target, int steps) {
      myBG = new ColorCross(getBodyColor(), target, steps);
   }

   public String getChoice() { return myChoice; }

   public ColorCross getBG() { return myBG; }

   public void reset() {
      setPosition(myOrigPos.copy());
      setSize(myOrigSize.copy());
      setBodyColor(myOrigColor);
   }
}
