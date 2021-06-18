/**
 * [Room.java]
 * Represents a room that contains walls, enemies and projectiles
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

/******* Input and Output imports *******/
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

/******* Utility imports *******/
import java.util.ArrayList;


class Room implements Drawable {
  
  private int rows, columns;
  private int chunkSize;
  private int[][] layout;
  
  private ArrayList<Rectangle> walls = new ArrayList<>();;
  private ArrayList<Enemy> enemies = new ArrayList<>();;
  private ArrayList<Projectile> playerBullets = new ArrayList<>();;
  private ArrayList<Projectile> enemyBullets = new ArrayList<>();;
  
  private Item item;
  
  private static BufferedImage floorSprite, largeFloorSprite;
  private static BufferedImage[] wallSprites = new BufferedImage[16];
  
  /**
   * Room
   * @param rows The number of rows in the room
   * @param columns The number of columns in the room
   * @param chunkSize The size of each row and column in pixels
   */
  Room (int rows, int columns, int chunkSize) {
    this.rows = Math.max(rows,4);
    this.columns = Math.max(columns,4);
    this.chunkSize = chunkSize;
    this.layout = new int[rows][columns];
  }
  
  /**
   * loadSprites
   * Loads in the sprites for the room
   */
  public static void loadSprites() {
    floorSprite = loadSprite("Sprites/floor.png");
    largeFloorSprite = loadSprite("Sprites/floor_large.png");
    for (int i = 0; i < 16; i++) {
      wallSprites[i] = loadSprite("Sprites/wall-" + i + ".png");
    }
  }
  
  /**
   * addEnemy
   * Adds an enemy to the room
   * @param type The type of enemy
   * @param row The row of the enemy
   * @param column The column of the enemy
   * @param health The enemy's health
   * @param speed The enemy's speed
   * @param range The enemy's range
   * @param movementRange The enemy's movement range
   * @param fireRate The enemy's rate of fire
   * @param bulletSpeed The speed of the enemy's bullets
   * @return True if the enemy was successfully added, false otherwise
   */
  public boolean addEnemy(int type, int row, int column, int health, int speed, int range, int movementRange, int fireRate, int bulletSpeed) {
    
    // Do not add the enemy if it is out of bounds or if it will be on a wall
    if ( (row < 0) || (column < 0) || (row >= rows) || (column >= columns) ) {
      return false;
    }
    if (this.layout[row][column] == 1) {
      return false;
    }
    switch(type) {
      case 1:
        enemies.add(new BasicEnemy(column*chunkSize,row*chunkSize,50,50,health,speed,range,movementRange,fireRate,bulletSpeed));
        break;
      case 2:
        enemies.add(new ShotgunEnemy(column*chunkSize,row*chunkSize,50,50,health,speed,range,movementRange,fireRate,bulletSpeed));
        break;
      case 3:
        enemies.add(new MachineGunEnemy(column*chunkSize,row*chunkSize,50,50,health,speed,range,movementRange,fireRate,bulletSpeed));
        break;
      case 4:
        enemies.add(new ClearGunEnemy(column*chunkSize,row*chunkSize,50,50,health,speed,range,movementRange,fireRate,bulletSpeed));
        break;
      case 5:
        enemies.add(new WaveGunEnemy(column*chunkSize,row*chunkSize,50,50,health,speed,range,movementRange,fireRate,bulletSpeed));
        break;
      default:
        return false;
    }
    return true;
  }
  
  /**
   * addWall
   * Turn a rectangular portion of the room layout into a wall
   * @param row The starting row of the wall
   * @param column The starting column of the wall
   * @param height The height of the wall in rows
   * @param width The width of the wall in columns
   * @return True if the wall was succesfully added, false otherwise
   */
  public boolean addWall(int row, int column, int height, int width) {
    
    // Check if the wall is inside the bounds
    if ( (row < 0) || (column < 0) || (row + height > rows) || (column + width > columns) ) {
      return false;
    }
    for (int i = row; i < row + height; i++) {
      for (int j = column; j < column + width; j++) {
        this.layout[i][j] = 1;
      }
    }
    return true;
  }
  
