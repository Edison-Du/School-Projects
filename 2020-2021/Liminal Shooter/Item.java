/**
 * [Item.java]
 * Represents an object that can can be collected
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

abstract class Item extends GameObject {
  
  private static int collectRange = 100;
  
  /**
   * Item
   * @param x The item's x position
   * @param y The item's y position
   * @param width The item's width
   * @param height The item's height
   */
  Item (int x, int y, int width, int height) {
    super(x, y, width, height);
  }
  
  /**
   * getCollectRange
   * Getter for the collect range of the item
   * @return The collect range
   */
  public static int getCollectRange() {
    return collectRange;
  }
  
  /**
   * inCollectRange
   * Uses distance formula to check if a point is within the collect range of the item
   * @param x The x position of the point
   * @param y The y position of the point
   * @return True if the point is in the collect range, false otherwise
   */
  public boolean inCollectRange(int x, int y) {
    int distance = (int)(Math.sqrt(Math.pow(this.getCenterX()-x, 2)+Math.pow(this.getCenterY()-y, 2)));
    return (distance <= collectRange);
  }
};