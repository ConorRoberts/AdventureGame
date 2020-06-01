package adventure;

import java.util.ArrayList;
import java.util.HashMap;

public class Player implements java.io.Serializable{

  private static final long serialVersionUID = -9064936473102319459L;

  private ArrayList<Item> items;
  private HashMap<String,Item> itemsMap;
  private Room currentRoom;

  public Player(){
    items= new ArrayList<Item>();
    itemsMap = new HashMap<String, Item>();
  }

/**
 * 
 * @param name
 * @return
 */
  public final Item findItem(String name){
    return itemsMap.get(name);
  }

  /**
   * 
   * @return
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
   * 
   * @return
   */
  public final String getSaveGameName(){
    return null;
  }

  /**
   * 
   * @return
   */
  public final String getName(){
    return null;
  }

  /**
   * 
   * @return
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
