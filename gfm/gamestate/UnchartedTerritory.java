package gfm.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.Game;
import gfm.util.StringDraw;

public class UnchartedTerritory extends GameState {

   public UnchartedTerritory(Game game) {
      super(game);
   }

   @Override
   public void draw(Graphics pen) {
      pen.setFont(new Font("Ariel", 1, 30));
      pen.setColor(Color.green);
      int x = getGame().width() / 2;
      int y = getGame().height() / 2;
      StringDraw.drawStringCenter(pen, "Seems The Dev Sent You To Nowhere...", x, y);
   }

   @Override
   public void update() {
   }

   @Override
   public void initUI() {
   }

   @Override
   public void init() {
   }

   @Override
   public void keyPressed(KeyEvent event) {
   }

   @Override
   public void keyReleased(KeyEvent event) {
   }

   @Override
   public void keyTyped(KeyEvent event) {
   }

   @Override
   public void mouseClicked(MouseEvent event) {
   }

   @Override
   public void mouseDragged(MouseEvent event) {
   }

   @Override
   public void mouseEntered(MouseEvent event) {
   }

   @Override
   public void mouseExited(MouseEvent event) {
   }

   @Override
   public void mouseMoved(MouseEvent event) {
   }

   @Override
   public void mousePressed(MouseEvent event) {
   }

   @Override
   public void mouseReleased(MouseEvent event) {
   }

   @Override
   public void mouseWheelMoved(MouseWheelEvent event) {
   }
}
