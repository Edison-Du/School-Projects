/**
 * [Circle.java]
 * Represents a circle, a type of ellipse with equal lengths.
 * @author Edison Du
 * @version 1.0 May 12, 2021
 */

class Circle extends Ellipse {
  
  private int diameter;
  
  /**
   * Circle
   * @param centreX The x-coordinate of the circle's centre
   * @param centreY The y-coordinate of the circle's centre
   * @param redValue The red value of the circle's colour
   * @param greenValue The green value of the circle's colour
   * @param blueValue The blue value of the circle's colour
   * @param diameter The diameter of the circle (note: this is also the width and height of the circle)
   */
  Circle(int centreX, int centreY, int redValue, int greenValue, int blueValue, int diameter) {
    super(centreX, centreY, redValue, greenValue, blueValue, diameter, diameter);
    this.diameter = diameter;
  }
  
  /**
   * getPerimeter
   * Calculates the perimeter of the circle
   * @return The perimeter of the circle
   */
  @Override
  public double getPerimeter() {
    return (Math.PI * this.diameter);
  }
  
  /**
   * getDiameter
   * Getter for the diameter
   * @return The diameter of the circle
   */
  public int getDiameter() {
    return this.diameter;
  }
  
  /**
   * setDiameter
   * Changes the diameter, and consequently the width and height of the circle
   * @param diameter The value to change to
   * @return True if the parameter is valid, false otherwise
   */
  public boolean setDiameter(int diameter) {
    if (diameter < 1) {
      return false;
    }
    this.diameter = diameter;
    super.setWidth(diameter);
    super.setHeight(diameter);
    return true;
  }
  
  /**
   * setWidth
   * Changes the width, and consequently the diameter and height of the circle
   * @param width The value to change to
   * @return True if the parameter is valid, false otherwise
   */
  @Override
  public boolean setWidth(int width) {
    if (width < 1) {
      return false;
    }
    this.diameter = width;
    super.setWidth(width);
    super.setHeight(width);
    return true;
  }
  
  /**
   * setHeight
   * Changes the height, and consequently the diameter and width of the circle
   * @param height The value to change to
   * @return True if the parameter is valid, false otherwise
   */
  @Override
  public boolean setHeight(int height) {
    if (height < 1) {
      return false;
    }
    this.diameter = height;
    super.setWidth(height);
    super.setHeight(height);
    return true;
  }
 
}