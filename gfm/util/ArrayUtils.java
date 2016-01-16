package gfm.util;

import java.util.Iterator;
import java.util.Random;

public class ArrayUtils {
   public static Object random(Object[] hat, int start) {
      return random(hat, start, hat.length);
   }
   public static Object random(Object[] hat, int start, int end) {
      if ( start < 0 ) {
         throw new IllegalArgumentException("Start must be positive.");
      } else if ( end > hat.length ) {
         throw new IllegalArgumentException("End must be within array length.");
      } else if ( start > end ) {
         throw new IllegalArgumentException("End must be after start.");
      }

      Random picker = new Random();
      int choice = start + picker.nextInt(end - start);
      return hat[ choice ];
   }

   public static void selectionSort(Comparable<Object>[] array) {
      for ( int i = array.length; i > 0; i-- ) {
         int indexOfMax = findMax(array, i);
         swap(array, indexOfMax, i - 1);
      }
   }

   private static int findMax(Comparable<Object>[] array, int n) {
      int indexOfMax = 0;
      for ( int i = 1; i < n; i++ ) {
         if ( array[i].compareTo(array[indexOfMax]) > 0 ) {
            indexOfMax = i;
         }
      }
      return indexOfMax;
   }

   private static void swap(Comparable<Object>[] array, int a, int b) {
      Comparable<Object> temp = array[b];
      array[b] = array[a];
      array[a] = temp;
   }

   public static double[] concat(double[] first, double[] second) {
      double[] toReturn = new double[ first.length + second.length ];
      for ( int i = 0; i < first.length; i++ ) {
         toReturn[ i ]  = first[ i ];
      }
      for ( int i = first.length; i < toReturn.length; i++) {
         toReturn[ i ] = second[ i - first.length ];
      }
      return toReturn;
   }

   public static class IterableProtector<T> implements Iterable<T> {
      private Iterable<T> myIterable;

      public IterableProtector(Iterable<T> iterable) {
         myIterable = iterable;
      }

      @Override
      public Iterator<T> iterator() {
         return myIterable.iterator();
      }
   }
}