package gfm.physics;

public interface Collider {
   public enum CollideType {
      Circle, Rect, Point
   }
   public enum CollideDirection {
      Top, Bottom, Left,
      Right, TopLeft, TopRight,
      BottomLeft, BottomRight,
      Center, None
   }
   CollideDirection collides(Collider c);
   CollideType getType();
}