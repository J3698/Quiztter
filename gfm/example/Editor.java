package gfm.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import gfm.Game;
import gfm.gamestate.GameState;
import gfm.gui.MenuButton;
import gfm.util.Vec2;

public class Editor extends GameState {
   private static Color[] colorScheme = new Color[]
         { Color.black, Color.gray, Color.blue, Color.green,
               Color.orange, Color.red                          };
   private static int minX = 10;
   private static int minY = 30;
   private static int maxX = 630;
   private static int maxY = 290;
   private static int padX = 3;
   private static int padY = 2;

   private int incX;
   private int incY;
   private int correctX;

   private Block[][] myBlocks;

   public Editor(Game game) {
      super(game);
      init();
   }
   public Editor(Game game, String stateName) {
      super(game, stateName);
      init();
   }

   @Override
   public void init() {
      myBlocks = new Block[ 20 ][ 20 ];

      incX = (maxX - minX) / myBlocks[0].length;
      incY = (maxY - minY) / myBlocks.length;
      correctX = (minX + maxX - ( (myBlocks[0].length - 1) * incX + padX - 2 * padX)) / 2;
      Vec2 size = new Vec2(incX - 2 * padX, incY - 2 * padY);

      for ( int row = 0; row < myBlocks.length; row++ ) {
         for ( int col = 0; col < myBlocks[0].length; col++ ) {

            Vec2 pos = new Vec2(col * incX + padX + correctX, row * incY + padY);
            myBlocks[ row ][ col ] = new Block(pos, size.copy(), 0);
         }
      }
   }

   @Override
   public void update() {

   }

   @Override
   public void draw(Graphics pen) {
      pen.setColor(Color.white);

      for ( int row = 0; row < myBlocks.length; row++ ) {
         for ( int col = 0; col < myBlocks[0].length; col++ ) {
            Block block = myBlocks[ row ][ col ];
            block.draw(pen, colorScheme[block.getLives()]);
         }
      }

      getGUIManager().draw(pen);
   }

   @Override
   public void initUI() {
      getGUIManager().addButton(
            new MenuButton(new ExitListener(), "EXIT", new Vec2(640 / 3, 420), new Vec2(70, 30), 640, 480));
      getGUIManager().addButton(
            new MenuButton(new SaveListener(), "SAVE", new Vec2(640 * 2 / 3, 420), new Vec2(70, 30), 640, 480));
   }

   @Override
   public void mouseMoved(MouseEvent event) {
      getGUIManager().mouseMoved(event);
   }

   @Override
   public void mousePressed(MouseEvent event) {
      getGUIManager().mousePressed(event);
      for ( int row = 0; row < myBlocks.length; row++ ) {
         for ( int col = 0; col < myBlocks[0].length; col++ ) {
            Rect mouse = new RectAdapter(event);
            Block block = myBlocks[ row ][ col ];
            if ( Collider.collide(block, mouse) ) {
               if ( block.getLives() < 5 ) {
                  block.setLives(block.getLives() + 1);
               } else {
                  block.setLives(0);
               }
            }
         }
      }
   }

   private class ExitListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         System.exit(0);
      }
   }

   private class SaveListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         getGUIManager().disable();
         String filename = JOptionPane.showInputDialog("To Save As: ");
         PrintStream printer = null;
         try {
            printer = new PrintStream(new FileOutputStream(new File("./gfm/example/Levels/"+filename)));

            for ( int row = 0; row < myBlocks.length; row++ ) {
               for ( int col = 0; col < myBlocks[0].length; col++ ) {
                  Block block = myBlocks[ row ][ col ];
                  if ( !block.isDead() ) {
                     printer.print(block.getPos().getX() + " " + block.getPos().getY() + " ");
                     printer.print(block.getSize().getX() + " " + block.getSize().getY() + " ");
                     printer.print(block.getLives());
                     printer.println();
                  }
               }
            }
         } catch(Exception e) {
            e.printStackTrace();
         } finally {
            if ( printer != null ) {
               try {
                  printer.close();
               } catch(Exception e) {
                  e.printStackTrace();
               }
            }
         }
         getGUIManager().enable();
      }
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
   public void mouseReleased(MouseEvent event) {
   }
   @Override
   public void mouseWheelMoved(MouseWheelEvent event) {
   }
}