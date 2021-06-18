/**
 * [Square.java]
 * Represents a square, a rectangle with equal sides.
 * @author Edison Du
 * @version 1.0 May 12, 2021
 */

class Square extends Rectangle {
  
  private int sideLength;
  
  /**
   * Square
   * @param centreX The x-coordinate of the square's centre
   * @param centreY The y-coordinate of the square's centre
   * @param redValue The red value of the square's colour
   * @param greenValue The green value of the square's colour
   * @param blueValue The blue value of the square's colour
   * @param sideLength The side length of the square (note: this is also the width and height of the square)
   */
  Square(int centreX, int centreY, int redValue, int greenValue, int blueValue, int sideLength) {
    super(centreX, centreY, redValue, greenValue, blueValue, sideLength, sideLength);
    this.sideLength = sideLength;
  }
  
  /**
   * getSideLength
   * Getter for the side length
   * @return The side length of the square
   */
  public int getSideLength() {
    return this.sideLength;
  }
  
  /**
   * setSideLength
   * Changes the side length, and consequently the width and height of the square
   * @param sideLength The value to change to
   * @return True if the parameter is valid, false otherwise
   */
  public boolean setSideLength(int sideLength) {
    if (sideLength < 1) {
      return false;
    }
    this.sideLength = sideLength;
    super.setWidth(sideLength);
    super.setHeight(sideLength);
    return true;
  }
  
  /**
   * setWidth
   * Changes the width, and consequently the side length and height of the square
   * @param width The value to change to
   * @return True if the parameter is valid, false otherwise
   */
  @Override
  public boolean setWidth(int width) {
    if (width < 1) {
      return false;
    }
    this.sideLength = width;
    super.setWidth(width);
    super.setHeight(width);
    return true;
  }
  
  /**
   * setHeight
   * Changes the height, and consequently the side length and width of the square
   * @param height The value to change to
   * @return True if the parameter is valid, false otherwise
   */
  @Override
  public boolean setHeight(int height) {
    if (height < 1) {
      return false;
    }
    this.sideLength = height;
    super.setWidth(height);
    super.setHeight(height);
    return true;
  }
}
