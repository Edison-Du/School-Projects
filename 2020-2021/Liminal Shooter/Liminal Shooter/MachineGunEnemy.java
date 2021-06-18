/**
 * [MachineGunEnemy.java]
 * Represents an enemy that shoots rapidly but less accurately
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Graphics;
import java.awt.image.BufferedImage; 


class MachineGunEnemy extends Enemy {
  
  private static BufferedImage sprite;
  
  /**
   * MachineGunEnemy
   * @param x The enemy's x position
   * @param y The enemy's y position
   * @param width The enemy's width
   * @param height The enemy's height
   * @param health The enemy's health
   * @param speed The enemy's speed
   * @param range The enemy's shooting range
   * @param movementRange The enemy's movement range
   * @param fireRate The enemy's rate of fire
   * @param bulletSpeed The speed of the enemy's bullets
   */
  MachineGunEnemy (int x, int y, int width, int height, int health, int speed, int range, int movementRange, int fireRate, int bulletSpeed) {
    super(x, y, width, height, health, speed, range, movementRange, fireRate, bulletSpeed);
    this.setGun(new MachineGun(x-5, y-5, 60, 60, fireRate, bulletSpeed));
  }
  
  /**
   * loadSprite
   * Loads in the sprite for the enemy
   */
  public static void loadSprite() {
    sprite = loadSprite("Sprites/enemy-3.png");
  }
  
  /**
   * draw
   * Draws the enemy onto a graphics object
   * @param g The graphics object to draw on
   * @param xShift How much the x should be shifted when drawn
   * @param yShift How much the y should be shifted when drawn
   */
  public void draw (Graphics g, int xShift, int yShift) {
    g.drawImage(sprite, this.getX() + xShift, this.getY() + yShift, null);
  }
}