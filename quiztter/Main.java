package quiztter;

/**

 * Example
 * @version 1.0
 * @author
 */

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import gfm.Game;
import gfm.GameFrame;
import gfm.gamestate.GameState;
import gfm.gui.GUIManager;
import gfm.util.Vec2;

public class Main {
   // draw space, different from space taken up on screen
   public static final Color[] scheme = new Color[] {
         new Color(85, 172, 238), new Color(41, 47, 51),
         new Color(102, 117, 127), new Color(136, 153, 166),
         new Color(204, 214, 221), new Color(225, 232, 237),
         new Color(245, 248, 150) };

   public static int gameWidth = (int) (640);
   public static int gameHeight = (int) (480);

   public static final int questionsAGame = 5;
   public static final int lives = 3;

   public static void main(String[] args) {
      // set JOptionPane Colors
      UIManager.put("OptionPane.background", new ColorUIResource(85, 172, 238));
      UIManager.put("Panel.background", new ColorUIResource(85, 172, 238));
      UIManager.put("OptionPane.messageForeground", new ColorUIResource(225, 232, 237));

      // initialize twitter
      GTwitter.init();

      // create new game and set game state
      Game game = new Game("Quiztter", gameWidth, gameHeight);
      game.setGameState("intro");

      // handle versioning
      try {
         game.getLauncher().initVersioning("/quiztter/versioning.txt");
      } catch (UnknownHostException e) {
         e.printStackTrace();
      }

      // Set game cursor
      makeTwitterCursor(game);

      // initialize and add game states
      GameState pininput = new PinInput(game);
      GameState intro = new Intro(game);
      GameState upgrade = new Upgrade(game);
      GameState loadingQuestions = new LoadingQuestions(game, "load");
      GameState play = new Play(game, questionsAGame, lives);
      GameState gameOver = new GameOver(game, "over");
      game.addGameState(intro);
      game.addGameState(upgrade);
      game.addGameState(pininput);
      game.addGameState(loadingQuestions);
      game.addGameState(play);
      game.addGameState(gameOver);

      // add maouse macro
      game.addMacro(new TwitterMouseMacro());

      // fullscreen option
      int fullscreen = JOptionPane.showConfirmDialog(null, "Go Fullscreen?\nRUN NORMAL In JGRASP");
      if ( fullscreen == JOptionPane.YES_OPTION ) {
         game.setFullScreen();
         // add exit button
         GUIManager macroGUI = new GUIManager(game);
         macroGUI.addButton(new ExitButton(new Vec2(620, 0), new Vec2(20, 20)));
         game.addMacro(macroGUI);
      } else if ( fullscreen == JOptionPane.NO_OPTION ) {
         addSaveOnExitListener(game);
      } else if ( fullscreen == JOptionPane.CANCEL_OPTION ) {
         System.exit(0);
      } else if ( fullscreen == JOptionPane.CLOSED_OPTION ) {
         System.exit(0);
      }


      // get the party started!!
      game.start();

      // TODO
      // System.out.println("Sound Button Macro");
      // System.out.println("Music Player Class For Game");
   }

   public static void addSaveOnExitListener(Game game) {
      GameFrame frame = game.getGameFrame();
      frame.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent windowEvent) {
            JOptionPane.showConfirmDialog(null, "...");
         }
      });
   }

   public static void makeTwitterCursor(Game game) {
      int cursorWidth = 15;
      int cursorHeight = 15;
      Toolkit kit = Toolkit.getDefaultToolkit();
      Dimension dim = kit.getBestCursorSize(cursorWidth, cursorHeight);
      BufferedImage cursorImg =
            new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
      Graphics pen = cursorImg.createGraphics();
      pen.setColor(scheme[0]);
      pen.fillRect(1, 1, cursorWidth, cursorHeight);
      pen.setColor(scheme[0].darker());
      pen.fillRect(0, 0, cursorWidth, cursorHeight);
      pen.dispose();
      Cursor cursor = kit.createCustomCursor(cursorImg, new Point(cursorWidth / 2, cursorHeight / 2), "cursor");
      game.getGamePanel().setCursor(cursor);
   }
}