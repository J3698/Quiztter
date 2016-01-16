package gfm.gamestate;

import java.util.ArrayList;
import java.util.HashMap;

import gfm.Game;
import gfm.util.ArrayUtils.IterableProtector;

public class GameStateManager {
   private String myCurrentGameState;
   private Game myGame;

   private boolean myIsTransitioning;
   private Transition myTransition;

   private ArrayList<GameState> myGameStatesArray;
   private HashMap<String, GameState> myGameStatesHash;

   public GameStateManager(Game game) {
      this(null, game);
   }

   public GameStateManager(String startGameState, Game game) {
      myCurrentGameState = startGameState;
      myGame = game;

      myIsTransitioning = false;
      myTransition = null;

      myGameStatesArray = new ArrayList<GameState>();
      myGameStatesHash = new HashMap<String, GameState>();
   }

   public IterableProtector<GameState> getStates() {
      return new IterableProtector<GameState>(myGameStatesArray);
   }

   public void add(GameState gameState) {
      add(gameState.getGameMode(), gameState);
   }

   public void add(String name, GameState gameState) {
      if ( myGameStatesHash.containsKey(name) ||
            myGameStatesHash.containsValue(gameState) ) {
         return;
      }

      myGameStatesArray.add(gameState);
      myGameStatesHash.put(name, gameState);
   }

   public void remove(String toRemove) {
      if ( !myGameStatesHash.containsKey(toRemove) ) { return; }

      GameState fromToRemove = myGameStatesHash.get(toRemove);
      myGameStatesHash.remove(toRemove);
      myGameStatesArray.remove(fromToRemove);
   }

   public void remove(GameState toRemove) {
      for ( String key : myGameStatesHash.keySet() ) {
         if ( myGameStatesHash.get(key) == toRemove ) {
            remove(key);
            return;
         }
      }
   }

   public GameState getGameState(String name) {
      return myGameStatesHash.get(name);
   }

   public String getGameState() {
      return myCurrentGameState;
   }

   public GameState getCurrentGameState() {
      if ( myIsTransitioning ) {
         return myTransition;
      } else if ( myGameStatesHash.get(myCurrentGameState) != null ) {
         return myGameStatesHash.get(myCurrentGameState);
      } else {
         return new UnchartedTerritory(myGame);
      }
   }

   public void setGameState(String gameState) {
      myCurrentGameState = gameState;
      myIsTransitioning = false;
      myTransition = null;
   }

   public void setGameState(Transition transition) {
      myIsTransitioning = true;
      myTransition = transition;
   }

   public boolean isTransitioning() {
      return myIsTransitioning;
   }

   public void setIsTransitioning(boolean isTransitioning) {
      myIsTransitioning = isTransitioning;
   }
}