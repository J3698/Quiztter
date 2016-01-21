package gfm.sound;

import java.util.HashMap;
import java.util.Iterator;

public class SoundManager {
   private HashMap<String, SoundList> mySounds;

   public SoundManager() {
      mySounds = new HashMap<String, SoundList>();
   }

   public void play(String sound) {
      SoundList toPlay = mySounds.get(sound);
      if ( toPlay != null ) {
         toPlay.play();
      } else {
         SoundList newList = new SoundList(sound);
         mySounds.put(sound, newList);
         newList.play();
      }
   }
}


class SoundList implements Iterable<SoundNode> {
   private String mySound;
   private SoundNode myHead;
   private SoundNode myLast;
   private SoundNode myCurr;

   public SoundList(String sound) {
      mySound = sound;
      myHead = null;
      myLast = null;
      myCurr = null;
   }

   public SoundNode getHead() {
      return myHead;
   }

   public void add(SoundNode toAdd) {
      if ( myHead == null ) {
         myHead = toAdd;
         myLast = toAdd;
         myCurr = toAdd;
      } else {
         myLast.setNext(toAdd);
         myLast = toAdd;
      }
   }

   public void play() {
      while ( myCurr.getSound().isRunning() ) {
         if ( myCurr.getNext() == null ) {
            add(new SoundNode(mySound, null));
         } else {
            myCurr = myCurr.getNext();
         }
      }

      myCurr.getSound().play();
   }

   @Override
   public Iterator<SoundNode> iterator() {
      return new SoundListIterator(this);
   }

   public class SoundListIterator implements Iterator<SoundNode> {
      private SoundNode myCurr;

      private SoundListIterator(SoundList toIter) {
         myCurr  = toIter.getHead();
      }

      @Override
      public boolean hasNext() {
         return myCurr != null;
      }

      @Override
      public SoundNode next() {
         if ( myCurr == null ) {
            return null;
         }
         SoundNode toReturn = myCurr;
         myCurr = myCurr.getNext();
         return toReturn;      }
   }
}

class SoundNode {
   private Sound mySound;
   private SoundNode myNext;

   public SoundNode(String sound, SoundNode next) {
      mySound = new Sound(sound, false);
      myNext = next;
   }

   public Sound getSound() { return mySound; }

   public void setNext(SoundNode next) { myNext = next; }
   public SoundNode getNext() { return myNext; }
}