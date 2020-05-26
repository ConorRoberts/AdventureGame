package adventure;

public class Item{

    private String itemDescription;
    private String itemName;
    private Room itemRoom;
    private String itemID;

    public final String getName(){
        return itemName;
    }

    public final void setName(String name){
        itemName=name;
    }

    public final String getLongDescription(){
        return itemDescription;
    }

    public final void setLongDescription(String description){
      itemDescription=description;
    }

    public final Room getContainingRoom(){
        return itemRoom;
    }

    public final void setContainingRoom(Room room){
        itemRoom=room;
    }

    public final void setID(String id){
        itemID=id;
    }

    public final String getID(){
        return itemID;
    }

}
