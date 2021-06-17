/**
 * [Polygon.java]
 * Represents a polygon shape composed of a definite number of vertexes and straight line segments.
 * @author Edison Du
 * @version 1.0 May 12, 2021
 */

import java.awt.Graphics;
import java.awt.Color;

abstract class Polygon extends TwoDimensionalShape {
  
  private int points;
  
  /**
   * Polygon
   * @param centreX The x-coordinate of the shape's centre
   * @param centreY The y-coordinate of the shape's centre
   * @param redValue The red value of the shape's colour
   * @param greenValue The green value of the shape's colour
   * @param blueValue The blue value of the shape's colour
   * @param points The number of vertexes in the shape
   */
  Polygon(int centreX, int centreY, int redValue, int greenValue, int blueValue, int points) {
    super(centreX, centreY, redValue, greenValue, blueValue);
    this.points = points;
  }
  
  /**
   * getXValues
   * Finds and returns an array containing the x-values of each vertex in the polygon
   * These x-values correspond to the y-values returned from the method getYValues()
   * @return An array containing the x-values
   */
  public abstract int[] getXValues();
  
  /**
   * getYValues
   * Finds and returns an array containing the y-values of each vertex in the polygon
   * These y-values correspond to the x-values returned from the method getXValues()
   * @return An array containing the y-values
   */
  public abstract int[] getYValues();
  
  /**
   * getPoints
   * Getter for the number of vertexes
   * @return The number of vertexes in the polygon
   */
  public int getPoints() {
    return this.points;
  }
  
  /**
   * draw
   * Draws a polygon onto a graphics object using the drawPolygon() method from the Graphics class
   * @param g The graphics object to draw on
   */
  public void draw (Graphics g) {
    g.setColor(new Color(this.getRedValue(),this.getGreenValue(),this.getBlueValue()));
    g.drawPolygon(this.getXValues(), this.getYValues(), this.getPoints());
  }
}
