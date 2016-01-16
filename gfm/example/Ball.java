package gfm.example;

import java.awt.Color;
import java.awt.Graphics;

import gfm.util.Vec2;

public class Ball implements Rect {
   private Vec2 myPos;
   private Vec2 mySize;
   private Vec2 myVelocity;

   public Ball(Vec2 pos) {
      myPos = pos;
      mySize = new Vec2(30, 40);
      myVelocity = new Vec2(1.1, -0.3);
      myVelocity.setMagnitude(9);
   }

   public void update() {
      myPos.addVector(myVelocity);
   }

   public void draw(Graphics pen) {
      pen.setColor(Color.green);
      int x = (int) (myPos.getX() - mySize.getX() / 2);
      int y = (int) (myPos.getY() - mySize.getY() / 2);
      int width = (int) mySize.getX();
      int height = (int) mySize.getY();
      pen.fillRect(x, y, width, height);
   }

   public void setPos(Vec2 pos) { myPos = pos; }
   public Vec2 getPos() { return myPos; }
   public void setSize(Vec2 size) { mySize = size; }
   public Vec2 getSize() { return mySize; }

   public Vec2 getVelocity() { return myVelocity; }
   public void setVelocity(Vec2 velocity) { myVelocity = velocity; }
}