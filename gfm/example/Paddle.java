package gfm.example;

import java.awt.Color;
import java.awt.Graphics;

import gfm.util.ControllableAdapter;
import gfm.util.Controls;
import gfm.util.Vec2;

public class Paddle extends ControllableAdapter implements Rect {
   private Vec2 myPos;
   private Vec2 mySize;

   @SuppressWarnings("unused")
   private int myLeftBound;
   private int myRightBound;

   private double myVelocity;
   private double myFriction;
   private double myAcceleration;
   private double myMaxVelocity;

   private String myButtonPressed;
   private Controls myControls;

   public Paddle(Vec2 pos, Vec2 size, int leftBound, int rightBound) {
      myPos = pos;
      mySize = size;

      myLeftBound = leftBound;
      myRightBound = rightBound;

      myVelocity = 0;
      myFriction = 0.9;
      myAcceleration = 1.5;
      myMaxVelocity = 12;

      myControls = new Controls(this);
      myButtonPressed = "";
   }

   public void draw(Graphics pen) {
      pen.setColor(Color.red);
      int x = (int) (myPos.getX() - mySize.getX() / 2);
      int y = (int) (myPos.getY() - mySize.getY() / 2);
      pen.fillRect(x, y, (int) mySize.getX(), (int) mySize.getY());
   }

   public void update() {
      updateVelocityAndSpeed();
   }

   public void updateVelocityAndSpeed() {
      // accelerate
      if ( myButtonPressed.equals("left") ) {
         myVelocity -= myAcceleration;
      } else if ( myButtonPressed.equals("right") ) {
         myVelocity += myAcceleration;
      }
      // enforce max velocity
      if ( Math.abs(myVelocity) > myMaxVelocity ) {
         if ( myVelocity > 0 ) {
            myVelocity = myMaxVelocity;
         } else {
            myVelocity = -myMaxVelocity;
         }
      }
      // enforce boundaries
      int width = myRightBound;
      if ( myPos.getX() + mySize.getX() / 2  > width) {
         myVelocity = 0;
         myPos.setX(width - mySize.getX() / 2);
      } else if ( mySize.getX() / 2 > myPos.getX() ) {
         myVelocity = 0;
         myPos.setX(mySize.getX() / 2);
      }

      myVelocity *= myFriction;
      myPos.addX(myVelocity);
   }

   @Override
   public void left() {
      myButtonPressed = "left";
   }
   @Override
   public void right() {
      myButtonPressed = "right";
   }

   @Override
   public void releaseLeft() {
      if ( myButtonPressed.equals("left") ) {
         myButtonPressed = "";
      }
   }
   @Override
   public void releaseRight() {
      if ( myButtonPressed.equals("right") ) {
         myButtonPressed = "";
      }
   }

   public Controls getControls() { return myControls; }
   public void setButtonPressed(String buttonPressed) { myButtonPressed = buttonPressed; }
   public String getButtonPressed() { return  myButtonPressed; }
   public void setPos(Vec2 pos) { myPos = pos; }
   @Override
   public Vec2 getPos() { return myPos; }
   public void setSize(Vec2 size) { mySize = size; }
   @Override
   public Vec2 getSize() { return mySize; }
}