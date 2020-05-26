package adventure;

import java.util.ArrayList;

public class Adventure{

    private Room currentRoom;
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Item> items= new ArrayList<Item>();

    public final ArrayList<Room> listAllRooms(){
        return rooms;
    }

    public final ArrayList<Item> listAllItems(){
        return items;
    }

    public final String getCurrentRoomDescription(){
        return currentRoom.getShortDescription();
    }

    public final void setCurrentRoom(Room room){
      currentRoom=room;
    }

    public final void addRoom(Room room){
      this.rooms.add(room);
    }

    public final Room getCurrentRoom(){
      return currentRoom;
    }

    public Item findItem(String id){
      return null;
    }

    public Room findRoom(String id){
      return null;
    }

}
