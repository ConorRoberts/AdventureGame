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
    private HashMap<String,String> connections;
    private HashMap<String,Room> connectedRooms;
    private HashMap<String, Item> itemsName;

    public Room(){
        items = new ArrayList<>();
        connections= new HashMap<>();
        itemsName = new HashMap<>();
        connectedRooms = new HashMap<>();
    }

    public Room(Adventure adv, JSONObject objRoom){
        this();
        JSONArray jsonConnections = (JSONArray) objRoom.get("entrance");

        this.parseConnectionsAsID(jsonConnections);

        this.setID(objRoom.get("id").toString());
        this.setName(objRoom.get("name").toString());
        this.setShortDescription(objRoom.get("short_description").toString());
        this.setLongDescription(objRoom.get("long_description").toString());

        JSONArray loot = (JSONArray) objRoom.get("loot");
        this.parseLoot(adv.getItemsMapID(), loot);
    }

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
                this.addItem(itemsMap.get(item.get("id").toString()));
            }
        }
    }

    @Override
    public final String toString(){
      return (getName().toUpperCase() + ". "+getShortDescription());
    }

    /**
     * 
     * @return Arraylist of items contained in room
     */
    public final ArrayList<Item> listItems(){
        return items;
    }

    /**
     * 
     * @param newName New room name
     * @return Item object with specified name
     */
    public final Item findItem(String newName){
      return itemsName.get(newName);
    }

    /**
     * 
     * @param newName Item name
     * @return Whether or not the room contains this item
     */
    public final boolean containsItem(String newName){
      return itemsName.containsKey(newName);
    }

    /**
     * 
     * @param item
     */
    public final void addItem(Item item){
        items.add(item);
        itemsName.put(item.getName(), item);
    }

    /**
     * 
     * @return Room name
     */
    public final String getName(){
        return this.name;
    }

    /**
     * 
     * @param newName
     */
    public final void setName(String newName){
        this.name=newName;
    }

    /**
     * 
     * @return Room long description
     */
    public final String getLongDescription(){
        return this.longDescription;
    }

    /**
    * @param newDescription Description for room
    */
    public final void setLongDescription(String newDescription){
        this.longDescription=newDescription;
    }

    /**
     * 
     * @return Room short description
     */
    public final String getShortDescription(){
        return this.shortDescription;
    }

    /**
     * 
     * @param newDescription
     */
    public final void setShortDescription(String newDescription){
        this.shortDescription=newDescription;
    }

    /**
     * 
     * @param dir
     * @return Room id connected in specified direction
     */
    public final Room getConnectedRoom(String dir) {
        return connectedRooms.get(dir);
    }

    /**
     * 
     * @return Hashmap of connected IDs
     */
    public final HashMap<String,String> getConnectedRoomsList(){
        return connections;
    }

    public final HashMap<String,Room> getConnectedRoomsListTwo(){
        return connectedRooms;
    }

    /**
     * 
     * @param dir
     * @return Whether or not there is a connected room in that direction
     */
    public final boolean hasConnection(String dir){
      return connections.containsKey(dir);
    }

    /**
     * Adds a connected room ID
     * @param connectionDir
     * @param connectionID
     */
    public final void setConnectedRoomAsID(String connectionDir, String connectionID){
        connections.put(connectionDir,connectionID);
    }

    public final void setConnectedRoomAsRoom(String connectionDir, Room room){
        connectedRooms.put(connectionDir,room);
    }

    /**
     * 
     * @param newID Room ID
     */
    public final void setID(String newID){
        this.id=newID;
    }

    /**
     * 
     * @return Room ID
     */
    public final String getID(){
        return this.id;
    }

    /**
     * 
     * @param item
     */
    public final void removeItem(Item item){
      items.remove(item);
      itemsName.remove(item.getName());
    }
}
