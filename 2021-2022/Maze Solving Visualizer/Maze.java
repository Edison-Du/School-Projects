/**
 * [Maze.java]
 * This class represents a two-dimensional maze.
 * The maze is read from a file and stored in a vector of strings.
 * 
 * @author Edison Du
 * @version 1.0 Nov 13, 2021
 */

import java.util.Vector;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Maze {

    private int rows;
    private int columns;
    private int startingRow, startingColumn;
    private Vector<String> maze;

    /**
     * Maze
     * Constructs a new maze from a file.
     * @param fileName The name of the file to read the maze from
     */
    public Maze(String fileName) {
        this.rows = 0;
        this.columns = 0;
        this.maze = new Vector<String>();

        // Stop the program if the file does not exist
        if (!this.readFile(fileName)) {
            System.exit(0);
        }
    }

    /**
     * readFile
     * Stores the maze from the file as a vector of strings.
     * Determines the number of rows and columns in the maze, 
     * as well as the starting point.
     * @param fileName The name of the file to read from
     * @return True if the file exists, false otherwise.
     */
    private boolean readFile(String fileName) {
        try {
            Scanner fileInput = new Scanner(new File(fileName));

            while (fileInput.hasNextLine()) {
                String row = fileInput.nextLine();

                this.maze.add(row);
                this.columns = row.length();
                
                // Find the starting point in the maze
                for (int i = 0; i < this.columns; i++) {
                    if (row.charAt(i) == Consts.START) {
                        this.startingRow = this.rows;
                        this.startingColumn = i;
                    } 
                }
                this.rows++;
            }
            fileInput.close();
            
        } catch (FileNotFoundException e) {
            System.out.printf("Error opening file %s, no such file exists.\n", fileName);
            return false;
        }
        return true;
    }

    /**
     * rows
     * Gets the number of rows in the maze.
     * @return the number of rows.
     */
    public int rows() {
        return this.rows;
    }

    /**
     * columns
     * Gets the number of columns in the maze.
     * @return the number of columns.
     */
    public int columns() {
        return this.columns;
    }

    /**
     * getStartingRow
     * Gets the row of the starting position in the maze.
     * @return the row of the starting position.
     */
    public int getStartingRow() {
        return this.startingRow;
    }

    /**
     * getStartingColumn
     * Gets the column of the starting position in the maze.
     * @return the column of the starting position.
     */
    public int getStartingColumn() {
        return this.startingColumn;
    }

    /**
     * cellAt
     * Gets the character at a cell in the maze.
     * @param row the row of the cell to get
     * @param column the column of the cell to get
     * @return the character at the cell, or null if the specified cell is out of bounds
     */
    public Character cellAt(int row, int column) {
        if (inBounds(row, column)) {
            return this.maze.get(row).charAt(column);
        }
        return null;
    }

    /**
     * inBounds
     * Checks of a cell is inside the maze boundaries.
     * @param row the row of the cell to check
     * @param column the column of the cell to check
     * @return true if the cell is inside the maze, false otherwise
     */
    public boolean inBounds(int row, int column) {
        if ( (column >= 0) && (row >= 0) && (column < this.columns) && (row < this.rows) ) {
            return true;
        }
        return false;
    }
}