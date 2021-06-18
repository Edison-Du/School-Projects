/**
 * [ExplosiveProjectile.java]
 * Represents a projectile that can explode into smaller projectiles
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Graphics;
import java.awt.image.BufferedImage; 

/******* Utility imports *******/
import java.util.ArrayList;


class ExplosiveProjectile extends Projectile {
  
  private static BufferedImage sprite;
  
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
  ExplosiveProjectile(int x, int y, int width, int height, int targetX, int targetY, int speed, int damage) {
    super(x, y, width, height, targetX, targetY, speed, damage);
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
  ExplosiveProjectile(int x, int y, int width, int height, double angle, int speed, int damage) {
    super(x, y, width, height, angle, speed, damage);
  }
  
  /**
   * loadSprite
   * Loads in the sprite for the projectile
   */
  public static void loadSprite() {
    sprite = loadSprite("Sprites/bullet_enemy_explosive.png");
  }
  
  /**
   * explode
   * Creates 8 new projectiles moving in all directions and adds them to an ArrayList of projectiles
   * @param projectileList The ArrayList to add to
   */
  public void explode (ArrayList<Projectile> projectileList) {
    double angle = 0;
    for (int i = 0; i < 8; i++) {
      Projectile bullet = new Projectile(this.getCenterX()-15,this.getCenterY()-15,30,30,angle,this.getSpeed(),this.getDamage());
      projectileList.add(bullet);
      angle += Math.PI/4;
    }
  }
  
  /**
   * draw
   * Draws the projectile onto a graphics object
   * @param g The graphics object to draw on
   * @param xShift How much the x should be shifted when drawn
   * @param yShift How much the y should be shifted when drawn
   */
  @Override
  public void draw(Graphics g, int xShift, int yShift) {
    g.drawImage(sprite,this.getX() + xShift, this.getY() + yShift, null);
  }
}