  /**
   * clearWall
   * Turn a rectangular portion of the room layout into open space
   * @param row The starting row of the open space
   * @param column The starting column of the open space
   * @param height The height of the open space in rows
   * @param width The width of the open space in columns
   * @return True if the open space was succesfully added, false otherwise
   */
  public boolean clearWall(int row, int column, int height, int width) {
    
    // Check if the wall is inside the bounds
    if ( (row < 0) || (column < 0) || (row + height > rows) || (column + width > columns) ) {
      return false;
    }
    for (int i = row; i < row + height; i++) {
      for (int j = column; j < column + width; j++) {
        this.layout[i][j] = 0;
      }
    }
    return true;
  }
  
  /**
   * generateWalls
   * Creates rectangles to represent the walls in the room and adds them to the room's ArrayList of walls
   */
  public void generateWalls() {
    
    boolean[][] partOfWall = new boolean[rows][columns];
    this.walls.clear();
    
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        
        /* If the current index of the layout is a wall and it is not part of any rectangles yet,
         * it becomes the top-left corner of a new rectangle. We find the bottom-right corner by
         * expanding the rectangle right and down until we find an open space or we have reached the bounds.
         */
        
        if ( (!partOfWall[i][j]) && (this.layout[i][j] == 1) ) {
          
          int rightMost = j;
          int bottomMost = i+1;
          
          // Increase the column as long as it is a wall
          while ( (rightMost < columns) && (this.layout[i][rightMost] == 1) && (!partOfWall[i][rightMost]) ) {
            partOfWall[i][rightMost] = true;
            rightMost++;
          }
          
          // Increase the row as long as it is a wall
          boolean isWall = true;
          while ( (bottomMost < rows) && isWall) {
            isWall = true;
            for (int k = j; k < rightMost; k++) {
              isWall &= (this.layout[bottomMost][k] == 1);
            }
            if (isWall) {
              isWall = true;
              for (int k = j; k < rightMost; k++) {
                partOfWall[bottomMost][k] = true;
              }
              bottomMost++;
            }
          }
          
          // Add rectangle to walls
          this.walls.add(new Rectangle (j*this.chunkSize, i*this.chunkSize, (rightMost-j)*this.chunkSize, (bottomMost-i)*this.chunkSize));
        } 
      }
    }
  }
  
  /**
   * getWalls
   * Getter for the room's walls
   * @return An arraylist containing rectangles representing the walls
   */
  public ArrayList<Rectangle> getWalls() {
    return this.walls;
  }
  
  /**
   * getEnemies
   * Getter for the room's enemies
   * @return An arraylist containing the enemies
   */
  public ArrayList<Enemy> getEnemies() {
    return this.enemies;
  }
  
  /**
   * getPlayerBullets
   * Getter for the player's projectiles in the room
   * @return An arraylist containing the projectiles
   */
  public ArrayList<Projectile> getPlayerBullets() {
    return this.playerBullets;
  }
  
  /**
   * getEnemyBullets
   * Getter for the enemy's projectiles in the room
   * @return An arraylist containing the projectiles
   */
  public ArrayList<Projectile> getEnemyBullets() {
    return this.enemyBullets;
  }
  
  /**
   * getChunkSize
   * Getter for the room's chunk size
   * @return The room's chunk size
   */
  public int getChunkSize() {
    return this.chunkSize;
  }
  
  /**
   * getRows
   * Getter for the number of rows in the room
   * @return The number of rows
   */
  public int getRows() {
    return this.rows;
  }
  
  /**
   * getColumns
   * Getter for the number of columns in the room
   * @return The number of columns
   */
  public int getColumns() {
    return this.columns;
  }
  
  /**
   * getLayout
   * Getter for the layout of the room
   * @return An array storing the layout of the room
   */
  public int[][] getLayout() {
    return this.layout;
  }
  
  /**
   * getItem
   * Getter for the room's item
   * @return The room's item
   */
  public Item getItem() {
    return this.item;
  }
  
  /**
   * setItem
   * Changes the room's item
   * @param item The item to change to
   */
  public void setItem(Item item) {
    this.item = item;
  }
  
  /**
   * loadSprite
   * Reads and returns an image
   * @param fileName The image to read
   * @return A BufferedImage representing the image of the specified file or null if there were errors reading the file
   */
  public static BufferedImage loadSprite(String fileName) {
    try {
      return (ImageIO.read(new File(fileName)));
    } catch (FileNotFoundException e) {
      System.out.println("No file by the name '" + fileName + "' exists.");  
    } catch (IOException e) {
      System.out.println("Error loading the file '" + fileName + "'.");
    }
    return null;
  }
  
  /**
   * draw
   * Draws all the objects in the room onto a graphics object
   * @param g The graphics object to draw on
   * @param xShift How much the x should be shifted when drawn
   * @param yShift How much the y should be shifted when drawn
   */
  public void draw (Graphics g, int xShift, int yShift) {
    
    // Draw the floor
    if (this.rows <= 24) {
      g.drawImage(floorSprite, xShift, yShift, null);
    } else {
      g.drawImage(largeFloorSprite, xShift, yShift, null);
    }
    
    // Draw the walls
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (layout[i][j] == 1) {
          int y = i*chunkSize;
          int x = j*chunkSize;
          int wallNumber = 0;
          
          // Choose wall to draw such that visible corners are rounded
          if (i <= 0 || (i > 0 && layout[i-1][j] == 0)) {
            wallNumber|=1;
          }
          if (j <= 0 || (j > 0 && layout[i][j-1] == 0)) {
            wallNumber|=2;
          }
          if (j+1 >= rows || (j+1 < rows && layout[i][j+1] == 0)) {
            wallNumber|=4;
          }
          if (i+1 >= rows || (i+1 < rows && layout[i+1][j] == 0)) {
            wallNumber|=8;
          }
          g.drawImage(wallSprites[wallNumber], x + xShift, y + yShift, null);
        }
      }
    }
    
    // Draw the item
    if (item != null) {
      if (item instanceof BasicGun) {
        ((BasicGun)item).setSprite(8);
      } else if (item instanceof Shotgun) {
        ((Shotgun)item).setSprite(8);
      } else if (item instanceof MachineGun) {
        ((MachineGun)item).setSprite(8);
      } else if (item instanceof ClearGun) {
        ((ClearGun)item).setSprite(4);
      } else if (item instanceof WaveGun) {
        ((WaveGun)item).setSprite(8);
      }
      item.draw(g,xShift,yShift);
    }
    
    // Draw player bullets
    for (int i = 0; i < this.playerBullets.size(); i++) {
      Projectile bullet = this.playerBullets.get(i);
      if (bullet != null) {
        bullet.setSprite(0);
        bullet.draw(g, xShift, yShift);
      }
    }
    
    // Draw enemy bullets
    for (int i = 0; i < this.enemyBullets.size(); i++) {
      Projectile bullet = this.enemyBullets.get(i);
      if (bullet != null) {
        if (bullet.getWidth() == 30) {
          bullet.setSprite(2);
        } else {
          bullet.setSprite(1);
        }
        bullet.draw(g, xShift, yShift);
      }
    }
    
    // Draw enemies and their guns
    for (int i = 0; i < this.enemies.size(); i++) {
      Enemy enemy = this.enemies.get(i);
      Gun enemyGun = enemy.getGun();
      if (enemy != null) {
          if (enemyGun instanceof BasicGun) {
            ((BasicGun)enemyGun).calculateSprite();
          } else if (enemyGun instanceof Shotgun) {
            ((Shotgun)enemyGun).calculateSprite();
          } else if (enemyGun instanceof MachineGun) {
            ((MachineGun)enemyGun).calculateSprite();
          } else if (enemyGun instanceof ClearGun) {
            ((ClearGun)enemyGun).calculateSprite();
          } else if (enemyGun instanceof WaveGun) {
            ((WaveGun)enemyGun).calculateSprite();
          } 
        enemy.draw(g, xShift, yShift);
        enemyGun.draw(g, xShift, yShift);
      }
    }
  }
}