package gfm.example;

public class Collider {
   public static boolean collide(Rect first, Rect second) {
      if ( (first.getPos().getX() - first.getSize().getX() / 2) >
            (second.getPos().getX() + second.getSize().getX() / 2) ) {
         return false;
      } else if ( (first.getPos().getX() + first.getSize().getX() / 2) <
                   (second.getPos().getX() - second.getSize().getX() / 2) ) {
         return false;
      } else if ( (first.getPos().getY() - first.getSize().getY() / 2) >
            (second.getPos().getY() + second.getSize().getY() / 2) ) {
         return false;
      } else if ( (first.getPos().getY() + first.getSize().getY() / 2) <
                   (second.getPos().getY() - second.getSize().getY() / 2) ) {
         return false;
      }

      return true;
   }
}