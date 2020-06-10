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
    private String ID;
    private ArrayList<Item> items;
    private HashMap<String,String> connections;
    private HashMap<String,Room> connectedRooms;
    private HashMap<String, Item> itemsName;

    public void setItemsName(){
        itemsName = new HashMap<>();
    }

    public void setItems(){
        items=new ArrayList<>();
    }

    public void setConnections(){
        connections=new HashMap<>();
    }

    public void setConnectedRooms(){
        connectedRooms=new HashMap<>();
    }

    public Room(){
        setItems();
        setConnections();
        setConnectedRooms();
        setItemsName();
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
        ID=newID;
    }

    /**
     * 
     * @return Room ID
     */
    public final String getID(){
        return ID;
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
