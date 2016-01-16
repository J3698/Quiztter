package gfm.particlesys;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import gfm.util.Vec2;

public abstract class Particle {
   private Vec2 myParticlePosition;
   private Vec2 myParticleVelocity;
   private int myLife = 255;
   private Color myColor;

   public Particle(Vec2 position, Vec2 velocity) {
      myParticlePosition = position;
      myParticleVelocity = velocity;
   }

   public void run(Graphics pen) {
      update();
      draw(pen);
   }

   public abstract void update();
   public abstract void draw(Graphics pen);
   public abstract Particle newParticle();

   
   public boolean isDead() {
      if (myLife > 0) {
         return false;
      }
      return true;
   }

   public Color getRandomColor() {
      Random random = new Random();
      int red = random.nextInt(256);
      int green = random.nextInt(256);
      int blue = random.nextInt(256);
      return new Color(red, green, blue);
   }
   public Color getRandomColor(int minRed, int maxRed, int minGreen, int maxGreen,
         int minBlue, int maxBlue) {
      Random random = new Random();
      int red = minRed + random.nextInt(maxRed - minRed);
      int green = minGreen + random.nextInt(maxGreen - minGreen);
      int blue = minBlue + random.nextInt(maxBlue - minBlue);
      return new Color(red, green, blue);
   }

   public void loseLife(int damage) { myLife -= damage; }
   public int getLife(){ return myLife; }
   public void setLife(int life){ myLife = life; }
   public Vec2 getPosition() { return myParticlePosition; }
   public void setPosition(Vec2 position) {myParticlePosition = position; }
   public Vec2 getVelocity() { return myParticleVelocity; }
   public void setVelocity(Vec2 velocity) { myParticleVelocity = velocity; }
   public void setColor(Color color) { myColor = color; }
   public Color getColor() { return myColor; }
}