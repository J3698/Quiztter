package gfm.gui;

import java.awt.Graphics;

import gfm.Game;
import gfm.util.Vec2;

public interface Button {
   void draw(Graphics pen);
   void drawHovered(Graphics pen);

   void doAction();
   boolean collidesPoint(double x, double y);

   void setPosition(Vec2 position);
   Vec2 getPosition();
   void setSize(Vec2 size);
   Vec2 getSize();

   boolean getMouseHovering();
   void setMouseHovering(boolean isHovering);

   void setGame(Game myGame);
   Game getGame();
}