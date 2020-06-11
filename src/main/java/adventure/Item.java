package adventure;

public class Item implements java.io.Serializable{

    private static final long serialVersionUID = -9064936473102319459L;

    private String longDescription;
    private String name;
    private String id;
    private Room containingRoom;

    public Item(){
      this(null,null,null);
    }

    public Item(String itemName, String itemID, String itemDesc){
        setName(itemName);
        setID(itemID);
        setLongDescription(itemDesc);
    }

    /**
     * @return A string formatted version of the object
     */
    @Override
    public final String toString(){
      return (getName().toUpperCase()+": "+getLongDescription());
    }

    /**
     * @return Name of item
     */
    public final String getName(){
        return name;
    }

    /**
     * 
     * @param newName
     */
    public final void setName(String newName){
        name=newName;
    }

    /**
     *
     * @return Room containing the item
     */
    public final Room getContainingRoom(){
        return containingRoom;
    }

    /**
     *
     * @param newRoom
     */
    public final void setContainingRoom(Room newRoom){
        containingRoom=newRoom;
    }

    /**
     * 
     * @return Item long description
     */
    public final String getLongDescription(){
        return longDescription;
    }

    /**
     * 
     * @param newDescription
     */
    public final void setLongDescription(String newDescription){
      longDescription=newDescription;
    }

    /**
     * 
     * @param newID
     */
    public final void setID(String newID){
        id=newID;
    }

    /**
     * 
     * @return Item ID
     */
    public final String getID(){
        return id;
    }

}
