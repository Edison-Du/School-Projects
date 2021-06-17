/**
 * [Triangle.java]
 * Represents an equilateral triangle.
 * @author Edison Du
 * @version 1.0 May 12, 2021
 */

class Triangle extends Polygon {

  private int sideLength;
  
  /**
   * Triangle
   * @param centreX The x-coordinate of the triangle's centre
   * @param centreY The y-coordinate of the triangle's centre
   * @param redValue The red value of the triangle's colour
   * @param greenValue The green value of the triangle's colour
   * @param blueValue The blue value of the triangle's colour
   * @param sideLength The side length of the triangle (note: this is also the width and height of the triangle)
   */
  Triangle(int centreX, int centreY, int redValue, int greenValue, int blueValue, int sideLength) {
    super(centreX, centreY, redValue, greenValue, blueValue, 3);
    this.sideLength = sideLength;
  }
  
  /**
   * getPerimeter
   * Calculates the perimeter of the triangle
   * @return The perimeter of the triangle
   */
  public double getPerimeter() {
    return (sideLength * 3);
  }
  
  /**
   * getArea
   * Calculates the area of the triangle, using the 30-60-90 triangle rule to find the height
   * @return The area of the triangle
   */
  public double getArea() {
    double height = (this.sideLength/2.0) * Math.sqrt(3.0);
    return ((this.sideLength * height) / 2.0);
  }
  
  /**
   * getXValues
   * Finds the x-values of the triangle's points
   * @return An array containing the x-values
   */
  public int[] getXValues() {
    int[] xValues = new int[4];
    xValues[0] = this.getCentreX();
    xValues[1] = (int)(this.getCentreX() + this.sideLength/2.0);
    xValues[2] = (int)(this.getCentreX() - this.sideLength/2.0);
    return xValues;
  }
  
  /**
   * getYValues
   * Finds the y-values of the triangle's points
   * @return An array containing the y-values
   */
  public int[] getYValues() {
    int[] yValues = new int[4];
    double halfHeight = (this.sideLength / 4.0) * Math.sqrt(3.0);
    yValues[0] = (int)(this.getCentreY() - halfHeight);
    yValues[1] = (int)(this.getCentreY() + halfHeight);
    yValues[2] = (int)(this.getCentreY() + halfHeight);
    return yValues;
  }
  
  /**
   * getSideLength
   * Getter for the side length
   * @return The side length of the triangle
   */
  public int getSideLength() {
    return this.sideLength;
  }
  
  /**
   * setSideLength
   * Changes the side length of the triangle
   * @param sideLength The value to change into
   * @return True if the parameter is valid, false otherwise
   */
  public boolean setSideLength(int sideLength) {
    if (sideLength < 1) {
      return false;
    }
    this.sideLength = sideLength;
    return true;
  }
}