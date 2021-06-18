/**
 * [Pathfinder.java]
 * A class that can perform various pathfinding-related tasks
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Utility imports *******/
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Random;


class Pathfinder {
  
  private static int[] columnChange = { 1, 1,-1,-1, 0, 0, 1,-1};
  private static int[] rowChange = { 1,-1, 1,-1, 1,-1, 0, 0};
  
  private static int filled;
  private static Random random = new Random();
  
  /**
   * breadthFirstSearch
   * Performs a breadth-first-search to find the shortest path between two points on a grid
   * @param grid The grid to search on
   * @param rows The number of rows in the grid
   * @param columns The number of columns in the grid
   * @param startRow The row of the starting position
   * @param startColumn The column of the starting position
   * @param endRow The row of the ending position
   * @param endColumn The column of the ending position
   * @return An ArrayList of coordinates containing the shortest path
   */
  public static ArrayList<Coordinate> breadthFirstSearch(int[][] grid, int rows, int columns, int startRow, int startColumn, int endRow, int endColumn) {

    ArrayList<Coordinate> path = new ArrayList<>();
    LinkedList<Coordinate> queue = new LinkedList<>();
    
    int cost[][] = new int[rows][columns];
    int inLayer = 1, nextLayer = 0;
    int distance = 0;
    
    /* Return null if the starting or ending positions are out of bounds, 
     * are on top of each other, or are on top of a wall
     */
    if ( (startRow < 0) || (startColumn < 0) || (startRow >= columns) || (startColumn >= rows) ) {
      return null;
    }
    if ( (endRow < 0) || (endColumn < 0) || (endRow >= columns) || (endColumn >= rows) ) {
      return null;
    }
    if ( (grid[startColumn][startRow] == 1) || (grid[endColumn][endRow] == 1) ) {
      return null;
    }
    if ( (startRow == endRow) && (startColumn == endColumn) ) {
      return null;
    }
    
    // Initialize cost as -1 which denotes the positions are unvisited
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        cost[i][j] = -1;
      }
    }
    
    // Start of breadth-first-search
    queue.add(new Coordinate(startRow, startColumn));
    
    // Search until the ending position is reached, or all available positions were searched
    while ( (!queue.isEmpty()) && (!( (queue.getFirst().x == endRow) && (queue.getFirst().y == endColumn) )) ) {
      
      Coordinate currentCoordinate = queue.poll();
      cost[currentCoordinate.y][currentCoordinate.x] = distance;
      
      // Search all 8 directions
      for (int i = 0; i < 8; i++) {
        
        int newY = currentCoordinate.y + columnChange[i];
        int newX = currentCoordinate.x + rowChange[i];
        
        // Only visit if the position is valid, and has not been visited before
        if ( (newX > 0) && (newY > 0) && (newY < rows) && (newX < columns) ) {
          if ( (cost[newY][newX] == -1) && (grid[newY][newX] == 0) ) {
            
            // Edge case for diagonal movement bypassing walls
            if ( ! ( (i < 4) && ( (grid[newY][currentCoordinate.x] == 1) || (grid[currentCoordinate.y][newX] == 1) ) ) ) {
              cost[newY][newX] = distance + 1;
              queue.add(new Coordinate (newX, newY));
              nextLayer++;
            }
          }
        }
      }
      
      // Increase the distance travelled after each layer of the search
      inLayer--;
      if (inLayer == 0) {
        inLayer = nextLayer;
        nextLayer = 0;
        distance++;
      }
      
    }
    
    // There exists no path
    if (cost[endColumn][endRow] == -1) {
      return null;
    }

    // Backtrack from the end to the start to find the path
    path.add(new Coordinate(endRow, endColumn));
    do {
      int nextX = 0, nextY = 0;
      for (int i = 0; i < 8; i++) {
        int newX = endRow + rowChange[i];
        int newY = endColumn + columnChange[i];
        
        /* Determine the previous spot as the spot reached with a cost exactly 
         * one less than the cost to the current spot
         */
        if ( (newX >= 0) && (newY >= 0) && (newX < columns) && (newY < rows) ) {
          if (cost[newY][newX] == cost[endColumn][endRow]-1) {
            nextX = newX;
            nextY = newY;
          }
        }
      }
      // Add spot to path
      endRow = nextX;
      endColumn = nextY;
      path.add(new Coordinate(endRow, endColumn));
    } while (!(endRow == startRow && endColumn == startColumn));
    
    Collections.reverse(path);
    return path;
  }
  
  /**
   * randomizedDepthFirstSearch
   * Uses a depth-first-search on a grid that visits positions randomly, and only visits a certain amount of positions
   * @param rows The number of rows in the grid
   * @param columns The number of columns in the grid
   * @param needFilled The number of positions to visit
   * @return The grid with the visited positions marked as 1, and non-visited marked as 0
   */
  public static int[][] randomizedDepthFirstSearch(int rows, int columns, int needFilled) {
    
    int[][] grid = new int[rows][columns];
    int startingRow = random.nextInt(rows);
    int startingColumn = random.nextInt(columns);
    
    // Impossible to fill the required amount
    if (needFilled > rows*columns) {
      return null;
    }
    
    filled = 0;
    randomizedDepthFirstSearch(grid,rows,columns,startingRow,startingColumn,needFilled);
    return grid;
  }
  
  /**
   * randomizedDepthFirstSearch
   * Recursive method that performs a depth-first-search on a grid that visits positions randomly, and only visits a certain amount of positions
   * @param grid The grid to search on
   * @param rows The number of rows in the grid
   * @param columns The number of columns in the grid
   * @param currentRow The current row being visited
   * @param currentColumn The current column being visited
   * @param needFilled The number of positions to visit
   */
  private static void randomizedDepthFirstSearch(int[][] grid, int rows, int columns, int currentRow, int currentColumn, int needFilled) {
    
    // Indexes represent the 4 directions of travel
    int[] indexes = {4,5,6,7};
    
    /**
     * Recursive base cases:
     * 1. Current position is out of bounds
     * 2. Current position has been visited
     * 3. The required amount of positions have been visited
     */
    if (currentRow < 0 || currentColumn < 0 || currentRow >= rows || currentColumn >= columns) {
      return;
    }
    if (grid[currentRow][currentColumn] == 1) {
      return;
    }
    if (filled == needFilled) {
      return;
    }
    
    grid[currentRow][currentColumn] = 1;
    filled++;
    
    // Shuffle the indexes of the 4 directions to randomize the search
    for (int i = 3; i > 0; i--) {
      int indexToSwap = random.nextInt(i);
      int currentValue = indexes[i];
      
      indexes[i] = indexes[indexToSwap];
      indexes[indexToSwap] = currentValue;
      
    }
    
    // Search from each direction
    for (int i : indexes) {
      int newRow = currentRow + columnChange[i];
      int newColumn = currentColumn + rowChange[i];
      randomizedDepthFirstSearch(grid,rows,columns,newRow,newColumn,needFilled);
    }
  }
}