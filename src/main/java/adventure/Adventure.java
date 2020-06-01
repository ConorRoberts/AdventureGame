package adventure;

import java.util.ArrayList;
import java.util.HashMap;

public class Adventure implements java.io.Serializable{

  private static final long serialVersionUID = -9064936473102319459L;

    private Player player;
    private ArrayList<Room> rooms;
    private ArrayList<Item> items;
    private HashMap<String,Room> roomsID;
    private HashMap<String,Item> itemsID;
    private HashMap<String,Item> itemsName;

    public Adventure(){
      rooms = new ArrayList<Room>();
      items= new ArrayList<Item>();
      roomsID = new HashMap<String,Room>();
      itemsID = new HashMap<String,Item>();
      itemsName = new HashMap<String,Item>();
      player=new Player();
    }

    /**
     * @return A string formatted version of the object
     */
    @Override
    public final String toString(){
      return ("cool");
    }

    /**
    * Checks whether the input name is part of the item list
    * @param name Item name to check
    * @return Whether the given name is a valid item
    */
    public final boolean isItem(String name){
      return itemsName.containsKey(name);
    }

    /**
     * Gives access to the player
     * @return Player object
     */
    public Player getPlayer(){
      return player;
    }

    /**
    * @return List of items in player's inventory
    */
    public final ArrayList<Item> listPlayerItems(){
      return player.getInventory();
    }

    /**
    * @return List of all rooms
    */
    public final ArrayList<Room> listAllRooms(){
        return rooms;
    }

    /**
    * @return List of all items
    */
    public final ArrayList<Item> listAllItems(){
        return items;
    }

    /**
    * @return Current room description
    */
    public final String getCurrentRoomDescription(){
        return player.getCurrentRoom().getShortDescription();
    }
    
    /**
     * Adds room object to adventure
     * @param room
     */
    public final void addRoom(Room room){
      this.rooms.add(room);
      this.roomsID.put(room.getID(),room);
    }

    /**
     * 
     * @param item
     */
    public final void addItem(Item item){
      this.items.add(item);
      this.itemsID.put(item.getID(),item);
      this.itemsName.put(item.getName(),item);
    }

    /**
    * Search for an item based on ID
    * @param id Item ID
    * @return Item object with given ID
    */
    public final Item findItemID(String id){
      return itemsID.get(id);
    }

    /**
    * Search for an item based on name
    * @param name Item name
    * @return Item object with given name
    */
    public final Item findItemName(String name){
      return itemsName.get(name);
    }

    /**
    * Search for a room based on ID
    * @param id Room ID
    * @return Room object with given ID
    */
    public final Room findRoom(String id){
      return roomsID.get(id);
    }

}
