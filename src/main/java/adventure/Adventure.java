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
      rooms = new ArrayList<>();
      items= new ArrayList<>();
      roomsID = new HashMap<>();
      itemsID = new HashMap<>();
      itemsName = new HashMap<>();
      setPlayer("Player");
    }

    public final HashMap<String,Item> getItemsMapID(){
      return itemsID;
    }

    /**
     * @return A string formatted version of the object
     */
    @Override
    public final String toString(){
      return ("Adventure with "+listAllRooms().size()+" rooms and "+listAllItems().size()+"items");
    }

    /**
     * Gives access to the player
     * @return Player object
     */
    public final Player getPlayer(){
      return player;
    }

    public final void setPlayer(String playerName){
      player=new Player(playerName);
    }

    /**
    * @return List of items in player's inventory
    */
    public final ArrayList<Item> listInventory(){
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
        return player.getCurrentRoom().getLongDescription();
    }

    /**
     * 
     * @return Current room
     */
    public final Room getCurrentRoom(){
      return player.getCurrentRoom();
    }
    
    /**
     * Adds room object to adventure
     * @param room Room object
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
    * Search for a room based on ID
    * @param id Room ID
    * @return Room object with given ID
    */
    public final Room findRoom(String id){
      return roomsID.get(id);
    }

}
