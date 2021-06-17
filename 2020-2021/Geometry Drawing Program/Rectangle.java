/**
 * [Rectangle.java]
 * Represents a rectangle, a quadrilateral with sides that align to the x and y axis.
 * @author Edison Du
 * @version 1.0 May 12, 2021
 */

class Rectangle extends Quadrilateral {
  
  /**
   * Rectangle
   * @param centreX The x-coordinate of the rectangle's centre
   * @param centreY The y-coordinate of the rectangle's centre
   * @param redValue The red value of the rectangle's colour
   * @param greenValue The green value of the rectangle's colour
   * @param blueValue The blue value of the rectangle's colour
   * @param width The width of the rectangle
   * @param height The height of the rectangle
   */
  Rectangle(int centreX, int centreY, int redValue, int greenValue, int blueValue, int width, int height) {
    super(centreX, centreY, redValue, greenValue, blueValue, width, height);
  }
  
  /**
   * getPerimeter
   * Calculates the perimeter of the rectangle
   * @return The perimeter of the rectangle
   */
  public double getPerimeter() {
    return ((this.getWidth() + this.getHeight())*2);
  }

  /**
   * getArea
   * Calculates the area of the rectangle
   * @return The area of the rectangle
   */
  public double getArea() {
    return (this.getWidth() * this.getHeight());
  }
  
  /**
   * getXValues
   * Finds the x-values of the rectangles's points
   * @return An array containing the x-values
   */
  public int[] getXValues() {
    int[] xValues = new int[4];
    double xDistance = this.getWidth()/2.0;
    xValues[0] = (int)(this.getCentreX() - xDistance);
    xValues[1] = (int)(this.getCentreX() + xDistance);
    xValues[2] = (int)(this.getCentreX() + xDistance);
    xValues[3] = (int)(this.getCentreX() - xDistance);
    return xValues;
  }

  /**
   * getYValues
   * Finds the y-values of the rectangles's points
   * @return An array containing the y-values
   */
  public int[] getYValues() {
    int[] yValues = new int[4];
    double yDistance = this.getHeight()/2.0;
    yValues[0] = (int)(this.getCentreY() - yDistance);
    yValues[1] = (int)(this.getCentreY() - yDistance);
    yValues[2] = (int)(this.getCentreY() + yDistance);
    yValues[3] = (int)(this.getCentreY() + yDistance);
    return yValues;
  }
}