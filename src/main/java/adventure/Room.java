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
        connectedRooms = new HashMap<String,Room>();
        itemsName = new HashMap<String, Item>();
    }

    @Override
    public final String toString(){
      return (name + "("+id+"): "+shortDescription);
    }

    /**
     * 
     * @return
     */
    public final HashMap<String,Room> getConnectedRoomList(){
        return connectedRooms;
    }

    public final HashMap<String,String> getConnectedIDList(){
        return connections;
    }

    /**
     * 
     * @return
     */
    public final ArrayList<Item> listItems(){
        return items;
    }

    /**
     * 
     * @param newName
     * @return
     */
    public final Item findItem(String newName){
      return itemsName.get(newName);
    }

    /**
     * 
     * @param newName
     * @return
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
     * @return
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
     * @return
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
     * @return
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
     * @return
     */
    public final Room getConnectedRoom(String dir) {
        return connectedRooms.get(dir);
    }

    /**
     * Adds a connected room object
     * @param dir
     * @param room
     */
    public final void setConnectedRoomAsRoom(String dir, Room room){
        connectedRooms.put(dir,room);
    }

    /**
     * 
     * @param dir
     * @return
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

    /**
     * 
     * @param newID
     */
    public final void setID(String newID){
        this.id=newID;
    }

    /**
     * 
     * @return
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
