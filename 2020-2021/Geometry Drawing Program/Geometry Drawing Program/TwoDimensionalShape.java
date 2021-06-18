/**
 * [TwoDimensionalShape.java]
 * Represents a 2D-shape with coordinates, colour, and can be drawn.
 * @author Edison Du
 * @version 1.0 May 12, 2021
 */

abstract class TwoDimensionalShape implements Drawable {
  
  private int centreX, centreY;
  private int redValue, greenValue, blueValue;
  
  /**
   * TwoDimensionalShape
   * @param centreX The x-coordinate of the shape's centre
   * @param centreY The y-coordinate of the shape's centre
   * @param redValue The red value of the shape's colour
   * @param greenValue The green value of the shape's colour
   * @param blueValue The blue value of the shape's colour
   */
  TwoDimensionalShape(int centreX, int centreY, int redValue, int greenValue, int blueValue) {
    this.centreX = centreX;
    this.centreY = centreY;
    this.redValue = redValue;
    this.greenValue = greenValue;
    this.blueValue = blueValue;
  }
  
  /**
   * getPerimeter
   * Calculates the perimeter of the shape
   * @return The perimeter of the shape
   */
  public abstract double getPerimeter();

  /**
   * getArea
   * Calculates the area of the shape
   * @return The area of the shape
   */
  public abstract double getArea();
  
  /**
   * getCentreX
   * Getter for the x-value of the centre of the shape
   * @return The x-value of the centre of the shape
   */
  public int getCentreX() {
    return this.centreX;
  }
  
  /**
   * getCentreY
   * Getter for the y-value of the centre of the shape
   * @return The y-value of the centre of the shape
   */
  public int getCentreY() {
    return this.centreY;
  }
  
  /**
   * getRedValue
   * Getter for the red value of the shape's colour
   * @return The red value of the shape
   */
  public int getRedValue() {
    return this.redValue;
  }

  /**
   * getGreenValue
   * Getter for the green value of the shape's colour
   * @return The green value of the shape
   */
  public int getGreenValue() {
    return this.greenValue;
  }
  
  /**
   * getBlueValue
   * Getter for the blue value of the shape's colour
   * @return The blue value of the shape
   */
  public int getBlueValue() {
    return this.blueValue;
  }
  
  /**
   * setCentreX
   * Changes the x-coordinate of the centre
   * @param centreX The x value to change to
   */
  public void setCentreX(int centreX) {
    this.centreX = centreX;
  }
  
  /**
   * setCentreY
   * Changes the y-coordinate of the centre
   * @param centreY The y value to change to
   */
  public void setCentreY(int centreY) {
    this.centreY = centreY;
  }
  
  /**
   * setRedValue
   * Changes the red colour value of the shape
   * @param redValue The value to change to
   * @return True if the parameter is valid, false otherwise
   */
  public boolean setRedValue(int redValue) {
    if ( (redValue < 0) || (redValue > 255) ) {
      return false;
    }
    this.redValue = redValue;
    return true;
  }
  
  /**
   * setGreenValue
   * Changes the green colour value of the shape
   * @param greenValue The value to change to
   * @return True if the parameter is valid, false otherwise
   */
  public boolean setGreenValue(int greenValue) {
    if ( (greenValue < 0) || (greenValue > 255) ) {
      return false;
    }
    this.greenValue = greenValue;
    return true;
  }
  
  /**
   * setBlueValue
   * Changes the blue colour value of the shape
   * @param blueValue The value to change to
   * @return True if the parameter is valid, false otherwise
   */
  public boolean setBlueValue(int blueValue) {
    if ( (blueValue < 0) || (blueValue > 255) ) {
      return false;
    }
    this.blueValue = blueValue;
    return true;
  }
}
