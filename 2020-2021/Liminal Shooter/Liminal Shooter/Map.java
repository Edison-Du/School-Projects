/**
 * [Map.java]
 * Represents a map containing a layout of rooms
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

/******* Utility imports *******/
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

/******* Input and Output imports *******/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


class Map implements Drawable {
  
  private int rows, columns;
  private int numberOfRooms;
  private int currentRow, currentColumn;
  
  private int[][] mapLayout;
  private boolean[][] roomCleared;
  private Room[][] rooms;
  
  private Room bossRoom;
  private boolean reachedBoss;
  
  private ArrayList<int[][]> roomLayouts = new ArrayList<>();
  
  private Random random = new Random();

  /**
   * Map
   * @param rows The number of rows in the map
   * @param columns The number of columns in the map
   * @param numberOfRooms The number of rooms in the map
   */
  Map (int rows, int columns, int numberOfRooms) {

    /* Map must have at least 4 rooms because there are 3 keys and the starting room
     * If the dimensions cannot fit the number of rooms, create the smallest square that can
     */
    
    numberOfRooms = Math.max(numberOfRooms, 4);
    if (rows * columns < numberOfRooms) {
      rows = (int) (Math.sqrt(numberOfRooms) + 0.5);
      columns = (int) (Math.sqrt(numberOfRooms) + 0.5);
    }
    
    this.rows = rows;
    this.columns = columns;
    this.numberOfRooms = numberOfRooms;
    
    this.mapLayout = new int[rows][columns];
    this.roomCleared = new boolean[rows][columns];
    this.rooms = new Room[rows][columns];
    
    this.reachedBoss = false;
    
    // Read room layouts
    for (int i = 1; i < 10; i++) {
      String fileName = "Room Layouts/layout-" + i + ".txt";
      readLayout(fileName);
    }

    generateMapLayout();
  }
  
  /**
   * generateMapLayout
   * Generates a layout for the how the rooms in the map connect to one another, and determines the
   * wall layout of each room and the item each room contains. The layout is denoted with numbers:
   * 0 means there is no room
   * 1 means it is the starting room
   * 2 means it has a medic kit
   * 3 means it has a key
   * 4 means it has a shotgun
   * 5 means it has a machine gun
   * 6 means it has a clear gun
   * 7 means it has a wave gun
   */
  private void generateMapLayout() {
    
    ArrayList<Integer> availableGuns = new ArrayList<>();
    
    // Depth first search guarantees the rooms are connected
    this.mapLayout = Pathfinder.randomizedDepthFirstSearch(rows,columns,numberOfRooms);
    
    // Initially, set the item of all rooms to be either a medic kit or a gun
    for (int i = 4; i < 8; i++) {
      availableGuns.add(i);
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (this.mapLayout[i][j] != 0) {
          
          // Weighted random to have guns rarer than medic kits
          int item = random.nextInt(3);
          if ( (item == 0) && (availableGuns.size() > 0)) {
            int gun = random.nextInt(availableGuns.size());
            this.mapLayout[i][j] = availableGuns.get(gun);
            availableGuns.remove(gun);
          } else {
            this.mapLayout[i][j] = 2;
          }
        }
      }
    }
    
    // Place 3 keys
    for (int i = 0; i < 3; i++) {
      int row = random.nextInt(rows);
      int column = random.nextInt(columns);
      while (this.mapLayout[row][column]==0 || this.mapLayout[row][column]==3) {
        row = random.nextInt(rows);
        column = random.nextInt(columns);
      }
      this.mapLayout[row][column] = 3;
    }
    
