/**
 * [Player.java]
 * Represents a player character
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Graphics;
import java.awt.image.BufferedImage; 


class Player extends Character {
  
  private int invincibleTimer;
  private int keys;
  private boolean movingLeft, movingRight, movingUp, movingDown;
  private boolean isShooting;
  private Gun gun;
  private static BufferedImage sprite;
  
  /**
   * Player
   * @param x The player's x position
   * @param y The player's y position
   * @param width The player's width
   * @param height The player's height
   * @param health The player's health
   * @param speed The player's speed
   */
  Player(int x, int y, int width, int height, int health, int speed) {
    super(x, y, width, height, health, speed);
    this.movingLeft = false;
    this.movingRight = false;
    this.movingUp = false;
    this.movingDown = false;
    this.isShooting = false;
    this.keys = 0;
    this.gun = new BasicGun (x-5, y-5, 60, 60, 30, 7);
  }
  
  /**
   * loadSprite
   * Loads in the sprite for the player
   */
  public static void loadSprite() {
    sprite = loadSprite("Sprites/player.png");
  }
  
  /**
   * move
   * Moves both the x and y value of the player and the player's gun
   */
  public void move() {
    this.moveHorizontal();
    this.moveVertical();
  }
  
  /**
   * moveVertical
   * Moves the y value of the player and the player's gun
   */
  public void moveVertical() {
    this.calculateDirections();
    this.setY(this.getY() + this.getYDirection());
    this.gun.setPosition(this.getX()-5, this.getY()-5);
  }
  
  /**
   * moveHorizontal
   * Moves the x value of the player and the player's gun
   */
  public void moveHorizontal() {
    this.calculateDirections();
    this.setX(this.getX() + this.getXDirection());
    this.gun.setPosition(this.getX()-5, this.getY()-5);
  }
  
  /**
   * calculateDirections
   * Calculates how much the player should be moving horizontally and vertically
   */
  public void calculateDirections() {
    
    // Horizontal direction
    if (this.movingLeft ^ this.movingRight) {
      this.setXDirection((movingLeft ? -1 : 1) * this.getSpeed());
    } else {
      this.setXDirection(0);
    }
    
    // Vertical direction
    if (this.movingUp ^ this.movingDown) {
      this.setYDirection((movingUp ? -1 : 1) * this.getSpeed());
    } else {
      this.setYDirection(0);
    }
    
    // Reduce diagonal movement so the speed is consistent
    if ( (this.getXDirection() != 0) && (this.getYDirection() != 0) ) {
      int newDirection = (int)(Math.sqrt(Math.pow(this.getSpeed(), 2) / 2.0) + 0.5);
      this.setXDirection( (this.getXDirection() < 0 ? -1 : 1) * newDirection);
      this.setYDirection( (this.getYDirection() < 0 ? -1 : 1) * newDirection);
    }
  }
  
 /**
  * getKeys
  * Getter for how many keys the player has
  * @return The amount of keys
  */
  public int getKeys() {
    return this.keys;
  }
  
 /**
  * changeKeys
  * Adds an amount to the player's key count
  * @param change The amount to add
  */
  public void changeKeys(int change) {
    this.keys = Math.max(0, this.keys + change);
  }
  
  /**
   * isShooting
   * Checks if the player is shooting
   * @return True if the player is shooting, false otherwise
   */
  public boolean isShooting() {
    return this.isShooting;
  }
  
  /**
   * setShooting
   * Change whether or not the player is shooting
   * @param shooting Whether or not the player is shooting
   */
  public void setShooting(boolean shooting) {
    this.isShooting = shooting;
  }
  
  /**
   * isInvincible
   * Checks if the player is invincible
   * @return True if the player is invincible, false otherwise
   */
  public boolean isInvincible() {
    return this.invincibleTimer != 0;
  }
  
  /**
   * setInvincibility
   * Changes the player's invincibility timer 
   * @param value The invincibility timer to change to
   * @return True if the value is valid, false otherwise
   */
  public boolean setInvincibility(int value) {
    if (value < 0) {
      return false;
    }
    this.invincibleTimer = Math.max(value, 0);
    return true;
  }
  
  /**
   * reduceInvincibleTimer
   * Decrements the player's invincibility timer if it is greater than 0
   */
  public void reduceInvincibleTimer() {
    if (this.invincibleTimer > 0) {
      this.invincibleTimer--;
    }
  }
  
  /**
   * getGun
   * Getter for the player's gun
   * @return The player's gun
   */
  public Gun getGun() {
    return this.gun;
  }
  
  /**
   * setGun
   * Changes the player's gun
   * @param gun The gun to change to
   */
  public void setGun(Gun gun) {
    this.gun = gun;
  }

  /**
   * setMovingLeft
   * Change whether or not the player is moving left
   * @param moving Whether or not the player is moving left
   */
  public void setMovingLeft(boolean moving) {
    this.movingLeft = moving;
  }
  
  /**
   * setMovingRight
   * Change whether or not the player is moving right
   * @param moving Whether or not the player is moving right
   */
  public void setMovingRight(boolean moving) {
    this.movingRight = moving;
  }
  
  /**
   * setMovingUp
   * Change whether or not the player is moving up
   * @param moving Whether or not the player is moving up
   */
  public void setMovingUp(boolean moving) {
    this.movingUp = moving;
  }
  
  /**
   * setMovingDown
   * Change whether or not the player is moving down
   * @param moving Whether or not the player is moving down
   */
  public void setMovingDown(boolean moving) {
    this.movingDown = moving;
  }
  
  /**
   * draw
   * Draws the player onto a graphics object
   * @param g The graphics object to draw on
   * @param xShift How much the x should be shifted when drawn
   * @param yShift How much the y should be shifted when drawn
   */
  public void draw (Graphics g, int xShift, int yShift) {
    g.drawImage(sprite, this.getX() + xShift, this.getY() + yShift, null);
  }
}