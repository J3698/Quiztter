package gfm.gamestate;

import gfm.Game;

public abstract class Transition extends GameState {
   private GameState myFirstGameState;
   private GameState mySecondGameState;

   public Transition(Game game, String first, String second) {
      super(game);

      myFirstGameState = getGame().getGameState(first);
      mySecondGameState = getGame().getGameState(second);

      if ( getGame() != myFirstGameState.getGame() ||
            getGame() != mySecondGameState.getGame() ) {
         throw new IllegalArgumentException("Game State GamePanels Mismatch");
      }
   }

   public final void finish() {
      getGame().setGameState(mySecondGameState.getGameMode());
      getGame().getGamePanel().getGameStateManager().setIsTransitioning(false);
   }

   public final GameState getFirstGameState() { return myFirstGameState; }
   public final GameState getSecondGameState() { return mySecondGameState; }
}