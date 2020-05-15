package adventure;

import java.util.ArrayList;

public class Adventure{
    /* you will need to add some private member variables */
    private Room currentRoom;
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Item> items= new ArrayList<Item>();

    /* ======== Required public methods ========== */
        /* note,  you don't have to USE all of these
        methods but you do have to provide them.
        We will be using them to test your code */

    public ArrayList<Room> listAllRooms(){
        return rooms;
    }

    public ArrayList<Item> listAllItems(){
        return items;
        /*for (Object r : rooms){ 
            JSONObject current = (JSONObject) r;
            String temp=(String) current.get("name");
            System.out.println(temp);
        }*/
    }

    public String getCurrentRoomDescription(){
        return currentRoom.getLongDescription();
    }

    /* you may wish to add additional methods*/
    public void addRoom(){
        rooms.add(new Room());
    }
}
