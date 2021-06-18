/**
 * [Rhombus.java]
 * Represents a rhombus, a quadrilateral with diagonals that align to the x and y axis.
 * @author Edison Du
 * @version 1.0 May 12, 2021
 */

class Rhombus extends Quadrilateral {
  
  /**
   * Rhombus
   * @param centreX The x-coordinate of the rhombus's centre
   * @param centreY The y-coordinate of the rhombus's centre
   * @param redValue The red value of the rhombus's colour
   * @param greenValue The green value of the rhombus's colour
   * @param blueValue The blue value of the rhombus's colour
   * @param width The width of the rhombus
   * @param height The height of the rhombus
   */
  Rhombus(int centreX, int centreY, int redValue, int greenValue, int blueValue, int width, int height) {
    super(centreX, centreY, redValue, greenValue, blueValue, width, height);
  }
  
  /**
   * getPerimeter
   * Calculates the perimeter of the rhombus
   * @return The perimeter of the rhombus
   */
  public double getPerimeter() {
    double side = Math.sqrt(Math.pow(this.getWidth()/2.0, 2.0) + Math.pow(this.getHeight()/2.0, 2.0));
    return (side * 4);
  }
  
  /**
   * getArea
   * Calculates the area of the rhombus
   * @return The area of the rhombus
   */
  public double getArea() {
    return ((this.getWidth() * this.getHeight()) / 2.0);
  }
  
  /**
   * getXValues
   * Finds the x-values of the rhombus's points
   * @return An array containing the x-values
   */
  public int[] getXValues() {
    int[] xValues = new int[4];
    double xDistance = this.getWidth()/2.0;
    xValues[0] = this.getCentreX();
    xValues[1] = (int)(this.getCentreX()+xDistance);
    xValues[2] = this.getCentreX();
    xValues[3] = (int)(this.getCentreX()-xDistance);
    return xValues;
  }

  /**
   * getYValues
   * Finds the y-values of the rhombus's points
   * @return An array containing the y-values
   */
  public int[] getYValues() {
    int[] yValues = new int[4];
    double yDistance = this.getHeight()/2.0;
    yValues[0] = (int)(this.getCentreY()-yDistance);
    yValues[1] = this.getCentreY();
    yValues[2] = (int)(this.getCentreY()+yDistance);
    yValues[3] = this.getCentreY();
    return yValues;
  }
}