    // Determine starting room
    do {
      this.currentRow = random.nextInt(rows);
      this.currentColumn = random.nextInt(columns);
      
      if (this.mapLayout[this.currentRow][this.currentColumn] != 0) {
        if (this.mapLayout[this.currentRow][this.currentColumn] != 3) {
          this.mapLayout[this.currentRow][this.currentColumn] = 1;
        }
      }
    } while (this.mapLayout[this.currentRow][this.currentColumn] != 1);

    
    // Add random wall layout to each room
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (this.mapLayout[i][j] != 0) {
          
          int roomNumber = random.nextInt(this.roomLayouts.size());
          int[][] room = this.roomLayouts.get(roomNumber);
          int roomRows = room.length;
          int roomColumns = room[0].length;
          this.rooms[i][j] = new Room (roomRows, roomColumns, 50);
          
          // The starting room is always empty of walls
          if (this.mapLayout[i][j] == 1) {
            room = this.roomLayouts.get(0);
          }
          
          // Copy layout into room 
          for (int k = 0; k < roomRows; k++) {
            for (int l = 0; l < roomColumns; l++) {
              this.rooms[i][j].getLayout()[k][l] = room[k][l];
            }
          }
        }
      }
    }
  }
  
  /**
   * readlayout
   * Reads a wall layout from a file
   * @param fileName The name of the file to read from
   */
  private void readLayout(String fileName) {
    try {
      Scanner fileInput = new Scanner(new File (fileName));
      int rows, columns;
      int[][] layout;

      rows = fileInput.nextInt();
      columns = fileInput.nextInt();
      
      if (rows <= 0 || columns <= 0) {
        return;
      }
      
      // Read the grid containing the layout
      layout = new int[rows][columns];
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
          layout[i][j] = fileInput.nextInt();
        }
      }
      
      // Add it to the map's ArrayList of room layouts
      this.roomLayouts.add(layout);
      fileInput.close();
      
    } catch (FileNotFoundException e) {
      System.out.println("No such file by the name of '" + fileName + "' exists."); 
    } catch (InputMismatchException e) {
      System.out.println("The file '" + fileName + "' is not in proper format for a room layout");
    }
  } 
  
  /**
   * spawnEnemies
   * Adds enemies to the current room
   * @amount The number of enemies to add
   * @return Whether or not it's possible to add the specified amount of enemies
   */
  public boolean spawnEnemies(int amount) {
    
    Room currentRoom = this.getCurrentRoom();
    
    int[][] canPlace = new int[currentRoom.getRows()][currentRoom.getColumns()];
    int emptySpace = 0;
    
    // Copy wall layout of room so enemies don't spawn on walls
    
    for (int i = 0; i < currentRoom.getRows(); i++) {
      for (int j = 0; j < currentRoom.getColumns(); j++) {
        canPlace[i][j] = currentRoom.getLayout()[i][j];
      }
    }
    
    // Make sure enemy doesn't spawn at doorways
    for (int i = 0; i < 5; i++) {
      for (int j = currentRoom.getColumns()/2-4 ; j < currentRoom.getColumns()/2+4 ; j++) {
        canPlace[i][j] = 1;
        canPlace[currentRoom.getRows()-i-1][j] = 1;
      }
    }
    for (int i = currentRoom.getRows()/2-4; i < currentRoom.getColumns()/2+4; i++) {
      for (int j = 0; j < 5; j++) {
        canPlace[i][j] = 1;
        canPlace[i][currentRoom.getColumns()-j-1] = 1;
      }
    }
    
    // Determine if it's possible to have the required amount of enemies
    for (int i = 0; i < currentRoom.getRows(); i++) {
      for (int j = 0; j < currentRoom.getColumns(); j++) {
        if (canPlace[i][j] == 0) {
          emptySpace++;
        }
      }
    }
    
    if (emptySpace < amount) {
      return false;
      
    } else {
      for (int i = 0; i < amount; i++) {
        int newRow, newColumn;
        int type = random.nextInt(10);
        
        // Determine spot to spawn the enemy
        do {
          newRow = random.nextInt(currentRoom.getRows());
          newColumn = random.nextInt(currentRoom.getColumns());
        } while (canPlace[newRow][newColumn] == 1);
        
        canPlace[newRow][newColumn] = 1;
        
        // Weighted randomness so some enemies are rarer
        if (type < 4) {
          currentRoom.addEnemy(1,newRow,newColumn,30,1,300,100,80,3);
        } else if (type < 6) {
          currentRoom.addEnemy(2,newRow,newColumn,60,1,300,150,100,3);
        } else if (type < 8) {
          currentRoom.addEnemy(3,newRow,newColumn,60,1,300,100,20,3);
        } else if (type < 9) {
          currentRoom.addEnemy(4,newRow,newColumn,80,1,500,200,200,3);
        } else {
          currentRoom.addEnemy(5,newRow,newColumn,80,1,500,200,200,4);
        }
      }
      return true;
    }
  }
  
  /**
   * openCurrentRoom
   * Creates doors on the sides of the room depending on whether that side connects to another room
   */
  public void openCurrentRoom() {
    Room currentRoom = this.getCurrentRoom();
    
    // Create doors
    if (currentRow > 0 && mapLayout[currentRow-1][currentColumn] != 0) {
      currentRoom.clearWall(0, currentRoom.getColumns()/2-2, 1,4);
    }
    if (currentRow < this.rows - 1 && mapLayout[currentRow+1][currentColumn] != 0) {
      currentRoom.clearWall(currentRoom.getRows()-1, currentRoom.getColumns()/2-2, 1,4);
    }
    if (currentColumn > 0 && mapLayout[currentRow][currentColumn-1] != 0) {
      currentRoom.clearWall(currentRoom.getRows()/2-2, 0, 4,1);
    }
    if (currentColumn < this.columns - 1 && mapLayout[currentRow][currentColumn+1] != 0) {
      currentRoom.clearWall(currentRoom.getRows()/2-2, currentRoom.getColumns()-1, 4,1);
    }
    
    // Puts an item in the center of the room depending on the layout
    switch(this.mapLayout[currentRow][currentColumn]) {
      case 1:
        currentRoom.setItem(new BossPortal(500, 500, 200, 200));
        break;
      case 2:
        currentRoom.setItem(new MedicKit(550, 550, 100, 100));
        break;
      case 3:
        currentRoom.setItem(new Key(550, 550, 100, 100));
        break;
      case 4:
        currentRoom.setItem(new Shotgun (550, 550, 60, 60, 60, 7));
        break;
      case 5:
        currentRoom.setItem(new MachineGun (550, 550, 60, 60, 10, 7));
        break;
      case 6:
        currentRoom.setItem(new ClearGun (550, 550, 60, 60,60, 5, 20));
        break;
      case 7:
        currentRoom.setItem(new WaveGun (550, 550, 60, 60, 200, 4, 20));
        break;
    }
  }
  
  /**
   * lockCurrentRoom
   * Encloses the border of the room with walls
   */
  public void lockCurrentRoom() {
    Room currentRoom = this.getCurrentRoom();
    currentRoom.addWall(0,0,currentRoom.getRows(),1);
    currentRoom.addWall(0,0,1,currentRoom.getColumns());
    currentRoom.addWall(0,currentRoom.getColumns()-1,currentRoom.getRows(),1);
    currentRoom.addWall(currentRoom.getRows()-1,0,1,currentRoom.getColumns());
  }
  
  /**
   * createBossRoom
   * Creates the boss room of the map
   */
  public void createBossRoom() {
    this.reachedBoss = true;
    this.bossRoom = new Room(40,40,50);
    this.lockCurrentRoom();
    this.bossRoom.generateWalls();
  }
  
  /**
   * getCurrentRoom
   * Gets the current room of the map (the room being displayed on the screen)
   * @return The current room
   */
  public Room getCurrentRoom() {
    if (this.reachedBoss && this.bossRoom != null) {
      return this.bossRoom;
    } else {
      return this.rooms[this.currentRow][this.currentColumn];
    }
  }
  
  /**
   * changeCurrentRoom
   * Changes the current room of the map
   * @row The row to change to
   * @column The column to change to
   * @return Whether or not the room to change to is valid
   */
  public boolean changeCurrentRoom(int row, int column) {
    if (row < 0 || column < 0 || row >= this.rows || column >= this.columns) {
      return false;
    }
    if (this.mapLayout[row][column] == 0) {
      return false;
    }
    this.currentRow = row;
    this.currentColumn = column;
    return true;
  }
  
  /**
   * getRows
   * Getter for the number of rows in the map
   * @return The number of rows
   */
  public int getRows() {
    return this.rows;
  }
  
  /**
   * getColumn
   * Getter for the number of columns in the map
   * @return The number of columns
   */
  public int getColumns() {
    return this.columns; 
  }
  
  /**
   * getCurrentRow
   * Getter for the row of the current room in the map
   * @return The row of the current room
   */
  public int getCurrentRow() {
    return this.currentRow;
  }
  
  /**
   * getCurrentColumn
   * Getter for the column of the current room in the map
   * @return The column of the current room
   */
  public int getCurrentColumn() {
    return this.currentColumn;
  }
  
  /**
   * getLayout
   * Getter for the layout of the map
   * @return An array storing the layout of the map
   */
  public int[][] getLayout() {
    return this.mapLayout;
  }
  
  /**
   * getRoomCleared
   * Getter for which rooms are cleared in the map
   * @return An array storing which rooms are cleared
   */
  public boolean[][] getRoomCleared() {
    return this.roomCleared;
  }
  
  /**
   * getRooms
   * Getter for the rooms in the map
   * @return An array storing the rooms in the map
   */
  public Room[][] getRooms() {
    return this.rooms;
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
   * Draws the map onto a graphics object
   * @param g The graphics object to draw on
   * @param xShift How much the x should be shifted when drawn
   * @param yShift How much the y should be shifted when drawn
   */
  public void draw (Graphics g, int xShift, int yShift) {
    g.setColor(new Color(200,200,200));
    g.drawRect(xShift-10,yShift-10,115,115);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        
        // Choose the colour of each room
        if (this.mapLayout[i][j] != 0) {
          if (i == currentRow && j == currentColumn && !reachedBoss) {
            g.setColor(new Color(200,0,0));
          } else if (this.mapLayout[i][j] == 1) {
            g.setColor(new Color(0,200,0));
          } else if (this.roomCleared[i][j]) {
            g.setColor(new Color(0,0,200));
          } else {
            g.setColor(new Color(125,125,125));
          }
          g.fillRect(xShift + j*20, yShift + i*20, 15, 15);
        } else {
          g.setColor(new Color(25,25,25));
          g.fillRect(xShift + j*20, yShift + i*20, 15, 15);
        }
      }
    }
  }
}