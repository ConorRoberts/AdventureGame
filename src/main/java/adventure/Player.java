package adventure;

import java.util.ArrayList;
import java.util.HashMap;

public class Player implements java.io.Serializable{

  private static final long serialVersionUID = -9064936473102319459L;

  private String name;
  private ArrayList<Item> items;
  private HashMap<String,Item> itemsMap;
  private Room currentRoom;

  public Player(){
    items= new ArrayList<Item>();
    itemsMap = new HashMap<String, Item>();
  }

  @Override
  public final String toString(){
    return ("useless method");
  }

/**
 * 
 * @param itemName
 * @return Item object with given name
 */
  public final Item findItem(String itemName){
    return itemsMap.get(itemName);
  }

  /**
   * 
   * @return An arraylist of the player's inventory
   */
  public final ArrayList<Item> getInventory(){
    return items;
  }

  /**
   * 
   * @param item
   */
  public final void take(Item item){
    items.add(item);
    itemsMap.put(item.getName(),item);
    currentRoom.removeItem(item);
  }

  /**
   * 
   * @param item
   */
  public final void drop(Item item){
    currentRoom.addItem(item);
    items.remove(items.indexOf(item));
    itemsMap.remove(item.getName());
  }

  /**
   * This doesn't work because it really shouldn't be in here.
   * @return Then player's name
   */
  public final String getName(){
    return null;
  }

  /**
   * 
   * @return Room object of current room
   */
  public final Room getCurrentRoom(){
    return currentRoom;
  }

  /**
   * 
   * @param room
   */
  public final void setCurrentRoom(Room room){
    currentRoom=room;
  }

}
