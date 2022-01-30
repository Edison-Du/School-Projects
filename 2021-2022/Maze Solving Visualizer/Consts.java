/**
 * [Consts.java]
 * This class contains the constants used by the program.
 * 
 * @author Edison Du
 * @version 1.0 Nov 13, 2021
 */

import java.awt.Color;

public final class Consts {

    /* Maze related constants */
    public static final char WALL = '#';
    public static final char EMPTY = ' ';
    public static final char START = 'S';
    public static final char EXIT = 'E';

    public static final int NUM_DIRECTIONS = 4;
    public static final int[] ROW_MOVEMENT =    { 1, -1,  0,  0};
    public static final int[] COLUMN_MOVEMENT = { 0,  0,  1, -1};

    /* Visualizer and graphics related constants */
    public static final int VISUALIZER_UPDATE_INTERVAL= 20;

    public static final Color WALL_COLOR = Color.BLACK;
    public static final Color EMPTY_SPACE_COLOR = Color.WHITE;
    public static final Color PATH_COLOR = Color.BLUE;
    public static final Color VISITED_COLOR = Color.GRAY;
    public static final Color START_COLOR = Color.GREEN;
    public static final Color EXIT_COLOR = Color.RED;

    /* This class should never be constructed */
    private Consts() {}
}