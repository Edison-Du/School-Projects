/**
 * [Projectile.java]
 * Represents an object that continuously moves in a certain direction
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Graphics;
import java.awt.image.BufferedImage; 


class Projectile extends GameObject implements Moveable{
  
  private int speed;
  private int damage;
  private double angle;
  private double exactX, exactY;
  private double xDirection, yDirection;
  private int currentSprite;
  private static BufferedImage[] sprites = new BufferedImage[3];

  /**
   * Projectile
   * @param x The projectile's x position
   * @param y The projectile's y position
   * @param width The projectile's width
   * @param height The projectile's height
   * @param targetX The x position of the projectile's target
   * @param targetY The y position of the projectile's target
   * @param speed The projectile's speed
   * @param damage The projectile's damage
   */
  Projectile(int x, int y, int width, int height, int targetX, int targetY, int speed, int damage) {
    super(x, y, width, height);
    this.exactX = x;
    this.exactY = y;
    this.speed = speed;
    this.damage = damage;
    
    this.angle = Math.atan2(targetY - y, targetX - x);
    this.xDirection = Math.cos(angle) * speed;
    this.yDirection = Math.sin(angle) * speed;
  }
  
  /**
   * Projectile
   * @param x The projectile's x position
   * @param y The projectile's y position
   * @param width The projectile's width
   * @param height The projectile's height
   * @param angle The angle the projectile moves in
   * @param speed The projectile's speed
   * @param damage The projectile's damage
   */
  Projectile(int x, int y, int width, int height, double angle, int speed, int damage) {
    super(x, y, width, height);
    this.exactX = x;
    this.exactY = y;
    this.speed = speed;
    this.damage = damage;
    
    this.angle = angle;
    this.xDirection = Math.cos(angle) * speed;
    this.yDirection = Math.sin(angle) * speed;
  }
  
  /**
   * loadSprites
   * Loads in the sprites for the projectile
   */
  public static void loadSprites() {
    sprites[0] = loadSprite("Sprites/bullet_player.png");
    sprites[1] = loadSprite("Sprites/bullet_enemy.png");
    sprites[2] = loadSprite("Sprites/bullet_enemy_large.png");
  }
  
  /**
   * move
   * Moves both the x and y value of the projectile
   */
  public void move() {
    this.moveVertical();
    this.moveHorizontal();
  }
  
  /**
   * moveVertical
   * Moves the y value of the projectile
   */
  public void moveVertical() {
    this.exactY += this.yDirection;
    this.setY((int)this.exactY);
  }
  
  /**
   * moveHorizontal
   * Moves the x value of the projectile
   */
  public void moveHorizontal() {
    this.exactX += this.xDirection;
    this.setX((int)this.exactX);
  }
  
  /**
   * getDamage
   * Getter for how much damage the projectile does
   * @return The damage
   */
  public int getDamage() {
    return this.damage;
  }
  
  /**
   * getSpeed
   * Getter for the projectile's speed
   * @return The speed
   */
  public int getSpeed() {
    return this.speed;
  }
  
  /**
   * getAngle
   * Getter for the projectile's movement angle
   * @return The movement angle
   */
  public double getAngle() {
    return this.angle;
  }
  
  /**
   * setSprite
   * Changes the sprite that the gun is drawn with
   * @param sprite The sprite to change to
   * @return True if the sprite is valid, false otherwise
   */
  public boolean setSprite(int sprite) {
    if ( (sprite >= 0) && (sprite < 3) ) {
      this.currentSprite = sprite;
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * draw
   * Draws the projectile onto a graphics object
   * @param g The graphics object to draw on
   * @param xShift How much the x should be shifted when drawn
   * @param yShift How much the y should be shifted when drawn
   */
  public void draw (Graphics g, int xShift, int yShift) {
    g.drawImage(this.sprites[currentSprite], this.getX() + xShift, this.getY() + yShift, null);
  }
}