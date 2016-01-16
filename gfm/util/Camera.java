package gfm.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import gfm.GamePanel;

public class Camera {
   private GamePanel myGamePanel;

   private BufferedImage myImage;
   private Graphics myPen;

   private Vec2 myDefaultPos1;
   private Vec2 myDefaultPos2;
   private Vec2 myPos1;
   private Vec2 myPos2;

   public Camera(GamePanel gamePanel) {
      myGamePanel = gamePanel;
      setImageSize(myGamePanel.getGameWidth(), myGamePanel.getGameHeight());
      myDefaultPos1 = new Vec2(0, 0);
      myDefaultPos2 = new Vec2(myGamePanel.getGameWidth(), myGamePanel.getGameHeight());
      myPos1 = myDefaultPos1.copy();
      myPos2 = myDefaultPos2.copy();
   }

   public void update() {
   }

   // put image size and perspective to default
   public void reset() {
      myPos1 = myDefaultPos1.copy();
      myPos2 = myDefaultPos2.copy();
      setImageSize(myGamePanel.getGameWidth(), myGamePanel.getGameHeight());
   }

   // erase image
   public void clearImage() {
      myPen.clearRect(0, 0, myImage.getWidth(), myImage.getHeight());
   }

   public Vec2 getScaledPos1() {
      Vec2 pos1 = myPos1.copy();
      pos1.divideX(myGamePanel.getGameWidth() / (double) myGamePanel.getWidth());
      pos1.divideY(myGamePanel.getGameHeight() / (double) myGamePanel.getHeight());
      return pos1;
   }
   public Vec2 getScaledPos2() {
      Vec2 pos2 = myPos2.copy();
      pos2.subVector(myPos1);
      pos2.divideX(myGamePanel.getGameWidth() / (double) myGamePanel.getWidth());
      pos2.divideY(myGamePanel.getGameHeight() / (double) myGamePanel.getHeight());
      return pos2;
   }

   public Vec2 getPos1() { return myPos1; }
   public Vec2 getPos2() { return myPos2; }

   public void setPos1(Vec2 pos1) { myPos1 = pos1; }
   public void setPos2(Vec2 pos2) { myPos2 = pos2; }

   public Graphics getPen() { return myPen; }
   public void setPen(Graphics pen) { myPen = pen; }

   public BufferedImage getImage() { return myImage; }

   public void setImageSize(int x, int y) {
      myImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
      myPen = myImage.getGraphics();
      Graphics2D pen2D = (Graphics2D) myPen;
      pen2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      pen2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
      pen2D.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
      pen2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      pen2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
   }
   public void setPerspective(Vec2 startPos, Vec2 endPos) {
      Vec2 diff = endPos.copy();
      diff.subVector(startPos);
      double xRatio = myGamePanel.getGameWidth() / diff.getX();
      double yRatio = myGamePanel.getGameHeight() / diff.getY();

      myPos1 = startPos.copy();
      myPos1.multiplyX(-xRatio);
      myPos1.multiplyY(-yRatio);

      myPos2 = new Vec2(myImage.getWidth(), myImage.getHeight());
      myPos2.multiplyX(xRatio);
      myPos2.multiplyY(yRatio);
      myPos2.addVector(myPos1);
   }
}