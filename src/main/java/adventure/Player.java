package adventure;

import java.util.ArrayList;
import java.util.HashMap;

public class Player implements java.io.Serializable{

  private static final long serialVersionUID = -9064936473102319459L;

  private String name="";
  private ArrayList<Item> inventory;
  private HashMap<String,Item> itemsMap;
  private Room currentRoom;
  private String gameSaveName="";

  public Player(){
    setInventory();
    setItemsMap();
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
   * Initializes itemsMap
   */
  public void setItemsMap(){
    itemsMap=new HashMap<>();
  }

  /**
   * Initializes inventory
   */
  public void setInventory(){
    inventory=new ArrayList<>();
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
      return inventory;
  }

  /**
   * 
   * @param item
   */
  public final void take(Item item){
    inventory.add(item);
    itemsMap.put(item.getName(),item);
    currentRoom.removeItem(item);
  }

  public final void removeItem(Item item){
    inventory.remove(item);
    itemsMap.remove(item.getName());
  }

  /**
   * 
   * @param item
   */
  public final void toss(Item item){
    currentRoom.addItem(item);
    removeItem(item);
  }

  public final void wear(Clothing item){
    item.setWearing(true);
  }

  public final void eat(Item item){
    removeItem(item);
  }

  /**
   * Return player name
   * @return The player's name
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
    return gameSaveName;
  }

  public final void setGameSaveName(String newSaveName){
    gameSaveName=newSaveName;
  }

}
