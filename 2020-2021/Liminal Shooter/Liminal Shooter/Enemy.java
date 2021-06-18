/**
 * [Enemy.java]
 * Represents an enemy character
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Utility imports *******/
import java.util.ArrayList;


abstract class Enemy extends Character {
  
  private int range;
  private int movementRange;
  private int fireRate;
  private int bulletSpeed; 
  private Coordinate destination;
  private Gun gun;
  
  /**
   * Enemy
   * @param x The enemy's x position
   * @param y The enemy's y position
   * @param width The enemy's width
   * @param height The enemy's height
   * @param health The enemy's health
   * @param speed The enemy's speed
   * @param range The enemy's shooting range
   * @param movementRange The enemy's movement range
   * @param fireRate The enemy's rate of fire
   * @param bulletSpeed The speed of the enemy's bullets
   */
  Enemy (int x, int y, int width, int height, int health, int speed, int range, int movementRange, int fireRate, int bulletSpeed) {
    super(x, y, width, height, health, speed);
    this.range = range;
    this.movementRange = movementRange;
    this.fireRate = fireRate;
    this.bulletSpeed = bulletSpeed;
  }
  
  /**
   * move
   * Moves both the x and y value of the enemy and the enemy's gun
   */
  public void move() {
    this.moveHorizontal();
    this.moveVertical();
  }
  
  /**
   * moveVertical
   * Moves the y value of the enemy and the enemy's gun
   */
  public void moveVertical() {
    this.setY(this.getY() + this.getYDirection());
    this.gun.setPosition(this.getX()-5, this.getY()-5);
  }
  
  /**
   * moveHorizontal
   * Moves the x value of the enemy and the enemy's gun
   */
  public void moveHorizontal() {
    this.setX(this.getX() + this.getXDirection());
    this.gun.setPosition(this.getX()-5, this.getY()-5);
  }
  
