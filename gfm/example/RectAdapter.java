package gfm.example;

import gfm.util.Vec2;

import java.awt.event.MouseEvent;

public class RectAdapter implements Rect {
   private Vec2 myPos;
   private Vec2 mySize;

   public RectAdapter(MouseEvent toConvert) {
      this(new Vec2(toConvert.getX(), toConvert.getY()));
   }
   public RectAdapter(Vec2 pos) {
      this(pos, new Vec2(0, 0));
   }
   public RectAdapter(Vec2 pos, Vec2 size) {
      myPos = pos;
      mySize = size;
   }

   public Vec2 getPos() {
      return myPos;
   }
   public Vec2 getSize() {
      return mySize;
   }
}