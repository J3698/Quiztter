package gfm.particlesys;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import gfm.particlesys.Particle;
import gfm.util.Vec2;

public class ParticleSystem {
   private ArrayList<Particle> myParticles;

   private Vec2 mySourcePosition;
   private Vec2 myMaxSourceOffset;
   private Vec2 myInitialVelocity = new Vec2(0, 0);
   private Particle myParticleType;
   private double myEmissionRate = 1;
   private double myLowerAngleLimit = -Math.PI;
   private double myUpperAngleLimit = Math.PI;
   private int myFertility;

   public ParticleSystem(Particle particleType, Vec2 position, Vec2 velocity, int fertility,
         double lowerAngleLimit, double upperAngleLimit) {
      myParticleType = particleType;
      mySourcePosition = position;
      myInitialVelocity = velocity;
      myFertility = fertility;
      myLowerAngleLimit = lowerAngleLimit;
      myUpperAngleLimit = upperAngleLimit;
      myParticles = new ArrayList<Particle>();
   }

   public void update() {
      Particle temp;
      for (int i = 0; i < myParticles.size(); i++) {
         temp = myParticles.get(i);
         if (temp.isDead()) {
            myParticles.remove(temp);
         } else {
            temp.update();
         }
      }

      if ( myFertility != 0 ) {
         for (int i = 0; i < myEmissionRate; i++) {
            createParticles();
         }

         double chance = myEmissionRate - Math.floor(myEmissionRate);
         if (new Random().nextDouble() < chance) {
            createParticles();
         }
      }

      if ( myFertility > 0 ) { myFertility--; }
   }

   public void draw(Graphics pen) {
      for (int i = 0; i < myParticles.size(); i++) {
         myParticles.get(i).draw(pen);
      }
   }

   private void createParticles() {
      Vec2 position = mySourcePosition.copy();
      if (myMaxSourceOffset != null) {
         double xLowerLim = -(myMaxSourceOffset.getX() / 2);
         double yLowerLim = -(myMaxSourceOffset.getY() / 2);
         double xUpperLim = myMaxSourceOffset.getX() / 2;
         double yUpperLim = myMaxSourceOffset.getY() / 2;
         position.addVector(Vec2.randSquareVector(xLowerLim, yLowerLim, xUpperLim, yUpperLim));
      }

      Vec2 velocity = myInitialVelocity.copy();

      double angle = new Random().nextDouble() * (myUpperAngleLimit - myLowerAngleLimit);
      angle += myLowerAngleLimit;

      velocity.rotateRadians(angle);

      Particle newParticle = myParticleType.newParticle();
      newParticle.setPosition(position);
      newParticle.setVelocity(velocity);

      myParticles.add(newParticle);
   }

   public int getFertility() { return myFertility; }
   public void setFertility(int fertility) { myFertility = fertility; }
   public Vec2 getSourcePosition() { return mySourcePosition; }
   public void setSourcePosition(Vec2 position) { mySourcePosition = position; }
   public void setSourceOffset(Vec2 offset) { myMaxSourceOffset = offset; }
   @SuppressWarnings("unused")
   private Vec2 getSourceOffset() { return myMaxSourceOffset; }

   public void setEmissionRate(double emissionRate) { myEmissionRate = emissionRate; }
   public double getEmissionRate() { return myEmissionRate; }
   public void setEmissionAngles(double lowerLimit, double upperLimit) {
      myLowerAngleLimit = lowerLimit;
      myUpperAngleLimit = upperLimit;
   }
   public void setEmissionAngles(double angle) { setEmissionAngles(angle, angle); }
   public double[] getEmissionAngles() { return new double[]{ myLowerAngleLimit, myUpperAngleLimit }; }
   public void setInitialVelocity(Vec2 velocity) { myInitialVelocity = velocity; }
   public Vec2 getInitialVelocity() { return myInitialVelocity; }
}