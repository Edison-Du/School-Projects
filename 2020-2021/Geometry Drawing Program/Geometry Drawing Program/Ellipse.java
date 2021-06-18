/**
 * [Ellipse.java]
 * Represents an ellipse/oval.
 * @author Edison Du
 * @version 1.0 May 12, 2021
 */

import java.awt.Graphics;
import java.awt.Color;

class Ellipse extends TwoDimensionalShape {
  
  private int width, height;
  
  /**
   * Ellipse
   * @param centreX The x-coordinate of the ellipse's centre
   * @param centreY The y-coordinate of the ellipse's centre
   * @param redValue The red value of the ellipse's colour
   * @param greenValue The green value of the ellipse's colour
   * @param blueValue The blue value of the ellipse's colour
   * @param width The width of the ellipse
   * @param height The height of the ellipse
   */
  Ellipse(int centreX, int centreY, int redValue, int greenValue, int blueValue, int width, int height) {
    super(centreX, centreY, redValue, greenValue, blueValue);
    this.width = width;
    this.height = height;
  }
  
  /**
   * getPerimeter
   * Calculates the perimeter of the ellipse using Ramanujan's approximation
   * @return The perimeter of the ellipse
   */
  public double getPerimeter() {
    double a = width/2.0;
    double b = height/2.0;
    double perimeter;
    perimeter = Math.sqrt((3*a+b)*(3*b+a));
    perimeter = 3*(a+b) - perimeter;
    perimeter *= Math.PI;
    return perimeter;
  }
  
  /**
   * getArea
   * Calculates the area of the ellipse 
   * @return The area of the ellipse
   */
  public double getArea() {
    double a = width/2.0;
    double b = height/2.0;
    double area = Math.PI * a * b;
    return area;
  }
  
  /**
   * getWidth
   * Getter for the width
   * @return The width of the ellipse
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * getHeight
   * Getter for the height
   * @return The height of the ellipse
   */
  public int getHeight() {
    return this.height;
  }
  
  /**
   * setWidth
   * Changes the width of the ellipse
   * @param width The value to change to
   * @return True if the parameter is valid, false otherwise
   */
  public boolean setWidth(int width) {
    if (width < 1) {
      return false;
    }
    this.width = width;
    return true;
  }
  
  /**
   * setHeight
   * Changes the height of the ellipse
   * @param height The value to change to
   * @return True if the parameter is valid, false otherwise
   */
  public boolean setHeight(int height) {
    if (height < 1) {
      return false;
    }
    this.height = height;
    return true;
  }
  
  /**
   * draw
   * Draws an elipse onto a graphics object using the drawOval() method from the Graphics class
   * @param g The graphics object to draw on
   */
  public void draw(Graphics g) {
    int topLeftX = (int)(this.getCentreX()-this.width/2.0);
    int topLeftY = (int)(this.getCentreY()-this.height/2.0);
    g.setColor(new Color(this.getRedValue(),this.getGreenValue(),this.getBlueValue()));
    g.drawOval(topLeftX, topLeftY, this.width, this.height);
  }
}
