package adventure;

import java.util.ArrayList;

public class Room {
    /* you will need to add some private member variables */
    private String roomName;
    private String roomLongDescription;
    private String roomShortDescription;
    private int roomID;
    private ArrayList<Item> items = new ArrayList<Item>();

    /* required public methods */

    public ArrayList<Item> listItems(){
        return items;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public String getName(){
        return roomName;
    }

    public void setName(String name){
        roomName=name;
    }

    public String getLongDescription(){
        return roomLongDescription;
    }

    public void setLongDescription (String description){
        roomLongDescription=description;
    }

    public String getShortDescription(){
        return roomShortDescription;
    }

    public void setShortDescription (String description){
        roomShortDescription=description;
    }

    public Room getConnectedRoom(String direction) {
        return null;
    }

    public void setConnectedRoom(Room connectedRoom, String direction){

    }

    public void setID(int id){
        roomID=id;
    }

    public int getID(){
        return roomID;
    }
}
