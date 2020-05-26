package adventure;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    /* you will need to add some private member variables */
    private String name;
    private String longDescription;
    private String shortDescription;
    private String id;
    private ArrayList<Item> items = new ArrayList<Item>();
    private HashMap<String,Room> connections= new HashMap<String,Room>();
    private ArrayList<String> connectedID = new ArrayList<String>();
    private ArrayList<String> connectedDir = new ArrayList<String>();

    public final ArrayList<Item> listItems(){
        return items;
    }

    public final Item findItem(String name){
      for (Item i : items){
        if (i.getName().equals(name)){
          return i;
        }
      }
      return null;
    }

    public final void addItem(Item item){
        items.add(item);
    }

    public final String getName(){
        return this.name;
    }

    public final void setName(String name){
        this.name=name;
    }

    public final String getLongDescription(){
        return this.longDescription;
    }

    public final void setLongDescription(String description){
        this.longDescription=description;
    }

    public final String getShortDescription(){
        return this.shortDescription;
    }

    public final void setShortDescription(String description){
        this.shortDescription=description;
    }

    public final Room getConnectedRoom(String dir) {
        return connections.get(dir);
    }

    public final void setConnectedRoom(Room room, String dir){
        connections.put(dir,room);
    }

    public final ArrayList<String> getConnectedDir(){
      return connectedDir;
    }

    public final ArrayList<String> getConnectedID(){
      return connectedID;
    }

    public final void setConnectedID(String id, String direction){
      connectedID.add(id);
      connectedDir.add(direction);
    }

    public final void setID(String id){
        this.id=id;
    }

    public final String getID(){
        return this.id;
    }
}
