package gfm.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import gfm.Game;
import gfm.Macro;
import gfm.sound.Sound;

public class GUIManager implements Macro {
   private Game myGame;
   private int myCurrHoverSound = 0;
   private Sound[] myHoverSounds = new Sound[7];
   private int myCurrClickSound = 0;
   private Sound[] myClickSounds = new Sound[7];

   /**
    *
    */
   private ArrayList<Button> myButtons;
   private boolean myDisabled;

   public GUIManager(Game game) {
      myGame = game;
      myButtons = new ArrayList<Button>();
      myDisabled = false;
   }

   /**
    * @param pen
    */
   @Override
   public void draw(Graphics pen) {
      for ( int i = 0; i < myButtons.size(); i++ ) {
         Button button = myButtons.get(i);
         if (button.getMouseHovering()) {
            button.drawHovered(pen);
         } else {
            button.draw(pen);
         }
      }
   }

   /**
    * @param event
    */
   @Override
   public void mousePressed(MouseEvent event) {
      if ( myDisabled || event.getButton() != MouseEvent.BUTTON1 ) {
         return;
      }
      for ( int i = 0; i < myButtons.size(); i++ ) {
         Button button = myButtons.get(i);
         if ( button.collidesPoint(event.getX(), event.getY()) ) {
            if ( myClickSounds[ 0 ] != null ) {
               myClickSounds[ myCurrClickSound ].reset();
               myClickSounds[ myCurrClickSound ].play();
               myCurrClickSound++;
               if ( myCurrClickSound > myClickSounds.length - 1) {
                  myCurrClickSound = 0;
               }
            }
            button.doAction();
         }
      }
   }

   /**
    * @param event
    */
   @Override
   public void mouseMoved(MouseEvent event) {
      if ( myDisabled ) { return; }
      for ( int i = 0; i < myButtons.size(); i++ ) {
         Button button = myButtons.get(i);
         if ( button.collidesPoint(event.getX(), event.getY())) {
            if ( button.getMouseHovering() == false ) {
               button.setMouseHovering(true);
               if ( myHoverSounds[ 0 ] != null ) {
                  myHoverSounds[ myCurrHoverSound ].reset();
                  myHoverSounds[ myCurrHoverSound ].play();
                  myCurrHoverSound++;
                  if ( myCurrHoverSound > myHoverSounds.length - 1) {
                     myCurrHoverSound = 0;
                  }
               }
            }
         } else {
            button.setMouseHovering(false);
         }
      }
   }

   /**
    *
    */
   public void resetInputs() {
      for (Button button: myButtons) {
         button.setMouseHovering(false);
      }
   }

   /**
    *
    */
   public void disable() {
      myDisabled = true;
      for ( int i = 0; i < myButtons.size(); i++ ) {
         myButtons.get(i).setMouseHovering(false);
      }
   }

   /**
    *
    */
   public void enable() {
      myDisabled = false;
   }

   public void deleteAllButtons() {
      myButtons = new ArrayList<Button>();
   }

   /**
    * @param button
    */
   public void addButton(Button button) {
      button.setGame(myGame);
      myButtons.add(button);
   }

   public void removeButton(Button button) {
      myButtons.remove(button);
   }

   public void setHoverSound(String sound) {
      myCurrHoverSound = 0;
      for ( int i = 0; i < myHoverSounds.length; i++ ) {
         myHoverSounds[ i ] = new Sound(sound, false);
      }
   }

   public void setClickSound(String sound) {
      myCurrClickSound = 0;
      for ( int i = 0; i < myClickSounds.length; i++ ) {
         myHoverSounds[ i ] = new Sound(sound, false);
      }
   }

   public ArrayList<Button> getButtons() {
      return myButtons;
   }

   public Game getGame() {
      return myGame;
   }

   @Override
   public void update() { }

   @Override
   public void keyPressed(KeyEvent event) { }

   @Override
   public void keyReleased(KeyEvent event) { }

   @Override
   public void keyTyped(KeyEvent event) { }

   @Override
   public void mouseClicked(MouseEvent event) { }

   @Override
   public void mouseDragged(MouseEvent event) { }

   @Override
   public void mouseEntered(MouseEvent event) { }

   @Override
   public void mouseExited(MouseEvent event) { }

   @Override
   public void mouseReleased(MouseEvent event) { }

   @Override
   public void mouseWheelMoved(MouseWheelEvent event) { }
}
