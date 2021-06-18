/**
 * [GameObject.java]
 * Represents an object that can be interacted with during gameplay
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/******* Input and Output imports *******/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


abstract class GameObject implements Drawable {
  private int x, y;
  private int width, height;
  private Rectangle collisionBox;
  
  /**
   * GameObject
   * @param x The object's x position
   * @param y The object's y position
   * @param width The object's width
   * @param height The object's height
   */
  GameObject(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.collisionBox = new Rectangle(x,y,width,height);
  }
  
  /**
   * getX
   * Getter for the x position
   * @return The x position of the object
   */
  public int getX() {
    return this.x;
  }
  
  /**
   * getY
   * Getter for the y position
   * @return The y position of the object
   */
  public int getY() {
    return this.y;
  }
  
  /**
   * getWidth
   * Getter for the width
   * @return The width of the object
   */
  public int getWidth() {
    return this.width;
  }
  
  /**
   * getHeight
   * Getter for the height
   * @return The height of the object
   */
  public int getHeight() {
    return this.height;
  }
  
  /**
   * getCenterX
   * Getter for the x position of the object's center
   * @return The x position of the object's center
   */
  public int getCenterX() {
    return this.x + this.width/2;
  }
  
  /**
   * getCenterY
   * Getter for the y position of the object's center
   * @return The y position of the object's center
   */
  public int getCenterY() {
    return this.y + this.width/2;
  }
  
  /**
   * getCollisionBox
   * Gets the collision box of the object
   * @return A rectangle representing the collision box
   */
  public Rectangle getCollisionBox() {
    return this.collisionBox;
  }
  
  /**
   * setX
   * Changes the x position of the object
   * @param x The x position to change to
   */
  public void setX(int x) {
    this.x = x;
    this.collisionBox.setLocation(x, y);
  }
  
  /**
   * setY
   * Changes the y position of the object
   * @param y The y position to change to
   */
  public void setY(int y) {
    this.y = y;
    this.collisionBox.setLocation(x, y);
  }
  
  /**
   * setPosition
   * Changes the position of the object
   * @param x The x position to change to
   * @param y The y position to change to
   */
  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
    this.collisionBox.setLocation(x, y);
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
}