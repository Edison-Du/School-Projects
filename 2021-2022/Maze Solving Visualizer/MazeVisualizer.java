/**
 * [MazeVisualizer.java]
 * This class is a JFrame that graphically displays a maze.
 * 
 * @author Edison Du
 * @version 1.0 Nov 13, 2021
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

public class MazeVisualizer extends JFrame {

    /* Graphics Related Constants */
    private final int MAX_X = (int)(getToolkit().getScreenSize().getWidth() * 0.8);
    private final int MAX_Y = (int)(getToolkit().getScreenSize().getHeight() * 0.8);    
    private final int GRID_SIZE;

    /* Maze variables */
    private Maze maze;
    private boolean[][] visited;
    private boolean[][] isVisiting;
    private MazePanel panel;

    /**
     * MazeVisualizer
     * Constructs the JFrame and adds a MazePanel to visualize the maze.
     * @param maze the maze object of the maze to visualize
     * @param visited a 2D array storing the spots in the maze that have already been visited
     * @param isVisiting a 2D array storing the spots in the maze currently being visited
     */
    public MazeVisualizer (Maze maze, boolean[][] visited, boolean[][] isVisiting) {
        
        this.maze = maze;
        this.visited = visited;
        this.isVisiting = isVisiting;
        GRID_SIZE = Math.min(MAX_X/maze.columns(), MAX_Y/maze.rows());

        // MazePanel
        this.panel = new MazePanel();
        this.panel.setPreferredSize(
            new Dimension(GRID_SIZE * maze.columns(), GRID_SIZE * maze.rows())
        );
        this.getContentPane().add(panel);

        // JFrame settings
        this.pack();
        this.setTitle("Maze Solver Visualizer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.requestFocusInWindow();
        this.setVisible(true);
    }

    /**
     * MazePanel
     * The JPanel to draw the maze onto.
     */
    private class MazePanel extends JPanel {

        /**
         * paintComponent
         * Draws the maze onto the JPanel.
         * @param g the graphics object to draw onto
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Traverse the entire maze and draw a coloured square depending on each cell in the maze
            for (int i = 0; i < maze.rows(); i++) {
                for (int j = 0; j < maze.columns(); j++) {

                    if (maze.cellAt(i, j) == Consts.EXIT) {
                        g.setColor(Consts.EXIT_COLOR);
                    } else if (maze.cellAt(i, j) == Consts.START) {
                        g.setColor(Consts.START_COLOR);
                    } else if (isVisiting[i][j]) {
                        g.setColor(Consts.PATH_COLOR);
                    } else if (visited[i][j]) {
                        g.setColor(Consts.VISITED_COLOR);
                    } else if (maze.cellAt(i, j) == Consts.WALL) {
                        g.setColor(Consts.WALL_COLOR);
                    } else {
                        g.setColor(Consts.EMPTY_SPACE_COLOR);
                    }

                    g.fillRect(GRID_SIZE*j, GRID_SIZE*i, GRID_SIZE, GRID_SIZE);
                }
            }
        }
    }
}