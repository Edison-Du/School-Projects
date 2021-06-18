/**
 * [BossPortal.java]
 * Represents a item that lets you enter a boss room
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Graphics;
import java.awt.image.BufferedImage; 


class BossPortal extends Item {
  
  private boolean unlocked;
  private static BufferedImage[] sprites = new BufferedImage[2];
  
  /**
   * BossPortal
   * @param x The boss portal's x position
   * @param y The boss portal's y position
   * @param width The boss portal's width
   * @param height The boss portal's height
   */
  BossPortal (int x, int y, int width, int height) {
    super(x, y, width, height);
    unlocked = false;
  }
  
  /**
   * unlock
   * Sets the portal to be unlocked
   */
  public void unlock() {
    this.unlocked = true;
  }
  
  /**
   * isOpen
   * Checks whether or not the portal is unlocked
   * @return True if it is unlocked, false otherwise
   */
  public boolean isOpen() {
    return this.unlocked;
  }
  
  /**
   * loadSprite
   * Loads in the sprite for the key
   */
  public static void loadSprites() {
    sprites[0] = loadSprite("Sprites/portal.png");
    sprites[1] = loadSprite("Sprites/portal_open.png");
  }
  
  /**
   * draw
   * Draws the boss portal onto a graphics object
   * @param g The graphics object to draw on
   * @param xShift How much the x should be shifted when drawn
   * @param yShift How much the y should be shifted when drawn
   */
  public void draw(Graphics g, int xShift, int yShift) {
    if (this.isOpen()) {
      g.drawImage(sprites[1], this.getX() + xShift, this.getY() + yShift - 100, null);
    } else {
      g.drawImage(sprites[0], this.getX() + xShift, this.getY() + yShift, null);
    }
  }
}