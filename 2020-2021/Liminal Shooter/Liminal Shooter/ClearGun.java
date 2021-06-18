/**
 * [ClearGun.java]
 * Represents a gun that shoots in a circle
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Graphics;
import java.awt.image.BufferedImage; 

/******* Utility imports *******/
import java.util.ArrayList;


class ClearGun extends Gun {
  
  private static BufferedImage sprites[] = new BufferedImage[5];
  private double exactSprite = 0;
  private int sprite = 0;
  private int amountOfBullets;
  
  /**
   * ClearGun
   * @param x The gun's x position
   * @param y The gun's y position
   * @param width The gun's width
   * @param height The gun's height
   * @param fireRate The gun's rate of fire
   * @param bulletSpeed The speed of the gun's bullets
   * @param amountOfBullets The number of bullets to shoot at once
   */
  ClearGun (int x, int y, int width, int height, int fireRate, int bulletSpeed, int amountOfBullets) {
    super(x, y, width, height, fireRate, bulletSpeed);
    this.amountOfBullets = amountOfBullets;
  }
  
  /**
   * shoot
   * Creates projectiles distributed evenly in a circle and adds them to an ArrayList of projectiles
   * @param projectileList The ArrayList to add to
   * @return True if the gun's cooldown is ready, false otherwise
   */
  public boolean shoot(ArrayList<Projectile> projectileList) {
    if (this.getFireCooldown() <= 0) {
      Projectile directBullet;
      double angle=Math.atan2(this.getTargetY()-this.getCenterY()-10, this.getTargetX()-this.getCenterX()-10);
      
      for (int i = 0; i < amountOfBullets; i++) {
        angle += (Math.PI)/(amountOfBullets/2);
        projectileList.add(new Projectile(this.getCenterX(),this.getCenterY(),20,20,angle,this.getBulletSpeed(),10));
      }
      this.setFireCooldown(this.getFireRate());
      return true;
      
    } else {
      return false;
    }
  }
  
  /**
   * loadSprites
   * Loads in all the gun's images
   */
  public static void loadSprites() {
    for (int i = 0; i < 5; i++) {
      sprites[i] = loadSprite("Sprites/gun_clear-" + i + ".png");
    }
  }
  
  /**
   * setSprite
   * Changes the sprite that the gun is drawn with
   * @param sprite The sprite to change to
   * @return True if the sprite is valid, false otherwise
   */
  public boolean setSprite(int sprite) {
    if ( (sprite >= 0) && (sprite < 5) ) {
      this.sprite = sprite;
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * calculateSprite
   * Changes the sprite to create the illusion of a rotating gun animation
   */
  public void calculateSprite() {
    this.exactSprite = (exactSprite+0.1)%4;
    this.sprite = (int) this.exactSprite;
  }
  
  /**
   * draw
   * Draws the gun onto a graphics object
   * @param g The graphics object to draw on
   * @param xShift How much the x should be shifted when drawn
   * @param yShift How much the y should be shifted when drawn
   */
  public void draw (Graphics g, int xShift, int yShift) {
    g.drawImage(sprites[sprite], this.getX()+xShift, this.getY()+yShift, null);
  }
}