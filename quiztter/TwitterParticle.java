package quiztter;

import java.awt.Color;
import java.awt.Graphics;

import gfm.particlesys.Particle;
import gfm.util.ArrayUtils;
import gfm.util.Vec2;


public class TwitterParticle extends Particle {
   private static double myGravity = -0.2;
   private static Vec2 myShrinkRate = new Vec2(0.2, 0.2);

   private Color myColor;
   private Vec2 mySize;

   public TwitterParticle(Vec2 pos, Vec2 vel) {
      super(pos, vel);
      myColor = (Color) ArrayUtils.random(Main.scheme, 0, 6);
      mySize = new Vec2(18, 18);
   }

   @Override
   public void draw(Graphics pen) {
      int width = (int) mySize.getX();
      int height = (int) mySize.getY();
      int x = (int) getPosition().getX() - width / 2;
      int y = (int) getPosition().getY() - width / 2;
      pen.setColor(myColor);
      pen.fillRect(x, y, width, height);
   }

   @Override
   public void update() {
      mySize.subVector(myShrinkRate);
      getPosition().addVector(getVelocity());
      getVelocity().subY(myGravity);
      loseLife(1);
   }

   @Override
   public Particle newParticle() {
      return new TwitterParticle(new Vec2(0, 0), new Vec2(0, 0));
   }
}