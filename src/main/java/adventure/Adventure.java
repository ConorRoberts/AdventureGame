package adventure;

import java.util.ArrayList;

public class Adventure{

    private Room currentRoom;
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Item> items= new ArrayList<Item>();

    public ArrayList<Room> listAllRooms(){
        return rooms;
    }

    public ArrayList<Item> listAllItems(){
        return items;
    }

    public String getCurrentRoomDescription(){
        return currentRoom.getShortDescription();
    }

}
