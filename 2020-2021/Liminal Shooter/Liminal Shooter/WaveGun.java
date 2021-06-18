/**
 * [WaveGun.java]
 * Represents a gun that shoots many bullets in a direction
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Graphics;
import java.awt.image.BufferedImage; 

/******* Utility *******/
import java.util.ArrayList;
import java.util.Random;


class WaveGun extends Gun {
  
  private static BufferedImage sprites[] = new BufferedImage[9];
  private int sprite = 0;
  private int amountOfBullets;
  
  /**
   * WaveGun
   * @param x The gun's x position
   * @param y The gun's y position
   * @param width The gun's width
   * @param height The gun's height
   * @param fireRate The gun's rate of fire
   * @param bulletSpeed The speed of the gun's bullets
   * @param amountOfBullets The number of bullets to shoot at once
   */
  WaveGun (int x, int y, int width, int height, int fireRate, int bulletSpeed, int amountOfBullets) {
    super(x, y, width, height, fireRate, bulletSpeed);
    this.amountOfBullets = amountOfBullets;
  }
  
  /**
   * shoot
   * Creates a large amount of projectiles that move in the same direction and adds them to an ArrayList of projectiles
   * @param projectileList The ArrayList to add to
   * @return True if the gun's cooldown is ready, false otherwise
   */
  public boolean shoot(ArrayList<Projectile> projectileList) {
    if (this.getFireCooldown() <= 0) {
      double angle=Math.atan2(this.getTargetY()-this.getCenterY()-10, this.getTargetX()-this.getCenterX()-10);
      Random random = new Random();
      
      for (int i = 0; i < this.amountOfBullets; i++) {
        int newX = this.getCenterX() + random.nextInt(50) * (random.nextInt(2) == 1 ? 1 : -1);
        int newY = this.getCenterY() + random.nextInt(50) * (random.nextInt(2) == 1 ? 1 : -1);
        projectileList.add(new Projectile(newX,newY,20,20,angle,this.getBulletSpeed(),5));
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
    for (int i = 0; i < 9; i++) {
      sprites[i] = loadSprite("Sprites/gun_wave-" + i + ".png");
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