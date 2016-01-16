package gfm.util;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Vec2 {                               //Two dimensional vector
   private double x;                                 //CVector elements
   private double y;

   public Vec2() {                                //Constructor
      this(0, 0);
   }

   public Vec2(double _x, double _y) {
      x = _x;
      y = _y;
   }

   public void setX(double _x) { x = _x; }           //Setters and getters
   public double getX() { return x; }

   public void setY(double _y) { y = _y; }
   public double getY(){ return y; }

   public void addX(double scalar){
      x += scalar;
   }
   public void addY(double scalar){
      y += scalar;
   }
   public void subX(double scalar){
      x -= scalar;
   }
   public void subY(double scalar){
      y -= scalar;
   }
   public void multiplyX(double scalar){
      x *= scalar;
   }
   public void multiplyY(double scalar){
      y *= scalar;
   }
   public void divideX(double scalar){
      x /= scalar;
   }
   public void divideY(double scalar){
      y /= scalar;
   }
   public void addVector(Vec2 v){                 //Basic vector arithmetic
      x += v.getX();
      y += v.getY();
   }
   public void subVector(Vec2 v){
      x -= v.getX();
      y -= v.getY();
   }
   public void multiply(double scalar){
      x = x * scalar;
      y = y * scalar;
   }
   public void divide(double scalar){
      if (scalar == 0){
         throw new IllegalArgumentException("Vector divided by zero");
      }
      x /= scalar;
      y /= scalar;
   }
   public double getMagnitude(){                     //Return the magnitude
      return Math.pow(x*x + y*y, 0.5);
   }
   public void normalizeMagnitude(){                 //Manipulate the magnitude
      double mag = getMagnitude();
      if (mag != 0){
         x /= mag;
         y /= mag;
      }
   }
   public void setMagnitude(double scalar){
      normalizeMagnitude();
      multiply(scalar);
   }
   public void limitMagnitude(double scalar){
      if(getMagnitude() > scalar) {
         setMagnitude(scalar);
      }
   }
   public double asRadians(){                        //Return vector heading
      if(x == 0) {
         return Math.PI / 2;
      }
      return Math.atan2(y, x);
   }
   public double asDegrees(){
      return Math.toDegrees(asRadians());
   }
   public void rotateAboutRadians(Vec2 center, double angle) {
      subVector(center);
      rotateRadians(angle);
      addVector(center);
   }
   public void rotateAboutDegrees(Vec2 center, double angle) {
      rotateAboutRadians(center, Math.toRadians(angle % 360));
   }
   public void rotateRadians(double angle){          //Rotate the vector
      while(Math.abs(angle) > 2 * Math.PI){
         angle -= Math.abs(angle) / angle * 2 * Math.PI;
      }
      double tempX = x;
      x = x * Math.cos(angle) - y * Math.sin(angle);
      y = y * Math.cos(angle) + tempX * Math.sin(angle);
   }
   public void rotateDegrees(double angle){
      rotateRadians(Math.toRadians(angle % 360));
   }
   public double distance(Vec2 other) {
      Vec2 copy = copy();
      copy.subVector(other);
      return copy.getMagnitude();
   }
   public Vec2 copy() {
      return new Vec2(x, y);
   }
   public double[] toArray() {
      return new double[] {x, y};
   }
   @Override
   public String toString(){
      return "(" + x + ", " + y + ")";
   }

   public boolean equals(Vec2 other) {
      return x == other.getX() && y == other.getY();
   }

   public static Vec2 randVector(double minRotate, double maxRotate) {
      Vec2 toReturn = new Vec2(1, 0);
      toReturn.rotateRadians(new Random().nextDouble() * (maxRotate - minRotate) + minRotate);
      return toReturn;
   }

   public static Vec2 randSquareVector(double xLowerLim, double yLowerLim,
         double xUpperLim, double yUpperLim) {
      Random rand = new Random();
      double x = rand.nextDouble() * (xUpperLim - xLowerLim) + xLowerLim;
      double y = rand.nextDouble() * (yUpperLim - yLowerLim) + yLowerLim;
      return new Vec2(x, y);
   }
}





class Vector2List {
   private ArrayList<Vec2> myVectors;

   public Vector2List() {
      myVectors = new ArrayList<Vec2>();
   }

   public void add(double x, double y) {
      add(new Vec2(x, y));
   }

   public void add(Vec2 _vector2) {
      myVectors.add(_vector2);
   }

   public void transform(TMatrix tMatrix) {
      for (Vec2 vector2 : myVectors) {
         double tempX = vector2.getX();
         double tempY = vector2.getY();
         vector2.setX(tempX * tMatrix.get(1, 1) + tempY * tMatrix.get(1, 2));
         vector2.setY(tempX * tMatrix.get(2, 1) + tempY * tMatrix.get(2, 2));
      }
   }

   public void addPos(double x, double y) {
      for (Vec2 vector2 : myVectors) {
         vector2.setX(vector2.getX() + x);
         vector2.setY(vector2.getY() + y);
      }
   }

   @Override
   public Vector2List clone() {
      Vector2List copy = new Vector2List();
      for (Vec2 vector2: myVectors) {
         copy.add(vector2.copy());
      }

      return copy;
   }

   public void draw(Graphics pen) {
      int numPoints = myVectors.size();
      int[] xPoints = new int[numPoints];
      int[] yPoints = new int[numPoints];

      int index = 0;
      for (Vec2 vector2 : myVectors) {
         xPoints[index] = (int) vector2.getX();
         yPoints[index] = (int) vector2.getY();
         index++;
      }

      pen.drawPolygon(xPoints, yPoints, numPoints);
   }
}

class TMatrix {
   private double a;
   private double b;
   private double c;
   private double d;

   public TMatrix(double _a, double _b,
         double _c, double _d) {
      a = _a;
      b = _b;
      c = _c;
      d = _d;
   }

   public double get(int row, int col) {
      if (row == 1 && col == 1) {
         return a;
      } else if (row == 2 && col == 1) {
         return c;
      } else if (row == 1 && col == 2) {
         return b;
      } else if (row == 2 && col == 2) {
         return d;
      }

      throw new IllegalArgumentException("OUT OF BOUNDS!!");
   }
}