  /**
   * calculateDirections
   * Calculates how much the enemy should be moving horizontally and vertically in order to reach the player
   * @param currentRoom The room the enemy is in
   * @param player The player to move to
   */
  public void calculateDirections(Room currentRoom, Player player) {
    
    ArrayList<Coordinate> usePath = null;
    ArrayList<ArrayList<Coordinate>> paths = new ArrayList<>();
    ArrayList<Enemy> otherEnemies = currentRoom.getEnemies();
    
    int[][] grid = new int[currentRoom.getRows()][currentRoom.getColumns()];
    
    int row = this.getY() / currentRoom.getChunkSize();
    int column = this.getX() / currentRoom.getChunkSize();
    
    int targetTopRow = player.getY() / currentRoom.getChunkSize();
    int targetLeftColumn = player.getX() / currentRoom.getChunkSize();
    int targetBottomRow = (player.getY() + player.getHeight() - 1) / currentRoom.getChunkSize();
    int targetRightColumn =(player.getX() + player.getWidth() - 1) / currentRoom.getChunkSize();
    
    int deltaX, deltaY;
    
    // Default directions
    this.setXDirection(0);
    this.setYDirection(0);
    
    // Copy the room layout into grid
    for (int i = 0; i < currentRoom.getRows(); i++) {
      for (int j = 0; j < currentRoom.getColumns(); j++) {
        grid[i][j] = currentRoom.getLayout()[i][j];
      }
    }
    
    // Treat other enemies in the room as walls so the pathfinding algorithm moves around them
    for (int i = 0; i < otherEnemies.size(); i++) {
      Enemy otherEnemy = otherEnemies.get(i);
      if ( (otherEnemy.getX() != this.getX()) || (otherEnemy.getY() != this.getY()) ) {
        
        int topRow = otherEnemy.getY() / currentRoom.getChunkSize();
        int leftColumn = otherEnemy.getX() / currentRoom.getChunkSize();
        int bottomRow = (otherEnemy.getY() + otherEnemy.getHeight() - 1) / currentRoom.getChunkSize();
        int rightColumn = (otherEnemy.getX() + otherEnemy.getWidth() - 1) / currentRoom.getChunkSize();
        
        // All chunks the enemy overlaps is marked as a wall
        for (int k = topRow; k <= bottomRow; k++) {
          for (int l = leftColumn; l <= rightColumn; l++) {
            grid[k][l] = 1;
          }
        }
      }
    }
    
    // Find if exists path from the enemy to any of the player's four corner chunks
    paths.add(Pathfinder.breadthFirstSearch(grid, currentRoom.getRows(), currentRoom.getColumns(), column, row, targetLeftColumn, targetTopRow));
    paths.add(Pathfinder.breadthFirstSearch(grid, currentRoom.getRows(), currentRoom.getColumns(), column, row, targetRightColumn, targetTopRow));
    paths.add(Pathfinder.breadthFirstSearch(grid, currentRoom.getRows(), currentRoom.getColumns(), column, row, targetLeftColumn, targetBottomRow));
    paths.add(Pathfinder.breadthFirstSearch(grid, currentRoom.getRows(), currentRoom.getColumns(), column, row, targetRightColumn, targetBottomRow));

    // Use the path that works
    for (int i = 0; i < paths.size(); i++) {
      if (paths.get(i) != null) {
        usePath = paths.get(i);
      }
    }
    if (usePath == null) {
      return;
    }
    
    // Determine destination point
    if (destination == null) {
      destination = new Coordinate(usePath.get(0).x, usePath.get(0).y);
      destination.x *= currentRoom.getChunkSize();
      destination.y *= currentRoom.getChunkSize();
    
    // Change destination point if we reached the current one
    } else if ( (Math.abs(destination.x-this.getX()) < this.getSpeed()) && (Math.abs(destination.y-this.getY()) < this.getSpeed()) ) {
      if (usePath.size() > 1) {
        destination = new Coordinate(usePath.get(1).x, usePath.get(1).y);
        destination.x *= currentRoom.getChunkSize();
        destination.y *= currentRoom.getChunkSize();
      }
    }
    
    // Calculate directions to the destination point
    deltaX = destination.x - this.getX();
    deltaY = destination.y - this.getY();
    
    if (deltaX != 0) {
      this.setXDirection((deltaX < 0 ? -1 : 1) * this.getSpeed());
    }
    if (deltaY != 0) {
      this.setYDirection((deltaY < 0 ? -1 : 1) * this.getSpeed());
    }
    
    // Reduce diagonal movement so the speed is consistent
    if ( (this.getXDirection() != 0) && (this.getYDirection() != 0) ) {
      int newDirection = (int)(Math.sqrt(Math.pow(this.getSpeed(), 2) / 2.0) + 0.5);
      this.setXDirection( (this.getXDirection() < 0 ? -1 : 1) * newDirection);
      this.setYDirection( (this.getYDirection() < 0 ? -1 : 1) * newDirection);
    }
    
  }
  
  /**
   * resetDestination
   * Changes destination point to null
   */
  public void resetDestination() {
    this.destination = null;
  }
  
  /**
   * getRange
   * Getter for the enemy's shooting range
   * @return The enemy's shooting range
   */
  public int getRange() {
    return this.range;
  }
  
  /**
   * getMovementRange
   * Getter for the enemy's movement range
   * @return The enemy's movement range
   */
  public int getMovementRange() {
    return this.movementRange;
  }
  
  /**
   * getFireRate
   * Getter for the enemy's rate of fire
   * @return The rate of fire
   */
  public int getFireRate() {
    return this.fireRate;
  }
  
  /**
   * getBulletSpeed
   * Getter for the speed of the enemy's bullets
   * @return The speed of the enemy's bullets
   */
  public int getBulletSpeed() {
    return this.bulletSpeed;
  }
  
  /**
   * getGun
   * Getter for the enemy's gun
   * @return The enemy's gun
   */
  public Gun getGun() {
    return this.gun;
  }
  
  /**
   * setGun
   * Changes the enemy's gun
   * @param gun The gun to change to
   */
  public void setGun(Gun gun) {
    this.gun = gun;
  }
}