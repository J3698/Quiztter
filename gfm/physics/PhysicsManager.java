package gfm.physics;

import java.util.ArrayList;

import gfm.physics.Collider.CollideDirection;

public class PhysicsManager {
   private ArrayList<PhysicsObject> myObjects;
   public PhysicsManager() {
      myObjects = new ArrayList<PhysicsObject>();
   }

   public void update() {
      for (PhysicsObject object : myObjects) {
         //keep in bounds
         object.keepInBounds();
         //compare animate objects to solid objects
         if (object.isAnimate()) {
            CollideDirection direction;
            for (PhysicsObject solid : myObjects) {
               if (solid.isSolid() && ! solid.isAnimate()) {
                  // check collision
                  direction = solid.getCollider().collides(object.getCollider());
                  if (direction != CollideDirection.None) {
                     object.boundRelativeTo(solid.getCollider(), direction);
                  } else {
                  }
               }
            }
         }
      }
   }
   public void add(PhysicsObject object) {
      myObjects.add(object);
   }
}
