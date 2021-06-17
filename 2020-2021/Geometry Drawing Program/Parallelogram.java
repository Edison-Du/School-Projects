/**
 * [Parallelogram.java]
 * Represents a parallelogram with an acute angle of 45 degrees.
 * @author Edison Du
 * @version 1.0 May 12, 2021
 */

class Parallelogram extends Quadrilateral {
  
  /**
   * Parallogram
   * @param centreX The x-coordinate of the parallelogram's centre
   * @param centreY The y-coordinate of the parallelogram's centre
   * @param redValue The red value of the parallelogram's colour
   * @param greenValue The green value of the parallelogram's colour
   * @param blueValue The blue value of the parallelogram's colour
   * @param width The base (not full horizontal extent) of the parallelogram
   * @param height The height of the parallelogram
   */
  Parallelogram(int centreX, int centreY, int redValue, int greenValue, int blueValue, int width, int height) {
    super(centreX, centreY, redValue, greenValue, blueValue, width, height);
  }
  
  /**
   * getPerimeter
   * Calculates the perimeter of parallelogram, using pythagorean to find the side lengths
   * @return The perimeter of the parallelogram
   */
  public double getPerimeter() {
    double side = Math.sqrt(Math.pow(this.getHeight(),2.0)*2.0);
    return ((this.getWidth()+side)*2);
  }
  
  /**
   * getArea 
   * Calculates the area of the parallologram
   * @return The area of the parallelogram
   */
  public double getArea() {
    return (this.getWidth() * this.getHeight());
  }
  
  /**
   * getXValues
   * Finds the x-values of the parallelogram's points
   * @return An array containing the x-values
   */
  public int[] getXValues() {
    int[] xValues = new int[4];
    double xDistance = (this.getWidth() - this.getHeight())/2.0;
    xValues[0] = (int)(this.getCentreX() - xDistance);
    xValues[1] = (int)(this.getCentreX() + xDistance + this.getHeight());
    xValues[2] = (int)(this.getCentreX() + xDistance);
    xValues[3] = (int)(this.getCentreX() - xDistance - this.getHeight());
    return xValues;
  }
  
  /**
   * getYValues
   * Finds the y-values of the parallelogram's points
   * @return An array containing the y-values
   */
  public int[] getYValues() {
    int[] yValues = new int[4];
    yValues[0] = (int)(this.getCentreY() - this.getHeight()/2.0);
    yValues[1] = (int)(this.getCentreY() - this.getHeight()/2.0);
    yValues[2] = (int)(this.getCentreY() + this.getHeight()/2.0);
    yValues[3] = (int)(this.getCentreY() + this.getHeight()/2.0);
    return yValues;
  }
}