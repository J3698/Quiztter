package gfm;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.gamestate.GameState;
import gfm.util.EventUtil;

public class ListenerManager {
   private GamePanel myGamePanel;

   public ListenerManager(GamePanel gamePanel) {
      myGamePanel = gamePanel;
   }

   public void addListeners() {
      myGamePanel.addKeyListener(new KeyListener());
      myGamePanel.addMouseListener(new MouseListener());
      myGamePanel.addMouseMotionListener(new MouseListener());
      myGamePanel.addFocusListener(new FocusListener());
   }

   public class KeyListener extends KeyAdapter {
      @Override
      public void keyPressed(KeyEvent event) {
         for ( Macro macro : myGamePanel.iterMacros() ) {
            macro.keyPressed(event);
         }
         myGamePanel.getGameStateManager().getCurrentGameState().keyPressed(event);
      }

      @Override
      public void keyReleased(KeyEvent event) {
         for ( Macro macro : myGamePanel.iterMacros() ) {
            macro.keyReleased(event);
         }
         myGamePanel.getGameStateManager().getCurrentGameState().keyReleased(event);
      }
      @Override
      public void keyTyped(KeyEvent event) {
         for ( Macro macro : myGamePanel.iterMacros() ) {
            macro.keyTyped(event);
         }
         myGamePanel.getGameStateManager().getCurrentGameState().keyTyped(event);
      }
   }
   public class MouseListener extends MouseAdapter {
      @Override
      public void mouseClicked(MouseEvent event) {
         EventUtil.scaleEvent(event);
         for ( Macro macro : myGamePanel.iterMacros() ) {
            macro.mouseClicked(event);
         }
         myGamePanel.getGameStateManager().getCurrentGameState().mouseClicked(event);
      }
      @Override
      public void mouseDragged(MouseEvent event) {
         EventUtil.scaleEvent(event);
         for ( Macro macro : myGamePanel.iterMacros() ) {
            macro.mouseDragged(event);
         }
         myGamePanel.getGameStateManager().getCurrentGameState().mouseDragged(event);
      }
      @Override
      public void mouseEntered(MouseEvent event) {
         EventUtil.scaleEvent(event);
         for ( Macro macro : myGamePanel.iterMacros() ) {
            macro.mouseEntered(event);
         }
         myGamePanel.getGameStateManager().getCurrentGameState().mouseEntered(event);
      }
      @Override
      public void mouseExited(MouseEvent event) {
         EventUtil.scaleEvent(event);
         for ( Macro macro : myGamePanel.iterMacros() ) {
            macro.mouseExited(event);
         }
         myGamePanel.getGameStateManager().getCurrentGameState().mouseExited(event);
      }
      @Override
      public void mouseMoved(MouseEvent event) {
         EventUtil.scaleEvent(event);
         for ( Macro macro : myGamePanel.iterMacros() ) {
            macro.mouseMoved(event);
         }
         myGamePanel.getGameStateManager().getCurrentGameState().mouseMoved(event);
      }
      @Override
      public void mousePressed(MouseEvent event) {
         EventUtil.scaleEvent(event);
         for ( Macro macro : myGamePanel.iterMacros() ) {
            macro.mousePressed(event);
         }
         myGamePanel.getGameStateManager().getCurrentGameState().mousePressed(event);
      }
      @Override
      public void mouseReleased(MouseEvent event) {
         EventUtil.scaleEvent(event);
         for ( Macro macro : myGamePanel.iterMacros() ) {
            macro.mouseReleased(event);
         }
         myGamePanel.getGameStateManager().getCurrentGameState().mouseReleased(event);
      }
      @Override
      public void mouseWheelMoved(MouseWheelEvent event) {
         EventUtil.scaleEvent(event);
         for ( Macro macro : myGamePanel.iterMacros() ) {
            macro.mouseWheelMoved(event);
         }
         myGamePanel.getGameStateManager().getCurrentGameState().mouseWheelMoved(event);
      }
   }
   private class FocusListener extends FocusAdapter {
      @Override
      public void focusLost(FocusEvent e) {
         for (GameState state : myGamePanel.getGameStateManager().getStates() ) {
            state.getGUIManager().resetInputs();
         }
      }
   }
}





