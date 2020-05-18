package adventure;

import java.util.ArrayList;

public class Room {
    /* you will need to add some private member variables */
    private String roomName;
    private String roomLongDescription;
    private String roomShortDescription;
    private String roomID;
    private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<Room> connectedRooms = new ArrayList<Room>();

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
        String[] directions = {"N","E","S","W"};
        for (int i=0;i<4;i++){
          if (direction.equals(directions[i])){
            return connectedRooms.get(i);
          }
        }
        return null;
    }

    public void setConnectedRoom(Room connectedRoom, String direction){
      String[] directions = {"N","E","S","W"};
        for (int i=0;i<4;i++){
          if (direction.equals(directions[i])){
            connectedRooms.set(i,connectedRoom);
          }
        }
    }

    public void setID(String id){
        roomID=id;
    }

    public String getID(){
        return roomID;
    }
}
