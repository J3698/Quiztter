package gfm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GFMScanner {
   private String myPath;
   private InputStream myIn;
   private InputStreamReader myInReader;
   private BufferedReader myReader;
   private String myNext;

   public GFMScanner(String path) {
      myPath = path;
      myIn = getClass().getResourceAsStream(myPath);
      myInReader = new InputStreamReader(myIn);
      myReader = new BufferedReader(myInReader);
      myNext = null;
   }

   public String nextLine() throws IOException {
      if ( myNext != null ) {
         String temp = myNext;
         myNext = null;
         return temp;
      }
      return myReader.readLine();
   }

   public boolean hasNextLine() throws IOException {
      if ( myNext != null) {
         return true;
      }
      myNext = myReader.readLine();
      return !(myNext == null);
   }

   public void close() throws IOException {
      myIn.close();
      myInReader.close();
      myReader.close();
   }

   public String getPath() { return myPath; }
}
