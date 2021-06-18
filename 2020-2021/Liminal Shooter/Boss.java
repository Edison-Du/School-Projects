/**
 * [Boss.java]
 * Represents a character with different projectile attack patterns
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Graphics;
import java.awt.image.BufferedImage; 

/******* Utility imports *******/
import java.util.ArrayList;
import java.util.Random;


class Boss extends Character {
  
  private static BufferedImage sprites[] = new BufferedImage[9];
  private int sprite = 0;
  private double exactSprite = 0;
  private Player player;
  private Room room;
  private Random random = new Random();
  
  /**
   * Boss
   * @param x The boss's x position
   * @param y The boss's y position
   * @param width The boss's width
   * @param height The boss's height
   * @param health The boss's health
   * @param speed The boss's speed
   * @param player The player the boss is targetting
   * @param room The room the boss is in
   */
  Boss (int x, int y, int width, int height, int health, int speed, Player player, Room room) {
    super(x, y, width, height, health, speed);
    this.player = player;
    this.room = room;
  }
  
  /**
   * loadSprites
   * Loads in the sprites for the boss
   */
  public static void loadSprites() {
    for (int i = 0; i < 9; i++) {
      sprites[i] = loadSprite("Sprites/boss-" + i + ".png");
    }
  }
  
  /**
   * move
   * Moves both the x and y value of the boss
   */
  public void move() {
    this.moveHorizontal();
    this.moveVertical();
  }
  
  /**
   * moveHorizontal
   * Moves the x value of the boss
   */
  public void moveHorizontal() {
    this.calculateDirections();
    this.setX(this.getX() + this.getXDirection());
  }
  
  /**
   * moveVertical
   * Moves the y value of the boss
   */
  public void moveVertical() {
    this.calculateDirections();
    this.setY(this.getY() + this.getYDirection());
  }
  
  /**
   * calculateDirections
   * Calculates how much the boss should be moving horizontally and vertically to reach the player
   */
  public void calculateDirections() {
    double angle = Math.atan2(player.getCenterY() - this.getCenterY(), player.getCenterX() - this.getCenterX());
    this.setXDirection((int) (Math.cos(angle) * this.getSpeed()));
    this.setYDirection((int) (Math.sin(angle) * this.getSpeed()));
    
    // Slow down diagonal movement so the distance travelled is the same
    if ( (this.getXDirection() != 0) && (this.getYDirection() != 0) ) {
      int newDirection = (int)(Math.sqrt(Math.pow(this.getSpeed(), 2) / 2.0) + 0.5);
      this.setXDirection( (this.getXDirection() < 0 ? -1 : 1) * newDirection);
      this.setYDirection( (this.getYDirection() < 0 ? -1 : 1) * newDirection);
    }
  }
  
  /**
   * useAttack
   * Uses the specified attack pattern
   * @param type The attack to be used
   * @param time The time the attack is used
   * @return True if the attack is still ongoing (dependent on time), false otherwise.
   */
  public boolean useAttack(int type, int time) {
    switch (type) {
      case 1:
        return this.singleDirectionAttack(time); 
      case 2:
        return this.eightDirectionAttack(time);
      case 3:
        return this.omnidirectionalAttack(time);
      case 4:
        return this.rotatingCrossAttack(time);
      case 5:
        return this.diamondAttack(time);
      case 6:
        return this.largeBulletAttack(time);
      default:
        return false;
    }
  }
  
  /**
   * singleDirectionAttack
   * Creates lines of bullets going from one end of the room to the other end
   * @param time The instant in time of the attack
   * @return True if the attack is ongoing, false otherwise
   */
  public boolean singleDirectionAttack(int time) {
    if (time >= 800) {
      return false;
    }
    if (time % 20 == 0) {
      
      // Create lines of bullets going from left to right
      if (time < 200) {
        for (int i = 85; i < room.getRows()*room.getChunkSize(); i+=100) {
          Projectile bullet = new Projectile(room.getChunkSize(),i,30,30,0,2,1);
          room.getEnemyBullets().add(bullet);
        }
      }
      
      // Create lines of bullets going from top to bottom
      if (time > 100 && time < 300) {
        for (int i = 85; i < room.getColumns()*room.getChunkSize(); i+=100) {
          Projectile bullet = new Projectile(i,room.getChunkSize(),30,30,Math.PI/2,2,1);
          room.getEnemyBullets().add(bullet);
        }
      }
    }
    return true;
  }  
  
