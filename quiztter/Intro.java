package quiztter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import gfm.Game;
import gfm.gamestate.FadeTransition;
import gfm.gamestate.GameState;
import gfm.gamestate.Transition;
import gfm.gui.Button;
import gfm.sound.Sound;
import gfm.util.ArrayUtils;
import gfm.util.ColorCross;
import gfm.util.StringDraw;
import gfm.util.Vec2;

public class Intro extends GameState {
   public static final Color[] scheme = new Color[] {
         new Color(85, 172, 238), new Color(41, 47, 51),
         new Color(102, 117, 127), new Color(136, 153, 166),
         new Color(204, 214, 221), new Color(225, 232, 237)
   };

   private int quizOpacity;
   private int tterPos;
   private ColorCross myBG;
   private Button myAuthButton;
   private Button myCreditsButton;
   private int myCrossSteps;
   private int sineWave;
   private Sound myMusic;
   private boolean myMusicStarted;

   private boolean loginButtonCreated;

   public Intro (Game game) {
      super(game);
   }
   public Intro(Game game, String stateName) {
      super(game, stateName);
   }

   @Override
   public void init() {
      quizOpacity = 0;
      tterPos = getGame().height() * 2 / 3;
      myCrossSteps = 250;
      myBG = new ColorCross(scheme[0], (Color) ArrayUtils.random(scheme, 1), myCrossSteps);
      sineWave = 0;
      loginButtonCreated = false;
      myMusicStarted = false;
   }
   @Override
   public void initUI() {
      Vec2 pos = new Vec2(30, getGame().height() / 2 - 80);
      Vec2 size = new Vec2(160, 160);
      myAuthButton = new TwitterAuthButton(new AuthTwitterListener(), pos, size);
      myCreditsButton = new SimpleButton(new CreditsListener(), "CREDITS",
            new Color(0, 0, 0, 0), Color.white, new Font("Ariel", 1, 12),
            new Vec2(290, 455), new Vec2(60, 30));
      myCreditsButton.getPosition().addY(100);
      getGUIManager().addButton(myCreditsButton);
   }

   // sorry for bad code here :P
   @Override
   public void draw(Graphics pen) {
      int width = getGame().width();
      int height = getGame().height();
      int wave = (int) (-10 * Math.sin(sineWave / 25.0));

      pen.setColor(myBG.getCurrentColor());
      pen.fillRect(0, 0, width, height);

      pen.setFont(new Font("SansSerif", Font.BOLD, 100));

      pen.setColor(new Color(255, 255, 255, quizOpacity));
      StringDraw.drawStringCenter(pen, "Quiz", width / 2 - 131 + 120, height - tterPos + wave);

      pen.setColor(Color.white);
      StringDraw.drawStringCenter(pen, "tter", width / 2 + 60 + 120, tterPos + wave);
      StringDraw.drawStringCenter(pen, "Twi", width / 2 - 100 + 120, height * 2 * 4 / 3 - 3 * tterPos + wave);

      getGUIManager().draw(pen);
   }
   @Override
   public void update() {
      if ( !myMusicStarted ) {
         myMusicStarted = true;
         myMusic = new Sound("./quiztter/conversion", false);
         myMusic.loop();
         myMusic.play();
      }

      if ( quizOpacity < 248 ) {
         quizOpacity += 2;
      } else if ( tterPos != getGame().height() / 2 ) {
         tterPos -= 4;
      } else {
         if ( myBG.isFinished() ) {
            Color choice = (Color) ArrayUtils.random(scheme, 0);
            myBG = new ColorCross(myBG.getCurrentColor(), choice, myCrossSteps);
         }
         myBG.next();

         if ( !loginButtonCreated ) {
            getGUIManager().addButton(myAuthButton);
            myCreditsButton.getPosition().subY(100);
            loginButtonCreated = true;
         }
      }
      sineWave++;
   }

   private class AuthTwitterListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         if ( GTwitter.loggedIn ) {
            Transition toLoading = new FadeTransition(getGame(), getGameMode(), "load", 100, Main.scheme[0]);
            getGame().setGameState(toLoading);
         } else {
            GTwitter.pointUserToAuthURL();
            Transition toPinInput = new FadeTransition(getGame(), getGameMode(), "pininput", 100, Main.scheme[0]);
            getGame().setGameState(toPinInput);
         }
      }
   }

   public class CreditsListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent event) {
         int red = myBG.getCurrentColor().getRed();
         int green = myBG.getCurrentColor().getGreen();
         int blue = myBG.getCurrentColor().getBlue();
         UIManager.put("OptionPane.background", new ColorUIResource(red, green, blue));
         UIManager.put("Panel.background", new ColorUIResource(red, green, blue));

         String message = "Credits:\n" + "Creator: Antioch Sanders\n" +
               "Teacher: Mr. Rudwick\n" + "Twitter: Twitter\n" +
               "Music: Elizabeth Sherrock ->\n" +
               "        https://soundcloud.com/lizzyd710\n" +
               "Sound: www.freesfx.co.uk\n" +
               "API: twitter4j.org\n" + "Support: You :)";

         JOptionPane.showMessageDialog(null, message);
         UIManager.put("OptionPane.background", new ColorUIResource(85, 172, 238));
         UIManager.put("Panel.background", new ColorUIResource(85, 172, 238));
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
      getGUIManager().mousePressed(event);
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
      getGUIManager().mouseMoved(event);
   }
   @Override
   public void mousePressed(MouseEvent event) {
      /*
      if ( tterPos == getGamePanel().getGameHeight() / 2 ) {
         int x = event.getX();
         int y = event.getY();
         myMouseParticles.setSourcePosition(new Vector2(x, y));
         myMouseParticles.setFertility(1);
      }
       */
   }
   @Override
   public void mouseReleased(MouseEvent event) {
   }
   @Override
   public void mouseWheelMoved(MouseWheelEvent event) {
   }
}