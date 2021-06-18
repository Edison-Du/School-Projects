/**
 * [Moveable.java]
 * An interface that allows objects to be moved
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

public interface Moveable {
  
  /**
   * move
   * Moves both the x and y value of the object
   */
  public void move();
  
  /**
   * moveHorizontal
   * Moves the x value of the object
   */
  public void moveHorizontal();
  
  /**
   * moveVertical
   * Moves the y value of the object
   */
  public void moveVertical();
}