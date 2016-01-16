package gfm;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
   private static final long serialVersionUID = 1488512595217358382L;

   private static final int myDefaultWidth = 770;
   private static final int myDefaultHeight = 580;

   private GamePanel myGamePanel;
   private String myName;

   public GameFrame(String name, GamePanel gamePanel) {
      super(name);
      myName = name;
      myGamePanel = gamePanel;
      getContentPane().add(myGamePanel);
      setSize(myDefaultWidth, myDefaultHeight);
      setLocationRelativeTo(null);
      setResizable(true);
      //setMinimumSize(new Dimension(myGamePanel.getGameWidth(), myGamePanel.getGameHeight()));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public GamePanel getGamePanel() {
      return myGamePanel;
   }

   @Override
   public String getName() {
      return myName;
   }
}
