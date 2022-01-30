/**
 * [MazeSolver.java]
 * This program uses depth-first search to find a path from the starting
 * to the exit point in a maze. The depth first search process is 
 * visualized on a JFrame.
 * 
 * @author Edison Du
 * @version 1.0 Nov 13, 2021
 */

import java.util.Scanner;

public class MazeSolver {

    private static Maze maze;
    private static boolean[][] visited;
    private static boolean[][] isVisiting;
    private static MazeVisualizer visualizerWindow;

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);

        // Create a maze object with the user-inputted file
        System.out.print("Enter the file of your maze: ");
        maze = new Maze(keyboard.next());
        keyboard.close();

        // Initialize supporting arrays for the depth-first search
        visited = new boolean[maze.rows()][maze.columns()];
        isVisiting = new boolean[maze.rows()][maze.columns()];

        visualizerWindow = new MazeVisualizer(maze, visited, isVisiting);
        findExit(maze.getStartingRow(), maze.getStartingColumn());
        updateVisualizer();
    }

    /**
     * findExit
     * A recursive, depth-first search method that finds the path
     * from the current point to the exit point of a maze.
     * @param currentRow the row of the current point
     * @param currentColumn the column of the current point
     * @return True if there is a path to the exit from the current point, false otherwise
     */
    private static boolean findExit(int currentRow, int currentColumn) {
        /**
         * Recursive Base Cases
         * 1. The current point is out of bounds of the maze
         * 2. The current point is a wall
         * 3. The current point has already been visited
         * 4. The current point is the exit point
         */
        if (!maze.inBounds(currentRow, currentColumn)) {
            return false;
        }
        if (maze.cellAt(currentRow, currentColumn) == Consts.WALL) {
            return false;
        }
        if (visited[currentRow][currentColumn] || isVisiting[currentRow][currentColumn]) {
            return false;
        }
        if (maze.cellAt(currentRow, currentColumn) == Consts.EXIT) {
            return true;
        }

        // Mark the current point as "being searched"
        isVisiting[currentRow][currentColumn] = true;
        updateVisualizer();

        // Recursively search each adjacent point in the maze
        for (int i = 0; i < Consts.NUM_DIRECTIONS; i++) {
            int newRow    = currentRow    + Consts.ROW_MOVEMENT[i];
            int newColumn = currentColumn + Consts.COLUMN_MOVEMENT[i];
            if (findExit(newRow, newColumn)) {
                return true;
            }
        }
        
        // Mark the current point as "completely searched"
        isVisiting[currentRow][currentColumn] = false;
        visited[currentRow][currentColumn] = true;
        updateVisualizer();
        return false;
    }

    /**
     * updateVisualizer
     * Repaints the JFrame displaying the maze at certain intervals.
     */
    private static void updateVisualizer() {
        try {
            visualizerWindow.repaint();
            Thread.sleep(Consts.VISUALIZER_UPDATE_INTERVAL);
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted");
        }
    }
}