  /**
   * eightDirectionAttack
   * Creates lines of bullets from the boss in 8 directions
   * @param time The instant in time of the attack
   * @return True if the attack is ongoing, false otherwise
   */ 
  public boolean eightDirectionAttack(int time) {
    if (time >= 80) {
      return false;
    }
    if (time % 5 == 0) {
      double angle = 0;
      for (int i = 0; i < 8; i++) {
        Projectile bullet = new Projectile(this.getCenterX()-15,this.getCenterY()-15,30,30,angle,5,1);
        room.getEnemyBullets().add(bullet);
        angle += Math.PI/4;
      }
    }
    return true;
  }
  
  /**
   * omnidirectionAttack
   * Shoots bullets from the boss randomly in all directions
   * @param time The instant in time of the attack
   * @return True if the attack is ongoing, false otherwise
   */ 
  public boolean omnidirectionalAttack(int time) {
    if (time >= 600) {
      return false;
    }
    if (time % 20 == 0) {
      double angle = random.nextDouble();
      for (int i = 0; i < 10; i++) {
        Projectile bullet = new Projectile(this.getCenterX()-15,this.getCenterY()-15,30,30,angle,3,1);
        room.getEnemyBullets().add(bullet);
        angle += Math.PI/5;
      }
    }
    return true;
  }
  
  /**
   * rotatingCrossAttack
   * Shoots bullets from the boss 4 directions, slowly rotating as time progresses
   * @param time The instant in time of the attack
   * @return True if the attack is ongoing, false otherwise
   */ 
  public boolean rotatingCrossAttack(int time) {
    if (time >= 600) {
      return false;
    }
    if (time % 10 == 0) {
      double angle = time/100.0;
      for (int i = 0; i < 4; i++) {
        Projectile bullet = new Projectile(this.getCenterX()-15,this.getCenterY()-15,30,30,angle,3,1);
        room.getEnemyBullets().add(bullet);
        angle += Math.PI/2;
      }
    }
    return true;
  }
  
  /**
   * diamondAttack
   * Shoots a large amount of bullets from the boss in the shape of a diamond
   * @param time The instant in time of the attack
   * @return True if the attack is ongoing, false otherwise
   */ 
  public boolean diamondAttack(int time) {
    if (time >= 150) {
      return false;
    }
    if (time % 50 == 0) {
      Projectile bullet;
      double angle = Math.atan2(player.getCenterY() - this.getCenterY(), player.getCenterX() - this.getCenterX());
      
      bullet = new Projectile(this.getCenterX()-15,this.getCenterY()-75,30,30,angle,3,1);
      room.getEnemyBullets().add(bullet);
      bullet = new Projectile(this.getCenterX()-15,this.getCenterY()+45,30,30,angle,3,1);
      room.getEnemyBullets().add(bullet);
      
      for (int i = -45; i < 45; i+=30) {
        bullet = new Projectile(this.getCenterX()+i,this.getCenterY()-45,30,30,angle,3,1);
        room.getEnemyBullets().add(bullet);
        bullet = new Projectile(this.getCenterX()+i,this.getCenterY()+15,30,30,angle,3,1);
        room.getEnemyBullets().add(bullet);
      }
      for (int i = -75; i < 75; i+=30) {
        bullet = new Projectile(this.getCenterX()+i,this.getCenterY()-15,30,30,angle,3,1);
        room.getEnemyBullets().add(bullet);
      } 
    }
    return true;
  }
  
  /**
   * largeBulletAttack
   * Shoots large and fast explosive projectile from the boss
   * @param time The instant in time of the attack
   * @return True if the attack is ongoing, false otherwise
   */ 
  public boolean largeBulletAttack(int time) {
    if (time >= 100) {
      return false;
    }
    if (time == 0) {
      double angle = Math.atan2(player.getCenterY() - this.getCenterY(), player.getCenterX() - this.getCenterX());
      Projectile bullet = new ExplosiveProjectile(this.getCenterX()-40,this.getCenterY()-40,80,80,angle,6,1);
      room.getEnemyBullets().add(bullet);
    }
    return true;
  }
  
  /**
   * setSprite
   * Changes the sprite that the boss is drawn with
   * @param sprite The sprite to change to
   * @return True if the sprite is valid, false otherwise
   */
  public boolean setSprite(int sprite) {
    if ( (sprite >= 0) && (sprite < 9) ) {
      this.sprite = sprite;
      this.exactSprite = sprite;
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * animateSprite
   * Changes the sprite to create the illusion of animation
   */
  public void animateSprite() {
    this.exactSprite = (exactSprite+0.1)%9;
    this.sprite = (int) this.exactSprite;
  }
  
  /**
   * draw
   * Draws the boss onto a graphics object
   * @param g The graphics object to draw on
   * @param xShift How much the x should be shifted when drawn
   * @param yShift How much the y should be shifted when drawn
   */
  public void draw (Graphics g, int xShift, int yShift) {
    g.drawImage(sprites[sprite], this.getX() + xShift - 10, this.getY() + yShift - 16, null);
  }
}