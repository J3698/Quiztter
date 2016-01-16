package quiztter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import gfm.gui.BasicButton;
import gfm.util.Vec2;

public class SimpleButton extends BasicButton {

   public SimpleButton(ActionListener listener, String text, Color bodyColor, Color textColor, Font font,
         Vec2 position, Vec2 size) {
      super(listener, text, bodyColor, textColor, font, position, size);
   }
}
