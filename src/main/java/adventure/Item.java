package adventure;

public class Item{

    private String itemName;
    private String roomDescription;
    private Room itemRoom;
    private int itemID;

    public String getName(){
        return itemName;
    }

    public String getLongDescription(){
        return roomDescription;
    }

    public Room getContainingRoom(){
        return itemRoom;
    }

    public void setName(String name){
        itemName=name;
    }

    public void setRoom(Room room){
        itemRoom=room;
    }

    public void setID(int ID){
        itemID=ID;
    }

    public int getID(){
        return itemID;
    }

}
