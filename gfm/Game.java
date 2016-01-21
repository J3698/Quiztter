package gfm;

import java.awt.Point;
import java.awt.Toolkit;

import gfm.sound.SoundManager;
import gfm.gamestate.GameState;
import gfm.gamestate.Transition;

public class Game {
   private String myName;

   private GamePanel myGamePanel;
   private GameFrame myGameFrame;
   private Launcher myLauncher;
   private SoundManager mySoundManager;

   public Game(String name, int gameWidth, int gameHeight) {
      myName = name;
      myGamePanel = new GamePanel(this, gameWidth, gameHeight, "");
      myGameFrame = new GameFrame(name, myGamePanel);
      myLauncher = new Launcher(this);
      mySoundManager = new SoundManager();
   }

   public void setGameState(String gameState) {
      myGamePanel.getGameStateManager().setGameState(gameState);
   }

   public void setGameState(Transition transition) {
      myGamePanel.getGameStateManager().setGameState(transition);
   }

   public GameState getGameState(String string) {
      return myGamePanel.getGameStateManager().getGameState(string);
   }


   public void addGameState(GameState toAdd) {
      addGameState(toAdd.getGameMode(), toAdd);
   }

   public void addGameState(String name, GameState toAdd) {
      toAdd.setGame(this);
      myGamePanel.getGameStateManager().add(name, toAdd);
   }

   public void addMacro(Macro toAdd) {
      myGamePanel.addMacro(toAdd);
   }

   public void setFullScreen() {
      myGameFrame.getContentPane().remove(myGamePanel);
      myGameFrame.setVisible(false);
      myGameFrame.dispose();
      myGameFrame = new GameFrame(myName, myGamePanel);
      myGameFrame.setUndecorated(true);
      myGameFrame.getContentPane().add(myGamePanel);
      myGameFrame.setSize(
            Toolkit.getDefaultToolkit().getScreenSize().width,
            Toolkit.getDefaultToolkit().getScreenSize().height);
      myGameFrame.setLocation(new Point(0, 0));
      myGameFrame.setVisible(true);
   }

   public void start() {
      myGamePanel.start();
      myGameFrame.setVisible(true);
   }

   public int getWidth() { return myGamePanel.getGameWidth(); }
   public void setWidth(int width) { myGamePanel.setGameWidth(width); }

   public int getHeight() { return myGamePanel.getGameHeight(); }
   public void setHeight(int height) { myGamePanel.setGameHeight(height); }

   public String getName() { return myName; }
   public GamePanel getGamePanel() { return myGamePanel; }
   public GameFrame getGameFrame() { return myGameFrame; }
   public Launcher getLauncher() { return myLauncher; }
   public SoundManager getSoundManager() { return mySoundManager; }
}