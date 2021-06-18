/**
 * [Gun.java]
 * Represents an object that can shoot projectiles
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Utility imports *******/
import java.util.ArrayList;


abstract class Gun extends Item {
  
  private int targetX, targetY;
  private int fireRate;
  private int fireCooldown;
  private int bulletSpeed;
  
  /**
   * Gun
   * @param x The gun's x position
   * @param y The gun's y position
   * @param width The gun's width
   * @param height The gun's height
   * @param fireRate The gun's rate of fire
   * @param bulletSpeed The speed of the gun's bullets
   */
  Gun (int x, int y, int width, int height, int fireRate, int bulletSpeed) {
    super(x, y, width, height);
    this.fireRate = fireRate;
    this.bulletSpeed = bulletSpeed;
    this.fireCooldown = fireRate;
    this.targetX = 0;
    this.targetY = 0;
  }
  
  /**
   * shoot
   * Creates projectiles and adds them to an ArrayList of projectiles
   * @param projectileList The ArrayList to add to
   * @return True if the gun can shoot, false otherwise
   */
  public abstract boolean shoot(ArrayList<Projectile> projectileList);
  
  /**
   * getTargetX
   * Getter for the x position of the gun's target
   * @return The x position of the gun's target
   */
  public int getTargetX() {
    return this.targetX;
  }
  
  /**
   * getTargetY
   * Getter for the y position of the gun's target
   * @return The y position of the gun's target
   */
  public int getTargetY() {
    return this.targetY;
  }
  
  /**
   * getFireRate
   * Getter for the gun's rate of fire
   * @return The rate of fire
   */
  public int getFireRate() {
    return this.fireRate;
  }
  
  /**
   * getFireCooldown
   * Getter for the gun's fire cooldown
   * @return The fire cooldown
   */
  public int getFireCooldown() {
    return this.fireCooldown;
  }
  
  /**
   * getBulletSpeed
   * Getter for the gun's bullet speed
   * @return The bullet speed
   */
  public int getBulletSpeed() {
    return this.bulletSpeed;
  }
  
  /**
   * setTarget
   * Changes the gun's target position
   * @param x The x position to change to
   * @param y The y position to change to
   */
  public void setTarget(int x, int y) {
    this.targetX = x;
    this.targetY = y;
  }
  
  /**
   * setFireCooldown
   * Changes the gun's fire cooldown
   * @param cooldown The cooldown to change to
   */
  public void setFireCooldown(int cooldown) {
    this.fireCooldown = cooldown;
  }
  
  /**
   * reduceFireCooldown
   * Decrements the gun's fire cooldown if it is greater than 0
   */
  public void reduceFireCooldown() {
    if (this.fireCooldown > 0) {
      this.fireCooldown--;
    }
  }
}