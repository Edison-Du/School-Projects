/**
 * [BasicGun.java]
 * Represents a gun that only shoots one bullet at a time
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Graphics;
import java.awt.image.BufferedImage; 

/******* Utility imports *******/
import java.util.ArrayList;


class BasicGun extends Gun {
  
  private static BufferedImage sprites[] = new BufferedImage[9];
  private int sprite = 0;
  
  /**
   * BasicGun
   * @param x The gun's x position
   * @param y The gun's y position
   * @param width The gun's width
   * @param height The gun's height
   * @param fireRate The gun's rate of fire
   * @param bulletSpeed The speed of the gun's bullets
   */
  BasicGun (int x, int y, int width, int height, int fireRate, int bulletSpeed) {
    super(x, y, width, height, fireRate, bulletSpeed);
  }
  
  /**
   * shoot
   * Creates a projectile and adds it to an ArrayList of projectiles
   * @param projectileList The ArrayList to add to
   * @return True if the gun's cooldown is ready, false otherwise
   */
  public boolean shoot(ArrayList<Projectile> projectileList) {
    if (this.getFireCooldown() <= 0) {
      projectileList.add(new Projectile(this.getX()+20,this.getY()+20,20,20,this.getTargetX()-10,this.getTargetY()-10,this.getBulletSpeed(),10));
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
    for (int i = 0; i < 9; i++) {
      sprites[i] = loadSprite("Sprites/gun_basic-" + i + ".png");
    }
  }
  
  /**
   * setSprite
   * Changes the sprite that the gun is drawn with
   * @param sprite The sprite to change to
   * @return True if the sprite is valid, false otherwise
   */
  public boolean setSprite(int sprite) {
    if ( (sprite >= 0) && (sprite < 9) ) {
      this.sprite = sprite;
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * calculateSprite
   * Determines the sprite to be drawn depending on which direction the gun is shooting
   */
  public void calculateSprite() { 
    int drawAngle = 0;
    double angle = Math.atan2(this.getTargetY() - this.getY(), this.getTargetX() - this.getX());
    double closestAngle = -1;
    if (angle < 0) {
      angle = Math.PI + angle + Math.PI;
    }
    
    // There are only 8 sprites, so the one closest to the actual angle is chosen
    for (int i = 0; i < 8; i++) {
      double currentAngle = Math.PI * (i/4.0);
      double difference = Math.abs(angle-currentAngle);
      if ( (difference <= closestAngle) || (closestAngle == -1) ) {
        closestAngle = difference;
        drawAngle = i;
      }
    }
    this.sprite = drawAngle;
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