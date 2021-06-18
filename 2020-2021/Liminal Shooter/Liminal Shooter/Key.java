/**
 * [Key.java]
 * Represents a key item
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Graphics;
import java.awt.image.BufferedImage; 


class Key extends Item {
  
  private static BufferedImage sprite;
  
  /**
   * Key
   * @param x The key's x position
   * @param y The key's y position
   * @param width The key's width
   * @param height The key's height
   */
  Key (int x, int y, int width, int height) {
    super(x, y, width, height);
  }
  
  /**
   * loadSprite
   * Loads in the sprite for the key
   */
  public static void loadSprite() {
    sprite = loadSprite("Sprites/key.png");
  }
  
  /**
   * draw
   * Draws the key onto a graphics object
   * @param g The graphics object to draw on
   * @param xShift How much the x should be shifted when drawn
   * @param yShift How much the y should be shifted when drawn
   */
  public void draw(Graphics g, int xShift, int yShift) {
    g.drawImage(sprite, this.getX() + xShift, this.getY() + yShift, null);
  }
}