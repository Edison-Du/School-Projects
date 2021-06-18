/**
 * [Character.java]
 * Represents object that has health and can move
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

abstract class Character extends GameObject implements Moveable {
  
  private int health;
  private int xDirection, yDirection;
  private int speed;
  
  /**
   * Character
   * @param x The character's x position
   * @param y The character's y position
   * @param width The character's width
   * @param height The character's height
   * @param health The character's health
   * @param speed The character's speed
   */
  Character (int x, int y, int width, int height, int health, int speed) {
    super(x, y, width, height);
    this.health = health;
    this.speed = speed;
    this.xDirection = 0;
    this.yDirection = 0;
  }
  
  /**
   * getHealth
   * Getter for the character's health
   * @return The health
   */
  public int getHealth() {
    return this.health;
  }
  
  /**
   * getSpeed
   * Getter for the character's speed
   * @return The speed
   */
  public int getSpeed() {
    return this.speed;
  }
  
  /**
   * getXDirection
   * Getter for the character's x-direction
   * @return The x-direction
   */
  public int getXDirection() {
    return this.xDirection;
  }
  
  /**
   * getYDirection
   * Getter for the character's y-direction
   * @return The y-direction
   */
  public int getYDirection() {
    return this.yDirection;
  }
  
  /**
   * changeHealth
   * Adds a certain amount to the character's health
   * @param change The amount to add
   */
  public void changeHealth(int change) {
    this.health += change;
    this.health = Math.max(0,this.health);
  }
  
  /**
   * setSpeed
   * Changes the character's speed
   * @param speed The speed to change to
   * @return True if the speed is valid, false otherwise
   */
  public boolean setSpeed(int speed) {
    if (speed < 0) {
      return false;
    }
    this.speed = speed;
    return true;
  }
  
  /**
   * setXDirection
   * Changes the character's x-direction
   * @param xDirection The x-direction to change to
   */
  public void setXDirection(int xDirection) {
    this.xDirection = xDirection;
  }
  
  /**
   * setYDirection
   * Changes the character's y-direction
   * @param yDirection The y-direction to change to
   */
  public void setYDirection(int yDirection) {
    this.yDirection = yDirection;
  }
}