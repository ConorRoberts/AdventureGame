package adventure;

import java.util.ArrayList;

public class Room {
    /* you will need to add some private member variables */
    private String roomName;
    private String roomLongDescription;
    private String roomShortDescription;
    private String roomID;
    private ArrayList<Item> items = new ArrayList<Item>();
    private Room connectedN;
    private Room connectedE;
    private Room connectedS;
    private Room connectedW;
    private ArrayList<String> connectedID = new ArrayList<String>();
    private ArrayList<String> connectedDir = new ArrayList<String>();

    public ArrayList<Item> listItems(){
        return items;
    }

    public Item findItem(String name){
      for (Item i : items){
        if (i.getName().equals(name)){
          return i;
        }
      }
      return null;
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

    public void setLongDescription(String description){
        roomLongDescription=description;
    }

    public String getShortDescription(){
        return roomShortDescription;
    }

    public void setShortDescription(String description){
        roomShortDescription=description;
    }

    public Room getConnectedRoom(String direction) {
        if (direction.equals("N")){
          return connectedN;
        }else if (direction.equals("E")){
          return connectedE;
        }else if (direction.equals("S")){
          return connectedS;
        }else if (direction.equals("W")){
          return connectedW;
        }else{
          return null;
        }
    }

    public void setConnectedRoom(Room connectedRoom, String direction){
        if (direction.equals("N")){
          connectedN=connectedRoom;
        }else if (direction.equals("E")){
          connectedE=connectedRoom;
        }else if (direction.equals("S")){
          connectedS=connectedRoom;
        }else if (direction.equals("W")){
          connectedW=connectedRoom;
        }
    }

    public ArrayList<String> getConnectedDir(){
      return connectedDir;
    }

    public ArrayList<String> getConnectedID(){
      return connectedID;
    }

    public void setConnectedID(String id, String direction){
      connectedID.add(id);
      connectedDir.add(direction);
    }

    public void setID(String id){
        roomID=id;
    }

    public String getID(){
        return roomID;
    }
}
