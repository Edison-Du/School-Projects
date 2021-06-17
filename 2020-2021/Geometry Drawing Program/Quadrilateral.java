/**
 * [Quadrilateral.java]
 * Represents a quadrilateral, a polygon with 4 sides.
 * @author Edison Du
 * @version 1.0 May 12, 2021
 */

abstract class Quadrilateral extends Polygon {
  
  private int width, height;
  
  /**
   * Quadrilateral
   * @param centreX The x-coordinate of the shape's centre
   * @param centreY The y-coordinate of the shape's centre
   * @param redValue The red value of the shape's colour
   * @param greenValue The green value of the shape's colour
   * @param blueValue The blue value of the shape's colour
   * @param width The width/base of the shape
   * @param height The height of the shape
   */
  Quadrilateral(int centreX, int centreY, int redValue, int greenValue, int blueValue, int width, int height) {
    super(centreX, centreY, redValue, greenValue, blueValue, 4);
    this.width = width;
    this.height = height;
  }
  
  /**
   * getWidth
   * Getter for the width
   * @return The width of the shape
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * getHeight
   * Getter for the height
   * @return The height of the shape
   */
  public int getHeight() {
    return this.height;
  }
  
  /**
   * setWidth
   * Changes the width of the shape
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
   * Changes the height of the shape
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
}