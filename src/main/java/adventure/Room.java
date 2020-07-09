package adventure;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Room implements java.io.Serializable{

    private static final long serialVersionUID = -9064936473102319459L;
    private String name;
    private String longDescription;
    private String shortDescription;
    private String id;
    private ArrayList<Item> items;
    private HashMap<String,String> connectedMapID;
    private HashMap<String,Room> connectedMapRooms;
    private HashMap<String, Item> itemsMapName;

    /**
     * Returns a short description about the room
     * @return Formatted room description
     */
    @Override
    public final String toString(){
      return (getName().toUpperCase() + ". "+getShortDescription());
    }

    /*Constructors */

    public Room(){
        setItems(new ArrayList<>());
        setConnectedMapID();
        setConnectedMapRooms();
        setItemsMapName();
    }

    public Room(Adventure adv, JSONObject objRoom){
        this();
        JSONArray jsonConnections = (JSONArray) objRoom.get("entrance");

        parseConnectionsAsID(jsonConnections);

        setID(objRoom.get("id").toString());
        setName(objRoom.get("name").toString());
        setShortDescription(objRoom.get("short_description").toString());
        setLongDescription(objRoom.get("long_description").toString());

        JSONArray loot = (JSONArray) objRoom.get("loot");
        parseLoot(adv.getItemsMapID(), loot);
    }

    /*Simple Setters */

    /**
     * 
     * @param newName New Room name
     */
    public final void setName(String newName){
        this.name=newName;
    }

    public final void setItemsMapName(){
        itemsMapName = new HashMap<>();
    }

    public final void setItems(ArrayList<Item> array){
        items=array;
    }

    public final void setConnectedMapID(){
        connectedMapID=new HashMap<>();
    }

    /**
     * Setter the map connecting room names to objects
     */
    public void setConnectedMapRooms(){
        connectedMapRooms=new HashMap<>();
    }

    /**
    * Setter for room long description
    * @param newDescription Description for room
    */
    public final void setLongDescription(String newDescription){
        longDescription=newDescription;
    }
    
    /**
     * Sets room short description
     * @param newDescription New short room description
     */
    public final void setShortDescription(String newDescription){
        shortDescription=newDescription;
    }
    
    /**
     * Adds a connected room ID
     * @param connectionDir
     * @param connectionID
     */
    public final void setConnectedRoomAsID(String connectionDir, String connectionID){
        connectedMapID.put(connectionDir,connectionID);
    }

    /**
     * Adds a connected room object
     * @param connectionDir
     * @param room
     */
    public final void setConnectedRoomAsRoom(String connectionDir, Room room){
        connectedMapRooms.put(connectionDir,room);
    }

    /**
     * Sets room ID
     * @param newID Room ID
     */
    public final void setID(String newID){
        id=newID;
    }

    /*Helper methods */

    /**
     * Connects rooms using room IDs. Intended as a temporary step before converting to room obj
     * @param objEntrances
     */
    private void parseConnectionsAsID(JSONArray objEntrances){
        for (Object conn : objEntrances){
        JSONObject c = (JSONObject) conn;
        this.setConnectedRoomAsID(c.get("dir").toString(),c.get("id").toString());
        }
    }

      /**
     * Populates room item list with items from JSONArray
     * @param itemsMap Hashmap mapping item IDs to item objects
     * @param objLoot JSONArray containing items
     */
    private void parseLoot(HashMap<String,Item> itemsMap, JSONArray objLoot){
        if (objLoot!=null) {
            for (Object i : objLoot) {
                JSONObject item = (JSONObject) i;

                Item objItem = itemsMap.get(item.get("id").toString());

                objItem.setContainingRoom(this);
                addItem(objItem);
            }
        }
    }
    
    /*Simple Getters */

    /**
     * Returns room ID
     * @return Room ID
     */
    public final String getID(){
        return id;
    }

    /**
     * 
     * @return Room name
     */
    public final String getName(){
        return name;
    }

    /**
     * 
     * @return Room long description
     */
    public final String getLongDescription(){
        return longDescription;
    }

    /**
     * 
     * @return Room short description
     */
    public final String getShortDescription(){
        return shortDescription;
    }

    /**
     * 
     * @param dir
     * @return Room id connected in specified direction
     */
    public final Room getConnectedRoom(String dir) {
        return connectedMapRooms.get(dir);
    }

    /**
     * 
     * @return Hashmap of connected IDs
     */
    public final HashMap<String,String> getConnectedRoomsList(){
        return connectedMapID;
    }

    /**
     * Gets list of items
     * @return Arraylist of items contained in room
     */
    public final ArrayList<Item> listItems(){
        return items;
    }

    /*Specific Mutators */

    /**
     * 
     * @param item
     */
    public final void addItem(Item item){
        items.add(item);
        itemsMapName.put(item.getName(), item);
    }

    /**
     * Removes item object from room
     * @param item
     */
    public final void removeItem(Item item){
      items.remove(item);
      itemsMapName.remove(item.getName());
    }

    /*Specific Getters */
    
    /**
     * Searches for an item object based on name
     * @param newName New room name
     * @return Item object with specified name
     */
    public final Item findItem(String newName){
        return itemsMapName.get(newName);
      }
  
      /**
       * 
       * @param newName Item name
       * @return Whether or not the room contains this item
       */
      public final boolean containsItem(String newName){
        return itemsMapName.containsKey(newName);
      }
  
      /**
       * 
       * @param dir
       * @return Whether or not there is a connected room in that direction
       */
      public final boolean hasConnection(String dir){
        return connectedMapID.containsKey(dir);
      }
}
