package adventure;

import java.util.ArrayList;
import java.util.HashMap;

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
        items = new ArrayList<Item>();
        connections= new HashMap<String,String>();
        itemsName = new HashMap<String, Item>();
        connectedRooms = new HashMap<String, Room>();
    }

    @Override
    public final String toString(){
      return (name + "("+id+"): "+shortDescription);
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
     * @param newName
     * @return Item object with specified name
     */
    public final Item findItem(String newName){
      return itemsName.get(newName);
    }

    /**
     * 
     * @param newName
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
    public final void setConnectedRoom(String connectionDir, String connectionID){
        connections.put(connectionDir,connectionID);
    }

    public final void setConnectedRoomAsRoom(String connectionDir, Room room){
        connectedRooms.put(connectionDir,room);
    }

    /**
     * 
     * @param newID
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
      items.remove(items.indexOf(item));
      itemsName.remove(item.getName());
    }
}
