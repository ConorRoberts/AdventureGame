package adventure;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adventure implements java.io.Serializable{

  private static final long serialVersionUID = -9064936473102319459L;

  private Player player;
  private ArrayList<Room> rooms;
  private ArrayList<Item> items;
  private HashMap<String,Room> roomsMapID;
  private HashMap<String,Item> itemsMapID;
  private HashMap<String,Item> itemsMapName;

  public Adventure(){
    setRooms(new ArrayList<>());
    setItems(new ArrayList<>());
    setRoomsMapID(new HashMap<>());
    setItemsMapID(new HashMap<>());
    setItemsMapName(new HashMap<>());
    setPlayer("Player");
  }

  public Adventure(JSONObject objAdventure){
    this();
    parseItems((JSONArray) objAdventure.get("item"));
    System.out.println("two");
    parseRooms((JSONArray) objAdventure.get("room"));

    parseConnectionsAsRoom();

    getPlayer().setCurrentRoom(listAllRooms().get(0));
  }

  /**
   * Converts JSONArray into individual room objects
   * @param objRooms JSONArray of rooms
   */
  private void parseRooms(JSONArray objRooms){
    for (Object obj : objRooms){
      this.addRoom(new Room(this,(JSONObject) obj));
    }
  }

  /**
   * Converts the list of room IDs to actual room objects
   */
  private void parseConnectionsAsRoom(){
    for(Room r : this.listAllRooms()){
      for (Map.Entry<String,String> m : r.getConnectedRoomsList().entrySet()) {
        r.setConnectedRoomAsRoom(m.getKey(),this.findRoom(m.getValue()));
      }
    }
  }


  /**
   * Adds items to Adventure's list of items
   * @param objItems JSONArray of items
   */
  private void parseItems(JSONArray objItems){
    for (Object objCurrent : objItems){
      JSONObject i = (JSONObject) objCurrent;

      String name=i.get("name").toString();
      String id = i.get("id").toString();
      String desc = i.get("desc").toString();

      this.addItem(new Item(name,id,desc));
    }
  }

  public final void setItemsMapName(HashMap<String,Item> newMap){
    itemsMapName=newMap;
  }

  public final void setRoomsMapID(HashMap<String,Room> newMap){
    roomsMapID=newMap;
  }

  public final void setItemsMapID(HashMap<String,Item> newMap){
    itemsMapID=newMap;
  }

  public final void setItems(ArrayList<Item> newItems){
    items=newItems;
  }

  public final void setRooms(ArrayList<Room> newRooms){
    rooms=newRooms;
  }

  public final HashMap<String,Item> getItemsMapID(){
    return itemsMapID;
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
    this.roomsMapID.put(room.getID(),room);
  }

  /**
   * 
   * @param item
   */
  public final void addItem(Item item){
    this.items.add(item);
    this.itemsMapID.put(item.getID(),item);
    this.itemsMapName.put(item.getName(),item);
  }

  /**
  * Search for a room based on ID
  * @param id Room ID
  * @return Room object with given ID
  */
  public final Room findRoom(String id){
    return roomsMapID.get(id);
  }

}
