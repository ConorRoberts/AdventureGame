package adventure;

import java.util.ArrayList;
import java.util.HashMap;

public class Player implements java.io.Serializable{

  private static final long serialVersionUID = -9064936473102319459L;

  private String name;
  private ArrayList<Item> items;
  private HashMap<String,Item> itemsMap;
  private Room currentRoom;
  private String saveName;

  public Player(){
    items= new ArrayList<Item>();
    itemsMap = new HashMap<String, Item>();
  }

  public Player(String newName){
    this();
    setName(newName);
  }

  @Override
  public final String toString(){
    return ("Player: "+name);
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
   * Useful to check if findItem will be null
   * @param itemName Item object name
   * @return Whether the item exists in player inventory
   */
  public final boolean hasItem(String itemName){
    return itemsMap.containsKey(itemName);
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
    items.remove(item);
    itemsMap.remove(item.getName());
  }

  /**
   * Return player name
   * @return Then player's name
   */
  public final String getName(){
    return name;
  }


  /**
   * Sets player name
   * @param newName Player's name
   */
  public final void setName(String newName){
    name=newName;
  }

  /**
   * 
   * @return Room object of current room
   */
  public final Room getCurrentRoom(){
    return currentRoom;
  }

  /**
   * Sets player's current position
   * @param room Room object
   */
  public final void setCurrentRoom(Room room){
    currentRoom=room;
  }

  /**
   * Why would I ever want to use this method???
   * @return Nothing useful lol
   */
  public final String getSaveGameName(){
    return saveName;
  }

  public final void setGameSaveName(String newSaveName){
    saveName=newSaveName;
  }

}
