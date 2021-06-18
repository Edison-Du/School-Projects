/**
 * [GeometryDrawingProgram.java]
 * A program that draws various different shapes as specified by the user.
 * @author Edison Du
 * @version 1.0 May 12, 2021
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

class GeometryDrawingProgram {
  
  /* ================================ Class Variables ================================ */
  
  static JFrame frame;
  static ArrayList<TwoDimensionalShape> shapes = new ArrayList<>();
  
  // Start of main
  
  public static void main(String[] args) {  
    
    /* ================================ Variables ================================ */
    
    GeometryScreen gs = new GeometryScreen();
    Scanner input = new Scanner(System.in);
    int userChoice = 0;
    
    /* ================================ Main code ================================ */
    
    do {
      
      try {
        
        // Main menu
        System.out.println("Please enter in '1' to display all shapes w/relevant data.");
        System.out.println("Please enter in '2' to add a shape.");
        System.out.println("Please enter in '3' to remove a shape.");
        System.out.println("Please enter in '4' to translate a single shape.");
        System.out.println("Please enter in '5' to translate the entire drawing.");
        System.out.println("Please enter in '6' to quit the program.");
        userChoice = input.nextInt();
        
        /*---------------- Outputting the list of all shapes with their information ----------------*/
        
        if (userChoice == 1) {
          
          if (shapes.size() == 0) {
            System.out.println("There are currently no shapes");
            
          } else {
            
            System.out.println("List of shapes:");
            
            for (int i = 0; i < shapes.size(); i++) {
              
              /* Output a string containing the info of the current shape, info includes:
               *   - # of the shape (ordered by time added)
               *   - Type of shape
               *   - Colour (R, G, B)
               *   - Coordinates of centre (x, y)
               *   - Dimensions of shape
               *   - Area of shape
               *   - Perimeter of shape
               */
              
              String currentShapeInfo = "";
              String shapeType = "";
              TwoDimensionalShape currentShape = shapes.get(i);
              
              // Determining shape name
              if (currentShape instanceof Circle) {
                shapeType = "Circle";
              } else if (currentShape instanceof Square) {
                shapeType = "Square";
              } else if (currentShape instanceof Parallelogram) {
                shapeType = "Parallelogram";
              } else if (currentShape instanceof Rhombus) {
                shapeType = "Rhombus";
              } else if (currentShape instanceof Rectangle) {
                shapeType = "Rectangle";
              } else if (currentShape instanceof Triangle) {
                shapeType = "Triangle";
              } else {
                shapeType = "Oval";
              } 
              
              currentShapeInfo += String.format("No.%2d | Type: %14s | ",i+1,shapeType);
              currentShapeInfo += String.format("Colour: (%3d, %3d, %3d) | ",currentShape.getRedValue(),currentShape.getGreenValue(),currentShape.getBlueValue());
              currentShapeInfo += String.format("Centre: (%4d,%4d) | ",currentShape.getCentreX()-250,250-currentShape.getCentreY());
              
              // Determine what type of dimensions describe the shape, and their respective values
              if (currentShape instanceof Triangle) {
                currentShapeInfo += String.format("Side Length: %13d | ",((Triangle)currentShape).getSideLength());
              } else if (currentShape instanceof Circle) {
                currentShapeInfo += String.format("Diameter: %16d | ",((Circle)currentShape).getDiameter());
              } else if (currentShape instanceof Ellipse) {
                currentShapeInfo += String.format("Width: %4d | Height: %4d | ",((Ellipse)currentShape).getWidth(),((Ellipse)currentShape).getHeight());
              } else {
                currentShapeInfo += String.format("Width: %4d | Height: %4d | ",((Quadrilateral)currentShape).getWidth(),((Quadrilateral)currentShape).getHeight());
              }
              
              currentShapeInfo += String.format("Area: %10.2f | Perimeter: %8.2f",currentShape.getArea(),currentShape.getPerimeter());
              
              System.out.println(currentShapeInfo);
            }
          }
          
        /*---------------- Adding a shape to the list ----------------*/
          
        } else if (userChoice == 2) {
          
          int shapeChosen;
          
          // Menu of possible shapes
          System.out.println("Please enter in '1' for Oval.");
          System.out.println("Please enter in '2' for Rectangle.");
          System.out.println("Please enter in '3' for Parallelogram.");
          System.out.println("Please enter in '4' for Rhombus.");
          System.out.println("Please enter in '5' for Square.");
          System.out.println("Please enter in '6' for Triangle.");
          System.out.println("Please enter in '7' for Circle.");
          shapeChosen = input.nextInt();
          
          if ( (shapeChosen < 1) || (shapeChosen > 7) ) {
            System.out.printf("%d is not a valid shape.\n", shapeChosen);
            
          } else {
            
            int centreX, centreY;
            int red, green, blue;
            
            System.out.println("What is the x-coordinate for the centre of your shape?");
            centreX = input.nextInt()+250;
            System.out.println("What is the y-coordinate for the centre of your shape?");
            centreY = 250-input.nextInt();
            System.out.println("What red colour value do you want for your shape (0-255)?");
            red = input.nextInt();
            System.out.println("What green colour value do you want for your shape (0-255)?");
            green = input.nextInt();
            System.out.println("What blue colour value do you want for your shape (0-255)?");
            blue = input.nextInt();
            
            if ( (red < 0) || (green < 0) || (blue < 0) || (red > 255) || (green > 255) || (blue > 255) ) {
              System.out.printf("The colour (%d,%d,%d) is not a valid colour.\n", red, green, blue);
              
            } else {
              
              // The first 4 shapes (oval, rectangle, parallelogram and rhombus) require a width and height to be constructed
              if (shapeChosen <= 4) {
                
                int width, height;
                
                System.out.println("What is the width of your shape? (must be >= 1)");
                width = input.nextInt();
                System.out.println("What is the height of your shape? (must be >= 1)");
                height = input.nextInt();
                
                if ( (width < 1) || (height < 1) ) {
                  System.out.printf("A shape with a width of %d and a height of %d is invalid\n", width, height);
                  
                } else {
                  
                  if (shapeChosen == 1) {
                    shapes.add(new Ellipse(centreX, centreY, red, green, blue, width, height));
                  } else if (shapeChosen == 2) {
                    shapes.add(new Rectangle(centreX, centreY, red, green, blue, width, height));
                  } else if (shapeChosen == 3) {
                    shapes.add(new Parallelogram(centreX, centreY, red, green, blue, width, height));
                  } else {
                    shapes.add(new Rhombus(centreX, centreY, red, green, blue, width, height));
                  }
                  
                  System.out.println("Shape succesfully added.");
                }
                
              // Shapes 5 and 6 (square and triangle) only requires a side length to be constructed
              } else if (shapeChosen <= 6) {
                
                int sideLength;
                
                System.out.println("What is the side length of your shape? (must be >= 1)");
                sideLength = input.nextInt();
                
                if (sideLength < 1) {
                  System.out.printf("%d is an invalid side length.\n", sideLength);
                  
                } else {
                  
                  if (shapeChosen == 5) {
                    shapes.add(new Square(centreX, centreY, red, green, blue, sideLength));
                  } else  {
                    shapes.add(new Triangle(centreX, centreY, red, green, blue, sideLength));
                  }
                  
                  System.out.println("Shape succesfully added.");
                }
                
              // Shape 8 (circle) only requires the diameter to be constructed
              } else {
                
                int diameter;
                
                System.out.println("What is the diameter of your shape? (must be >= 1)");
                diameter = input.nextInt();
                
                if (diameter < 1) {
                  System.out.printf("%d is an invalid diameter length.\n", diameter);
                  
                } else {
                  shapes.add(new Circle(centreX, centreY, red, green, blue, diameter));
                  System.out.println("Shape succesfully added.");
                }
              }
            }
          }
        
        /*---------------- Removing a shape from the list ----------------*/
          
        } else if (userChoice == 3) {
          
          if (shapes.size() == 0) {
            System.out.println("There are currently no shapes");
            
          } else {
            
            int shapeIndex;
            
            System.out.printf("Which shape do you want to remove? (1-%d)", shapes.size());
            shapeIndex = input.nextInt();
            
            // Make sure index is in bounds of the list
            if ( (shapeIndex < 1) || (shapeIndex > shapes.size()) ) {
              System.out.printf("No shape by the number %d exists.\n", shapeIndex);
              
            } else {
              shapes.remove(shapeIndex-1);
              System.out.println("Shape succesfully removed.");
            }
          }
          
        /*---------------- Translating a shape ----------------*/
          
        } else if (userChoice == 4) {
          
          if (shapes.size() == 0) {
            System.out.println("There are currently no shapes");
            
          } else {
            
            int shapeIndex;
            int xShift, yShift;
            
            System.out.printf("Which shape do you want to shift? (1-%d)", shapes.size());
            shapeIndex = input.nextInt();
            
            // Make sure index is in bounds of the list
            if ( (shapeIndex < 1) || (shapeIndex > shapes.size()) ) {
              System.out.printf("No shape numbered %d exists.\n", shapeIndex);
              
            } else {
              
              System.out.println("How much would you like to shift shapes along the x-axis?");
              xShift = input.nextInt();
              System.out.println("How much would you like to shift shapes along the y-axis?");
              yShift = input.nextInt();
              
              shapeIndex--;
              shapes.get(shapeIndex).setCentreX(shapes.get(shapeIndex).getCentreX() + xShift);
              shapes.get(shapeIndex).setCentreY(shapes.get(shapeIndex).getCentreY() - yShift);
              
              System.out.println("Succesfully shifted");
            }
          }
          
        /*---------------- Translating all shapes ----------------*/
          
        } else if (userChoice == 5) {
          
          if (shapes.size() == 0) {
            System.out.println("There are currently no shapes");
            
          } else {
            
            int xShift, yShift;
            
            System.out.println("How much would you like to shift shapes along the x-axis?");
            xShift = input.nextInt();
            System.out.println("How much would you like to shift shapes along the y-axis?");
            yShift = input.nextInt();
            
            // Shift every shape in the list
            for (int i = 0; i < shapes.size(); i++) {
              shapes.get(i).setCentreX(shapes.get(i).getCentreX() + xShift);
              shapes.get(i).setCentreY(shapes.get(i).getCentreY() - yShift);
            }
            
            System.out.println("Succesfully shifted");
          }
          
          
        } else if (userChoice != 6) {
          System.out.printf("%d is not a valid option.\n", userChoice);
        }
        
      } catch (InputMismatchException e) {
        System.out.println("Please enter an integer value.");
        input.next();
      }
      
      System.out.println();
      
      frame.repaint(); 
      
    } while(userChoice != 6);
    
    frame.dispose();
    input.close();
  }
  
  // End of main
  
  
  /* ================================ Inner Classes ================================ */
  
  /**
   * GeometryScreen
   * Represents a window that can be display visuals
   */
  public static class GeometryScreen {
    
    /**
     * GeometryScreen constructor
     * Creates a frame and a panel to be displayed on this window
     */
    GeometryScreen() {
      
      frame = new JFrame("Geometry Drawing Program 1.0");
      JPanel graphicsPanel = new GraphicsPanel();
      frame.getContentPane().add(graphicsPanel);
      
      // Set the frame to full screen 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(520,540);
      frame.setUndecorated(false);  
      frame.setVisible(true);
      
    } 
    
    
    /**
     * GraphicsPanel
     * Represents a panel that can be drawn on
     */
    public static class GraphicsPanel extends JPanel {
      
      /**
       * paintComponent
       * Draws onto a graphics object
       * @param g The graphics object to draw on
       */
      public void paintComponent(Graphics g) {          
        
        setDoubleBuffered(true);   
        
        // Draw the X/Y Axis
        g.setColor(Color.BLACK);
        g.drawLine(250, 0, 250, 500);
        g.drawLine(0, 250, 500, 250);
        
        // Draw all shapes currently active
        for (int i = 0; i < shapes.size(); i++) {
          shapes.get(i).draw(g);
        }
      }
    }
  }
}
