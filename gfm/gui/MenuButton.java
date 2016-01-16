package gfm.gui;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Random;

import gfm.util.Vec2;

public class MenuButton extends BasicButton {
   public MenuButton(ActionListener listener, String text, Vec2 position, Vec2 size, int gameWidth, int gameHeight) {
      super(listener, text, Color.black, Color.black, null, position, size);
      Random r = new Random();
      int base = 100;
      int rand = 255 - base;
      setBodyColor(new Color(base + r.nextInt(rand), base + r.nextInt(rand), base + r.nextInt(rand)));
      setTextColor(new Color(100, 100, 100));
   }
}