package adventure;

public class Item{

    private String itemDescription;
    private String itemName;
    private Room itemRoom;
    private String itemID;

    public String getName(){
        return itemName;
    }

    public void setName(String name){
        itemName=name;
    }

    public String getLongDescription(){
        return itemDescription;
    }

    public void setLongDescription(String description){
      itemDescription=description;
    }

    public Room getContainingRoom(){
        return itemRoom;
    }

    public void setContainingRoom(Room room){
        itemRoom=room;
    }

    public void setID(String id){
        itemID=id;
    }

    public String getID(){
        return itemID;
    }

}
