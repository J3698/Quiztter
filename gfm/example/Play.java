package gfm.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

import gfm.Game;
import gfm.gamestate.GameState;
import gfm.util.Vec2;

public class Play extends GameState {
   private static Color[] colorScheme = new Color[]
         { Color.black, Color.gray, Color.blue, Color.green,
               Color.orange, Color.red                          };

   private Paddle myPaddle;
   private Ball myBall;
   private LinkedList<Block> myBlocks;

   public Play(Game game) {
      super(game);
      myPaddle = new Paddle(new Vec2(320, 465), new Vec2(90, 10), 0,
            getGame().width());
      myBall = new Ball(new Vec2(30 + new Random().nextInt(610), 440));
      myBlocks = new LinkedList<Block>();
      String filename = JOptionPane.showInputDialog("ToLoad: ");
      if ( filename != null ) {
         configBlocksFromFile("./gfm/example/Levels/"+filename);
      }
   }

   public Play(Game game, String gameMode) {
      super(game, gameMode);
      myPaddle = new Paddle(
            new Vec2(320, 465), new Vec2(90, 10), 0, getGame().width());
      myBlocks = new LinkedList<Block>();
   }

   @Override
   public void draw(Graphics pen) {
      myPaddle.draw(pen);
      for ( Block block : myBlocks) {
         block.draw(pen, colorScheme[block.getLives()]);
      }
      myBall.draw(pen);
      getGUIManager().draw(pen);
   }
   @Override
   public void update() {
      myPaddle.update();
      myBall.update();

      if ( myBall.getPos().getX() - myBall.getSize().getX() / 2 < 0 &&
            myBall.getVelocity().getX() < 0) {
         myBall.getVelocity().multiplyX(-1);
      } else if ( myBall.getPos().getX() + myBall.getSize().getX() / 2 > getGame().width() &&
            myBall.getVelocity().getX() > 0) {
         myBall.getVelocity().multiplyX(-1);
      }

      if ( myBall.getPos().getY() - myBall.getSize().getY() / 2 < 0 &&
            myBall.getVelocity().getY() < 0) {
         myBall.getVelocity().multiplyY(-1);
      } else if ( myBall.getPos().getY() + myBall.getSize().getY() / 2 > getGame().height() &&
            myBall.getVelocity().getY() > 0) {
         System.exit(0);
      }


      Block toRemove = null;
      for ( Block block : myBlocks ) {
         if ( Collider.collide(block, myBall) ) {
            if ( new Random().nextBoolean() ) {
               myBall.getVelocity().multiplyX(-1);
            } else {
               myBall.getVelocity().multiplyY(-1);
            }

            block.loseLife(1);
            if ( block.isDead() ) {
               toRemove = block;
            }
            continue;
         }
      }

      if ( toRemove != null ) {
         myBlocks.remove(toRemove);
      }

      if ( Collider.collide(myBall, myPaddle) ) {
         myBall.getVelocity().multiplyY(-1);
      }
   }
   @Override
   public void initUI() {
   }

   public void configBlocksFromFile(String filename) {
      // save copy just in case load fails
      myBlocks = new LinkedList<Block>();

      File toRead = new File(filename);
      Scanner inFile = null;
      try {
         inFile = new Scanner(toRead);

         while ( inFile.hasNextDouble() ) {
            myBlocks.add(new Block(
                  new Vec2(inFile.nextDouble(), inFile.nextDouble()),
                  new Vec2(inFile.nextDouble(), inFile.nextDouble()),
                  inFile.nextInt()));
            if ( myBlocks.getLast().isDead() ) {
               myBlocks.removeLast();
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if ( inFile != null ) {
            try {
               inFile.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
   }

   @Override
   public void keyPressed(KeyEvent event) {
      myPaddle.getControls().keyPressed(event);
   }
   @Override
   public void keyReleased(KeyEvent event) {
      myPaddle.getControls().keyReleased(event);
   }
   @Override
   public void mouseClicked(MouseEvent event) {
   }
   @Override
   public void mouseMoved(MouseEvent event) {
   }

   @Override
   public void init() {
      // TODO Auto-generated method stub

   }

   @Override
   public void keyTyped(KeyEvent event) {
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
   public void mousePressed(MouseEvent event) {
      // TODO Auto-generated method stub

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
// class LevelCreator extends