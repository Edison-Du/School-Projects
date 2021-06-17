import java.awt.Graphics;

/**
 * [Drawable.java]
 * An interface that allows objects to be drawn.
 * @author Edison Du
 * @version 1.0 May 12, 2021
 */

public interface Drawable {
  
  /**
   * draw
   * Draws this shape onto a graphics object
   * @param g The graphics object to draw on
   */
  public void draw(Graphics g);
}