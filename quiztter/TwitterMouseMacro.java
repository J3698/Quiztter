package quiztter;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.Macro;
import gfm.particlesys.ParticleSystem;
import gfm.util.Vec2;

public class TwitterMouseMacro implements Macro {
   private ParticleSystem myMouseParticles;

   public TwitterMouseMacro() {
      Vec2 mouseParticlePos = new Vec2(300, 300);
      Vec2 mouseParticleVel = new Vec2(3, 0);
      myMouseParticles = new ParticleSystem(new TwitterParticle(null, null), mouseParticlePos, mouseParticleVel, 0, -180, 180);
      myMouseParticles.setSourceOffset(new Vec2(30, 30));
      myMouseParticles.setEmissionRate(10);
   }

   @Override
   public void draw(Graphics pen) {
      myMouseParticles.update();
      myMouseParticles.draw(pen);
   }

   @Override
   public void update() {
      // TODO Auto-generated method stub

   }

   @Override
   public void keyPressed(KeyEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void keyReleased(KeyEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void keyTyped(KeyEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseClicked(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseDragged(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseEntered(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseExited(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseMoved(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mousePressed(MouseEvent event) {
      int x = event.getX();
      int y = event.getY();
      myMouseParticles.setSourcePosition(new Vec2(x, y));
      myMouseParticles.setFertility(1);
   }

   @Override
   public void mouseReleased(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseWheelMoved(MouseWheelEvent event) {
      // TODO Auto-generated method stub

   }

}
