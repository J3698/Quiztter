
package gfm.gui;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Random;

import gfm.util.Vec2;

public class InstructionsButton extends BasicButton {
   public InstructionsButton(ActionListener listener, String text, Vec2 position, int gameWidth, int gameHeight) {
      super(listener, text, Color.black, Color.black, null, position, new Vec2(140, 50));
      Random r = new Random();
      setBodyColor(new Color(150 + r.nextInt(50), 150 + r.nextInt(50), 150 + r.nextInt(50)));
      setTextColor(new Color(100, 100, 100));
   }
}
